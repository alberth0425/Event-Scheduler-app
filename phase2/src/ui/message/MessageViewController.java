package ui.message;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import ui.BaseViewController;
import ui.message.send_message.SendMessageViewController;
import ui.navigation.FXMLFile;
import ui.user.UserActionViewController;
import ui.util.TextFieldPrompt;

@FXMLFile("message.fxml")
public class MessageViewController extends BaseViewController<Void> implements MessagePresenter.MessageView {
    public TableView<MessageAdapter> messagesTableView;
    public Label messageTitleLabel;
    public Label messageContentLabel;
    public VBox containerVBox;

    private MessagePresenter presenter;

    // Text field to display above TableView when prompted. Initialized when needed
    private Node promptTextField;

    @Override
    public void initializeWithParameters(Void parameters) {
        presenter = new MessagePresenter(this);

        ObservableList<TableColumn<MessageAdapter, ?>> columns = messagesTableView.getColumns();

        // Add table columns
        TableColumn<MessageAdapter, String> idColumn = new TableColumn<>();
        idColumn.setText("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        columns.add(idColumn);

        TableColumn<MessageAdapter, String> senderColumn = new TableColumn<>();
        senderColumn.setText("Sender");
        senderColumn.setCellValueFactory(new PropertyValueFactory<>("senderUsername"));
        columns.add(senderColumn);

        TableColumn<MessageAdapter, String> receiverColumn = new TableColumn<>();
        receiverColumn.setText("Receiver");
        receiverColumn.setCellValueFactory(new PropertyValueFactory<>("receiverUsername"));
        columns.add(receiverColumn);

        TableColumn<MessageAdapter, String> formattedTimeColumn = new TableColumn<>();
        formattedTimeColumn.setText("Time");
        formattedTimeColumn.setCellValueFactory(new PropertyValueFactory<>("formattedTime"));
        columns.add(formattedTimeColumn);

        TableColumn<MessageAdapter, String> archiveStateColumn = new TableColumn<>();
        archiveStateColumn.setText("Archive State");
        archiveStateColumn.setCellValueFactory(new PropertyValueFactory<>("archiveState"));
        columns.add(archiveStateColumn);

        // Sort by reverse chronological order by default
        formattedTimeColumn.setComparator(formattedTimeColumn.getComparator().reversed());
        messagesTableView.getSortOrder().add(formattedTimeColumn);

        messagesTableView.setItems(presenter.getMessageList());
        messagesTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        messagesTableView.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            Platform.runLater(() -> presenter.onSelectMessage((Integer) newValue));
        });
    }

    public void backButtonAction(ActionEvent actionEvent) {
        getNavigationController().navigate(UserActionViewController.class);
    }

    public void sendMessageAction(ActionEvent actionEvent) {
        presenter.onSendMessage();
    }

    @Override
    public void setMessageTitleLabel(String text) {
        messageTitleLabel.setText(text);
    }

    @Override
    public void setMessageContentLabel(String text) {
        messageContentLabel.setText(text);
    }

    @Override
    public void navigateToSendMessage() {
        getNavigationController().navigate(SendMessageViewController.class);
    }

    @Override
    public void displayTextField(String promptText, TextFieldPrompt.Validator validator) {
        dismissTextField();

        promptTextField = TextFieldPrompt.create(promptText, "Ok", validator, this::dismissTextField);

        // Add text field to container above actions
        containerVBox.getChildren().add(1, promptTextField);
    }

    private void dismissTextField() {
        if (promptTextField != null) {
            containerVBox.getChildren().remove(promptTextField);
            promptTextField = null;
        }
    }
}
