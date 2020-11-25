package ui.event;

import entities.Event;
import entities.Room;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import use_cases.EventService;
import use_cases.RoomService;

import java.util.Collections;
import java.util.List;

public class EventPresenter {
    private final List<String> actionList = Collections.singletonList("Sign up");

    private final EventView view;

    private final ObservableList<EventAdapter> eventList;

    private int selectedIndex = -1;

    public EventPresenter(EventView view) {
        this.view = view;

        this.eventList = FXCollections.observableArrayList();
        for (Event event : EventService.shared.getAllEvents()) {
            try {
                Room room = RoomService.shared.getRoom(event.getRoomNumber());
                eventList.add(new EventAdapter(event, room));
            } catch (RoomService.RoomException e) {
                System.out.println("Event's room does not exist. This shouldn't happen.");
            }
        }
    }

    public ObservableList<EventAdapter> getEventList() {
        return eventList;
    }

    public List<String> getActionList() {
        return actionList;
    }

    public void setSelectedIndex(int selectedIndex) {
        this.selectedIndex = selectedIndex;
    }

    public void onAction(int actionIndex) {
        if (selectedIndex == -1) return;

        switch (actionIndex) {
            case 0:
                System.out.println("Sign up for event.");
                // TODO: actually sign up for event
                break;
            default:
                break;
        }
    }

    public interface EventView {
        void refreshTableView();
    }
}
