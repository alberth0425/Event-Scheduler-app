package ui.event;

import entities.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import use_cases.AuthService;
import use_cases.EventService;
import use_cases.RoomService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EventPresenter {
    private final EventView view;

    private final ObservableList<EventAdapter> eventList = FXCollections.observableArrayList();

    private int selectedIndex = -1;

    private final EventFilter filter;

    public EventPresenter(EventView view, EventFilter filter) {
        this.view = view;
        this.filter = filter;

        refreshEvents();
    }

    private void refresh() {
        refreshEvents();
        // Table view is updated automatically when list changes
        view.refreshActionButtons();
    }

    private void refreshEvents() {
        Stream<Event> events = EventService.shared.getAllEvents().stream();
        String currentUsername = AuthService.shared.getCurrentUser().getUsername();

        // Apply event filters
        if (filter != null) {
            switch (filter) {
                case ALL:
                    break;
                case SIGNED_UP:
                    events = events.filter(event -> event.getAttendeeUNs().contains(currentUsername));
                    break;
                case GIVING_SPEECH:
                    events = events.filter(event -> event.getSpeakerUsername().equals(currentUsername));
                    break;
            }
        }

        List<EventAdapter> eventList = events
                .map(event -> {
                    try {
                        // Map Event to EventAdapter
                        Room room = RoomService.shared.getRoom(event.getRoomNumber());
                        return new EventAdapter(event, room, AuthService.shared.getCurrentUser().getUsername());
                    } catch (RoomService.RoomException e) {
                        System.out.println("Event's room does not exist. This shouldn't happen.");
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        this.eventList.setAll(eventList);
    }

    public ObservableList<EventAdapter> getEventList() {
        return eventList;
    }

    public List<EventTableColumn> getTableColumns() {
        List<EventTableColumn> columns = new ArrayList<>();

        Collections.addAll(columns,
                new EventTableColumn("ID", "id"),
                new EventTableColumn("Title", "title"),
                new EventTableColumn("Speaker", "speaker"),
                new EventTableColumn("Room", "room"),
                new EventTableColumn("Time", "time"),
                new EventTableColumn("Capacity", "capacity")
        );

        User user = AuthService.shared.getCurrentUser();
        if (user instanceof Attendee) {
            columns.add(new EventTableColumn("Signed Up", "signedUp"));
        }

        return columns;
    }

    public void setSelectedIndex(int selectedIndex) {
        this.selectedIndex = selectedIndex;
    }

    public List<EventAction> getActionsForEvent(int index) {
        Event event = eventList.get(index).getEvent();
        User user = AuthService.shared.getCurrentUser();
        List<EventAction> actions = new ArrayList<>();

        if (user instanceof Attendee) {
            // TODO: refactor things like this into use case
            if (event.getAttendeeUNs().contains(user.getUsername())) {
                // User signed up for this event
                actions.add(new EventAction("Cancel sign up", () -> {
                    try {
                        EventService.shared.removeEventAttendee((Attendee) user, event);
                        refresh();
                    } catch (EventService.EventException e) {
                        System.out.println("Failed to cancel sign up: " + e.getMessage());
                    }
                }));
            } else {
                actions.add(new EventAction("Sign up", () -> {
                    try {
                        EventService.shared.addEventAttendee((Attendee) user, event);
                        refresh();
                    } catch (EventService.EventException | RoomService.RoomException e) {
                        System.out.println("Failed to sign up: " + e.getMessage());
                    }
                }));
            }
        } else if (user instanceof Organizer) {
            actions.add(new EventAction("Change speaker", () -> {
                // TODO: present new view
                System.out.println("Changing speaker");
            }));

            actions.add(new EventAction("Cancel event", () -> {
                // TODO: cancel event after use case is done
                System.out.println("Cancelling event");
            }));
        }

        return actions;
    }

    public static class EventAction {
        private final String name;
        private final EventActionCallback callback;

        public EventAction(String name, EventActionCallback callback) {
            this.name = name;
            this.callback = callback;
        }

        public String getName() {
            return name;
        }

        public void call() {
            callback.run();
        }
    }

    private interface EventActionCallback {
        void run();
    }

    public static class EventTableColumn {
        private final String name;
        private final String field;

        public EventTableColumn(String name, String field) {
            this.name = name;
            this.field = field;
        }

        public String getName() {
            return name;
        }

        public String getField() {
            return field;
        }
    }

    public enum EventFilter {
        ALL, SIGNED_UP, GIVING_SPEECH,
    }

    public interface EventView {
        void refreshActionButtons();
        void refreshTableView();

        default void refresh() {
            refreshTableView();
            refreshActionButtons();
        }
    }
}
