package ui.message.send_message;

import entities.Attendee;
import entities.Organizer;
import entities.User;
import javafx.collections.ObservableList;
import use_cases.AuthService;
import use_cases.MessageService;

import java.util.List;

public abstract class SendMessagePresenter {
    private final SendMessageView view;

    private int receiverTypeIndex = -1;
    private String receiver = "";
    private String content = "";

    public SendMessagePresenter(SendMessageView view) {
        this.view = view;
    }

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

    public abstract ObservableList<String> getReceiverTypes();

    public abstract String getReceiverTextPrompt(int index);

    abstract void handleSendMessage(int index, String receiver, String content);

    public SendMessageView getView() {
        return view;
    }

    public void onSelectReceiverType(int index) {
        receiverTypeIndex = index;
    }

    public void onSendMessage() {
        handleSendMessage(receiverTypeIndex, receiver, content);
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public void setContent(String content) {
        this.content = content;
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
