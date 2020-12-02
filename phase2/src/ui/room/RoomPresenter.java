package ui.room;

import entities.Room;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import use_cases.RoomService;

public class RoomPresenter {
    private final RoomView view;

    private final ObservableList<Room> rooms;

    /**
     * Construct a room presenter with given view.
     *
     * @param view the view using this presenter
     */
    public RoomPresenter(RoomView view) {
        this.view = view;

        rooms = FXCollections.observableArrayList(RoomService.shared.getAllRooms());
    }

    /**
     * Get the list of rooms to display.
     *
     * @return a list of Room
     */
    public ObservableList<Room> getRooms() {
        return rooms;
    }

    public interface RoomView {

    }
}
