package ui.event;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import ui.BaseViewController;
import ui.user.create_event.createEventViewController;
import ui.navigation.FXMLFile;
import ui.user.UserActionViewController;
import ui.util.TextFieldPrompt;

import java.util.ArrayList;
import java.util.List;

@FXMLFile("event.fxml")
public class EventViewController extends BaseViewController<EventPresenter.EventFilter> implements EventPresenter.EventView {
    public VBox containerVBox;
    public TableView<EventAdapter> eventTableView;
    public Button backButton;
    public HBox actionHBox;
    public Label errorLabel;


    // Text field to display above TableView when prompted. Initialized when needed
    private Node promptTextField;

    private EventPresenter presenter;

    @Override
    public void initializeWithParameters(EventPresenter.EventFilter filter) {
        presenter = new EventPresenter(this, filter);

        // Set table columns given by presenter based on current user type
        List<TableColumn<EventAdapter, String>> tableColumns = new ArrayList<>();

        for (EventPresenter.EventTableColumn column : presenter.getTableColumns()) {
            TableColumn<EventAdapter, String> tableColumn = new TableColumn<>();
            tableColumn.setText(column.getName());
            tableColumn.setCellValueFactory(new PropertyValueFactory<>(column.getField()));
            tableColumns.add(tableColumn);
        }
        eventTableView.getColumns().setAll(tableColumns);

        eventTableView.setItems(presenter.getEventList());
        eventTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        eventTableView.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            Platform.runLater(() -> {
                if (!oldValue.equals(newValue)) {
                    dismissTextField();
                    refreshActionButtons();
                }
            });
        });
    }

    public void backButtonAction() {
        getNavigationController().navigate(UserActionViewController.class);
    }

    @Override
    public void displayTextField(String promptText, TextFieldPrompt.Validator validator) {
        dismissTextField();

        promptTextField = TextFieldPrompt.create(promptText, "Ok", validator, this::dismissTextField);

        // Add text field to container above actions
        containerVBox.getChildren().add(1, promptTextField);
    }

//    @Override
//    public void setError(String error) {
//        errorLabel.setText(error);
//    }

    private void dismissTextField() {
        if (promptTextField != null) {
            containerVBox.getChildren().remove(promptTextField);
            promptTextField = null;
        }
    }

    @Override
    public void refreshActionButtons() {
        int index = eventTableView.getSelectionModel().getSelectedIndex();

        List<Node> buttons = new ArrayList<>();
        buttons.add(backButton);


        for (EventPresenter.EventAction action : presenter.getActionsForEvent(index)) {
            Button button = new Button(action.getName());
            button.setOnAction(event -> action.call());
            buttons.add(button);

        }

        actionHBox.getChildren().setAll(buttons);
    }

    @Override
    public void refreshTableView() {
        eventTableView.refresh();
    }

    @Override
    public void navigateToCreateEvent() {
        getNavigationController().navigate(createEventViewController.class);
    }
}
