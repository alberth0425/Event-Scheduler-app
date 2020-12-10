package ui.message;

import entities.*;
import gateway.PersistenceStorage;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import use_cases.AuthService;
import use_cases.MessageService;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    public List<MessageAction> getActionsForMessage(int index) {
        List<MessageAction> actions = new ArrayList<>();

        actions.addAll(getMessageAction(index));

        return actions;
    }

    private List<MessageAction> getMessageAction(int index) {
        List<MessageAction> actions = new ArrayList<>();

        if (index != -1) {
            Message message = messages.get(index).getMessage();

            actions.add(new MessageAction("Delete Message", () -> {
                view.showLoading();
                // Run in background
                new Thread(() -> {
                    try {
                        PersistenceStorage.deleleMessage(message);
                        MessageService.shared.deleteSingleMessages(AuthService.shared.getCurrentUser().getUsername(), message);
                    } catch (IOException e) {
                        System.out.println("Unknown error.");
                    }

                    Platform.runLater(() -> {
                        view.hideLoading();
                        refresh();
                    });
                }).start();
            }));

            if (message.isArchived) {
                actions.add(new MessageAction("Mark as unarchive", () -> {
                    message.setUnarchived();
                    refresh();
                }));
            } else {
                actions.add(new MessageAction("Mark as archive", () -> {
                    message.setArchived();
                    refresh();
                }));;
            }
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
         * Get the name of the message action.
         *
         * @return the name
         */
        public String getName() {
            return name;
        }

        /**
         * Run the message action.
         */
        public void call() {
            callback.run();
        }
    }

    private void refresh() {
        refreshMessages();
        // Table view is updated automatically when list changes
        view.refreshActionButtons();
    }

    private void refreshMessages() {
        Stream<Message> messages = MessageService.shared.getAllMessages().stream();
        String currentUsername = AuthService.shared.getCurrentUser().getUsername();

        messages = messages.filter(message -> message.getReceiverUsername().contains(currentUsername));

        List<MessageAdapter> messageList = messages
                .map(message -> new MessageAdapter(message, DATE_FORMAT))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        this.messages.setAll(messageList);
    }

    private interface MessageActionCallback {
        void run();
    }

    public interface MessageView {
        void setMessageTitleLabel(String text);
        void setMessageContentLabel(String text);
        void navigateToSendMessage();

        void refreshActionButtons();
        void refreshTableView();

        default void refresh() {
            refreshTableView();
            refreshActionButtons();
        }

        void showLoading();
        void hideLoading();
    }
}
