package ui.event;

import entities.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ui.util.TextFieldPrompt;
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
    protected final EventPresenter.EventView view;

    protected final ObservableList<EventAdapter> eventList = FXCollections.observableArrayList();

    protected final EventPresenter.EventFilter filter;

    public EventPresenter(EventPresenter.EventView view, TalkPresenter.EventFilter filter){
        this.view = view;
        this.filter = filter;

        refreshEvents();
    }

    protected List<TalkPresenter.EventAction> getAttendeeEventActions(int index) {
        List<TalkPresenter.EventAction> actions = new ArrayList<>();
        Attendee user = (Attendee) AuthService.shared.getCurrentUser();

        if (index != -1) {
            Event event = eventList.get(index).getEvent();

            // TODO: refactor things like this into use case
            if (event.getAttendeeUNs().contains(user.getUsername())) {
                // User signed up for this event
                actions.add(new TalkPresenter.EventAction("Cancel sign up", () -> {
                    try {
                        EventService.shared.removeEventAttendee(user, event);
                        refresh();
                    } catch (EventService.EventException e) {
                        System.out.println("Failed to cancel sign up: " + e.getMessage());
                    }
                }));
            } else {
                actions.add(new TalkPresenter.EventAction("Sign up", () -> {
                    try {
                        EventService.shared.addEventAttendee(user, event);
                        refresh();
                    } catch (EventService.EventException | RoomService.RoomException e) {
                        System.out.println("Failed to sign up: " + e.getMessage());
                    }
                }));
            }
        }

        return actions;
    }

    public static class EventAction {
        protected final String name;
        protected final EventActionCallback callback;

        protected EventAction(String name, EventActionCallback callback) {
            this.name = name;
            this.callback = callback;
        }

        /**
         * Get the name of the event action.
         *
         * @return the name
         */
        public String getName() {
            return name;
        }

        /**
         * Run the event action.
         */
        public void call() {
            callback.run();
        }
    }


    protected void refresh() {
        refreshEvents();
        // Table view is updated automatically when list changes
        view.refreshActionButtons();
    }


    protected void refreshEvents() {
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
                    events = events.filter(event -> event.getSpeakerUNs().contains(currentUsername));
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

    /**
     * Get the list of actions that can be performed by the current user on the event at given index.
     * If index is -1, then get the list of actions that can be performed by this user in general.
     *
     * @param index the index of the selected event, or -1 if nothing is selected
     * @return a list of EventAction
     */
    public List<EventAction> getActionsForEvent(int index) {
        List<EventAction> actions = new ArrayList<>();
        User user = AuthService.shared.getCurrentUser();

        if (user instanceof Attendee) {
            actions.addAll(getAttendeeEventActions(index));
        } else if (user instanceof Organizer) {
            actions.addAll(getOrganizerEventActions(index));
        }

        return actions;
    }


    private List<EventAction> getOrganizerEventActions(int index) {
        List<EventAction> actions = new ArrayList<>();

        actions.add(new EventAction("Create event", view::navigateToCreateEvent));

        if (index != -1) {
            Event event = eventList.get(index).getEvent();

            actions.add(new EventAction("Change speaker", () -> {
                view.displayTextField("Speaker ID", speakerId -> {
                    try {
                        Speaker speaker = (Speaker) AuthService.shared.getUserByUsername(speakerId);
                        EventService.shared.setTalkSpeaker(speaker, event);
                        refresh();
                        return null;

                    } catch (AuthService.AuthException | ClassCastException e) {
                        return "No speaker with ID " + speakerId + ".";
                    } catch (EventService.SpeakerDoubleBookException e) {
                        return "Speaker double book.";
                    } catch (EventService.EventException e) {
                        return "Unknown error when changing speaker.";
                    }
                });
            }));

            actions.add(new EventAction("Change capacity", () -> {
                view.displayTextField("Capacity", capacityStr -> {
                    try {
                        int capacity = Integer.parseInt(capacityStr);
                        EventService.shared.setCapacity(capacity, event);
                        refresh();
                        return null;

                    } catch (NumberFormatException e) {
                        return "Invalid number " + capacityStr;
                    } catch (IllegalArgumentException e) {
                        return "Capacity is either smaller than current number of attendees or exceeds room limit.";
                    }
                });
            }));

            actions.add(new EventAction("Cancel event", () -> {
                // TODO: cancel event after use case is done
                System.out.println("Cancelling event");
            }));
        }

        return actions;
    }

    /**
     * Get the list of events to display.
     *
     * @return a list of EventAdapter
     */
    public ObservableList<EventAdapter> getEventList() {
        return eventList;
    }

    /**
     * Get the list of table columns to display based on the current user type.
     *
     * @return a list of EventTableColumn
     */
    public List<EventTableColumn> getTableColumns() {
        List<EventTableColumn> columns = new ArrayList<>();

        Collections.addAll(columns,
                new EventTableColumn("ID", "id"),
                new EventTableColumn("Title", "title"),
                new EventTableColumn("Speaker", "speaker"),
                new EventTableColumn("Room", "room"),
                new EventTableColumn("Start Time", "startTime"),
                new EventTableColumn("End Time", "endTime"),
                new EventTableColumn("Capacity", "capacity"),
                new EventTableColumn("Speaker rate", "rate")
        );

        User user = AuthService.shared.getCurrentUser();
        if (user instanceof Attendee) {
            columns.add(new EventTableColumn("Signed Up", "signedUp"));
        }

        return columns;
    }

    public enum EventFilter {
        ALL, SIGNED_UP, GIVING_SPEECH,
    }

    public static class EventTableColumn {
        private final String name;
        private final String field;

        protected EventTableColumn(String name, String field) {
            this.name = name;
            this.field = field;
        }

        /**
         * Get the name of the table column.
         *
         * @return the name
         */
        public String getName() {
            return name;
        }

        /**
         * Get the name of the field to display, which corresponds to a getter in EventAdapter.
         *
         * @return the name of the property in EventAdapter
         */
        public String getField() {
            return field;
        }
    }

    protected interface EventActionCallback {
        void run();
    }

    public interface EventView {
        void displayTextField(String promptText, TextFieldPrompt.Validator validator);

        void refreshActionButtons();
        void refreshTableView();

        default void refresh() {
            refreshTableView();
            refreshActionButtons();
        }

        void navigateToCreateEvent();
    }

}
