package ui.event;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import ui.BaseViewController;
import ui.navigation.FXMLFile;
import ui.user.UserActionViewController;

import java.util.ArrayList;
import java.util.List;

@FXMLFile("event.fxml")
public class EventViewController extends BaseViewController<EventPresenter.EventFilter> implements EventPresenter.EventView {
    public TableView<EventAdapter> eventTableView;
    public Button backButton;
    public HBox actionHBox;

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
                presenter.setSelectedIndex((Integer) newValue);

                if (!oldValue.equals(newValue)) {
                    refreshActionButtons();
                }
            });
        });
    }

    public void backButtonAction() {
        getNavigationController().navigate(UserActionViewController.class);
    }

    @Override
    public void refreshActionButtons() {
        int index = eventTableView.getSelectionModel().getSelectedIndex();

        List<Node> buttons = new ArrayList<>();
        buttons.add(backButton);

        if (index != -1) {
            for (EventPresenter.EventAction action : presenter.getActionsForEvent(index)) {
                Button button = new Button(action.getName());
                button.setOnAction(event -> action.call());
                buttons.add(button);
            }
        }

        actionHBox.getChildren().setAll(buttons);
    }

    @Override
    public void refreshTableView() {
        eventTableView.refresh();
    }
}
