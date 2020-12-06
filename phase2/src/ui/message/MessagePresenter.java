package ui.message;

import entities.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ui.event.EventPresenter;
import ui.util.TextFieldPrompt;
import use_cases.AuthService;
import use_cases.EventService;
import use_cases.MessageService;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class MessagePresenter {
    private final MessageView view;

    private final ObservableList<MessageAdapter> messages;

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * Construct a MessagePresenter with given view.
     *
     * @param view the view using this presenter
     */
    public MessagePresenter(MessageView view) {
        this.view = view;

        messages = FXCollections.observableArrayList();

        String username = AuthService.shared.getCurrentUser().getUsername();
        for (Message message : MessageService.shared.getReceivedMessages(username)) {
            messages.add(new MessageAdapter(message, DATE_FORMAT));
        }

        view.setMessageTitleLabel("");
        view.setMessageContentLabel("");
    }

    /**
     * Get the list of messages to display.
     *
     * @return a list of MessageAdapter
     */
    public ObservableList<MessageAdapter> getMessageList() {
        return messages;
    }

    /**
     * Handle the send message action.
     */
    public void onSendMessage() {
        view.navigateToSendMessage();
    }

    /**
     * Handle that a message has been selected.
     *
     * @param index the index of the selected message
     */
    public void onSelectMessage(int index) {
        if (index == -1) {
            view.setMessageTitleLabel("");
            view.setMessageContentLabel("");
        } else {
            view.setMessageTitleLabel(messages.get(index).getFormattedTime());
            view.setMessageContentLabel(messages.get(index).getContent());
        }
    }

    /**
     * Get the list of actions that can be performed by the current user on the message at given index.
     * If index is -1, then get the list of actions that can be performed by this user in general.
     *
     * @param index the index of the selected event, or -1 if nothing is selected
     * @return a list of MessageAction
     */
    public List<EventPresenter.EventAction> getActionsForMessage(int index) {
        List<EventPresenter.EventAction> actions = new ArrayList<>();
        User user = AuthService.shared.getCurrentUser();

        actions.addAll(getMessageAction(index));

        return actions;
    }

    private List<EventPresenter.EventAction> getMessageAction(int index) {
        List<EventPresenter.EventAction> actions = new ArrayList<>();

        // TODO send message?
//        actions.add(new EventPresenter.EventAction("Create event", view::navigateToCreateEvent));

        if (index != -1) {
            Message message = messages.get(index).getMessage();

            actions.add(new MessageAction("Delete Message", () -> {
                view.displayTextField("Speaker ID", speakerId -> {
                    try {
                        Speaker speaker = (Speaker) AuthService.shared.getUserByUsername(speakerId);
                        EventService.shared.setEventSpeaker(speaker, event);
                        refresh();
                        return null;

                    } catch (AuthService.AuthException | ClassCastException e) {
                        return "No speaker with ID " + speakerId + ".";
                    } catch (EventService.SpeakerDoubleBookException e) {
                        return "Speaker double book.";
                    } catch (EventService.EventException e) {
                        return "Unknown error when changing speaker.";
                    }
                });
            }));

            actions.add(new EventPresenter.EventAction("Change capacity", () -> {
                view.displayTextField("Capacity", capacityStr -> {
                    try {
                        int capacity = Integer.parseInt(capacityStr);
                        EventService.shared.setCapacity(capacity, event);
                        refresh();
                        return null;

                    } catch (NumberFormatException e) {
                        return "Invalid number " + capacityStr;
                    } catch (IllegalArgumentException e) {
                        return "Capacity is either smaller than current number of attendees or exceeds room limit.";
                    }
                });
            }));

            actions.add(new EventPresenter.EventAction("Cancel event", () -> {
                // TODO: cancel event after use case is done
                System.out.println("Cancelling event");
            }));
        }

        return actions;
    }

    public static class MessageAction {
        private final String name;
        private final MessageActionCallback callback;

        private MessageAction(String name, MessageActionCallback callback) {
            this.name = name;
            this.callback = callback;
        }

        /**
         * Get the name of the event action.
         *
         * @return the name
         */
        public String getName() {
            return name;
        }

        /**
         * Run the event action.
         */
        public void call() {
            callback.run();
        }
    }

    private interface MessageActionCallback {
        void run();
    }

    public interface MessageView {
        void setMessageTitleLabel(String text);
        void setMessageContentLabel(String text);
        void navigateToSendMessage();
        void displayTextField(String promptText, TextFieldPrompt.Validator validator);
    }
}
