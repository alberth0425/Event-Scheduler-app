package ui.message.send_message;

import entities.Attendee;
import entities.Organizer;
import entities.User;
import javafx.collections.ObservableList;
import use_cases.AuthService;
import use_cases.MessageService;

public abstract class SendMessagePresenter {
    private final SendMessageView view;

    private int receiverTypeIndex = -1;
    private String receiver = "";
    private String content = "";

    public SendMessagePresenter(SendMessageView view) {
        this.view = view;
    }

    /**
     * Create a new UserActionPresenter based on the current user type.
     *
     * @param view the view using this presenter
     * @return an UserActionPresenter instance
     */
    public static SendMessagePresenter create(SendMessageView view) {
        User user = AuthService.shared.getCurrentUser();

        if (user instanceof Attendee) {
            return new AttendeeSendMessagePresenter(view);
        } else if (user instanceof Organizer) {
            return new OrganizerSendMessagePresenter(view);
        } else {
            return new SpeakerSendMessagePresenter(view);
        }
    }

    /**
     * Get the list of strings, each representing the receiver type.
     *
     * @return the list of receiver types
     */
    public abstract ObservableList<String> getReceiverTypes();

    /**
     * Get the text prompt to show in the receiver text field for the receiver type at given index.
     * If a text field is not needed, return null.
     *
     * @param index the index of the selected receiver type
     * @return text prompt for the receiver text field if it is needed, otherwise null
     */
    public abstract String getReceiverTextPrompt(int index);

    /**
     * Handle sending a message with given parameters.
     *
     * @param receiverTypeIndex the index of the receiver type
     * @param receiver additional information about the receiver based on receiver type, or null if not needed
     * @param content the text content of the message to be sent
     */
    abstract void handleSendMessage(int receiverTypeIndex, String receiver, String content);

    /**
     * Handle that a receiver type is selected.
     *
     * @param index the index of the receiver type
     */
    public void onSelectReceiverType(int index) {
        receiverTypeIndex = index;
    }

    /**
     * Handle send message action.
     */
    public void onSendMessage() {
        handleSendMessage(receiverTypeIndex, receiver, content);
    }

    /**
     * Set the receiver field.
     *
     * @param receiver the receiver string
     */
    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    /**
     * Set the message content.
     *
     * @param content the content string
     */
    public void setContent(String content) {
        this.content = content;
    }

    SendMessageView getView() {
        return view;
    }

    boolean sendMessage(String username, String content) {
        User sender = AuthService.shared.getCurrentUser();

        try {
            User receiver = AuthService.shared.getUserByUsername(username);
            MessageService.shared.sendMessage(content, sender, receiver);

            return true;
        } catch (AuthService.AuthException e) {
            return false;
        }
    }

    public interface SendMessageView {
        void navigateToMessages();
        void displayError(String message);
    }
}
