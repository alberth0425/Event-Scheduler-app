package ui.message;

import entities.Message;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import use_cases.AuthService;
import use_cases.MessageService;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class MessagePresenter {
    private final MessageView view;

    private final ObservableList<MessageAdapter> messages;

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

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

    public ObservableList<MessageAdapter> getMessageList() {
        return messages;
    }

    public void onSendMessage() {
        view.navigateToSendMessage();
    }

    public void onSelectMessage(int index) {
        if (index == -1) {
            view.setMessageTitleLabel("");
            view.setMessageContentLabel("");
        } else {
            view.setMessageTitleLabel(messages.get(index).getFormattedTime());
            view.setMessageContentLabel(messages.get(index).getContent());
        }
    }

    public interface MessageView {
        void setMessageTitleLabel(String text);
        void setMessageContentLabel(String text);
        void navigateToSendMessage();
    }
}
