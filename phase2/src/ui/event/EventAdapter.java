package ui.event;

import entities.Event;
import entities.Room;

public class EventAdapter {
    private final Event event;
    private final Room room;
    private final String currentUsername;

    /**
     * Construct the event adapter with given event, room, and current username,
     *
     * @param event the event
     * @param room the room
     * @param currentUsername the current username
     */
    public EventAdapter(Event event, Room room, String currentUsername) {
        this.event = event;
        this.room = room;
        this.currentUsername = currentUsername;
    }

    /**
     * Get the event of the adapter.
     *
     * @return the Event instance
     */
    public Event getEvent() {
        return event;
    }

    /**
     * Get the event ID.
     *
     * @return the event ID string
     */
    public String getId() {
        return Integer.toString(event.getId());
    }

    /**
     * Get the title of the event.
     *
     * @return the title of the event
     */
    public String getTitle() {
        return event.getTitle();
    }

    /**
     * Get the speaker of the event.
     *
     * @return string displaying the speaker of the event
     */
    public String getSpeaker() {
        return event.getSpeakerUsername();
    }

    /**
     * Get the room number of the event.
     *
     * @return the room number string of the event
     */
    public String getRoom() {
        return Integer.toString(event.getRoomNumber());
    }

    /**
     * Get the starting time of the event.
     *
     * @return the starting time string of the event
     */
    public String getTime() {
        return Integer.toString(event.getStartingTime());
    }

    /**
     * Get the formatted capacity of the event.
     *
     * @return string displaying capacity of the event
     */
    public String getCapacity() {
        return event.getAttendeeUNs().size() + " / " + event.getCapacity();
    }

    /**
     * Get the string representing whether the current user has signed up.
     *
     * @return string representing whether the current user has signed up
     */
    public String getSignedUp() {
        if (event.getAttendeeUNs().contains(currentUsername)) {
            return "Yes";
        } else {
            return "No";
        }
    }
}
