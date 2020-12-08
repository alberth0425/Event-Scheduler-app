package ui.message.send_message;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AttendeeSendMessagePresenter extends SendMessagePresenter {
    private final ObservableList<String> receiverTypes = FXCollections.observableArrayList("Send to user");

    public AttendeeSendMessagePresenter(SendMessageView view) {
        super(view);
    }

    @Override
    public ObservableList<String> getReceiverTypes() {
        return receiverTypes;
    }

    @Override
    public String getReceiverTextPrompt(int index) {
        return "Username";
    }

    @Override
    void handleSendMessage(int receiverTypeIndex, String receiver, String content) {
        if (sendMessage(receiver, content)) {
            getView().navigateToMessages();
        } else {
            getView().displayError("Invalid username.");
        }
    }
}
