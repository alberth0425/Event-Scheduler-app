package ui.event;

import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import ui.BaseViewController;
import ui.navigation.Destination;

import java.util.List;

public class EventViewController extends BaseViewController implements EventPresenter.EventView {
    public TableView<EventAdapter> eventTableView;
    public TableColumn<EventAdapter, String> idColumn;
    public TableColumn<EventAdapter, String> titleColumn;
    public TableColumn<EventAdapter, String> speakerColumn;
    public TableColumn<EventAdapter, String> roomColumn;
    public TableColumn<EventAdapter, String> timeColumn;
    public TableColumn<EventAdapter, String> capacityColumn;
    public Button backButton;
    public HBox actionHBox;

    private EventPresenter presenter;

    public void initialize() {
        presenter = new EventPresenter(this);

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        speakerColumn.setCellValueFactory(new PropertyValueFactory<>("speaker"));
        roomColumn.setCellValueFactory(new PropertyValueFactory<>("room"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
        capacityColumn.setCellValueFactory(new PropertyValueFactory<>("capacity"));

        eventTableView.setItems(presenter.getEventList());
        eventTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        eventTableView.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            presenter.setSelectedIndex((Integer) newValue);
        });

        List<String> actions = presenter.getActionList();
        for (int i = 0; i < actions.size(); i++) {
            int index = i;
            Button button = new Button(actions.get(i));
            button.setOnAction(event -> presenter.onAction(index));
            actionHBox.getChildren().add(button);
        }
    }

    public void backButtonAction() {
        getNavigationController().navigateTo(Destination.USER_ACTIONS);
    }

    @Override
    public void refreshTableView() {
        eventTableView.refresh();
    }
}
