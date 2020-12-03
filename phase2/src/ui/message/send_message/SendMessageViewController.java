package ui.message.send_message;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import ui.BaseViewController;
import ui.message.MessageViewController;
import ui.navigation.FXMLFile;

@FXMLFile("send_message.fxml")
public class SendMessageViewController extends BaseViewController<Void> implements SendMessagePresenter.SendMessageView {
    public ComboBox<String> receiverComboBox;
    public TextField receiverTextField;
    public TextArea contentTextArea;

    private SendMessagePresenter presenter;

    @Override
    public void initializeWithParameters(Void parameters) {
        presenter = SendMessagePresenter.create(this);

        // TODO: maybe replace the receiver text field with a combo box

        receiverComboBox.setItems(presenter.getReceiverTypes());
        receiverComboBox.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            presenter.onSelectReceiverType((Integer) newValue);

            receiverTextField.setText("");

            String prompt = presenter.getReceiverTextPrompt((Integer) newValue);
            if (prompt == null) {
                receiverTextField.setVisible(false);
            } else {
                receiverTextField.setVisible(true);
                receiverTextField.setPromptText(prompt);
            }
        });

        receiverComboBox.getSelectionModel().select(0);

        receiverTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            presenter.setReceiver(newValue);
        });
        contentTextArea.textProperty().addListener((observable, oldValue, newValue) -> {
            presenter.setContent(newValue);
        });
    }

    public void sendButtonAction(ActionEvent actionEvent) {
        presenter.onSendMessage();
    }

    public void backButtonAction(ActionEvent actionEvent) {
        navigateToMessages();
    }

    @Override
    public void navigateToMessages() {
        getNavigationController().navigate(MessageViewController.class);
    }

    @Override
    public void displayError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message);
        alert.show();
    }
}
