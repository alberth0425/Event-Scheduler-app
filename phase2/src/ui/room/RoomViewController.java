package ui.room;

import entities.Room;
import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import ui.BaseViewController;
import ui.navigation.FXMLFile;
import ui.user.UserActionViewController;

@FXMLFile("room.fxml")
public class RoomViewController extends BaseViewController<Void> implements RoomPresenter.RoomView {
    public TableView<Room> roomsTableView;

    private RoomPresenter presenter;

    @Override
    public void initializeWithParameters(Void parameters) {
        presenter = new RoomPresenter(this);

        TableColumn<Room, Integer> roomNumberColumn = new TableColumn<>("Room Number");
        roomNumberColumn.setCellValueFactory(new PropertyValueFactory<>("roomNumber"));
        roomsTableView.getColumns().add(roomNumberColumn);

        TableColumn<Room, Integer> capacityColumn = new TableColumn<>("Capacity");
        capacityColumn.setCellValueFactory(new PropertyValueFactory<>("capacity"));
        roomsTableView.getColumns().add(capacityColumn);

        roomsTableView.setItems(presenter.getRooms());
    }

    public void backButtonAction(ActionEvent actionEvent) {
        getNavigationController().navigate(UserActionViewController.class);
    }
}
