package ui.event;

import entities.Event;
import entities.Room;

public class EventAdapter {
    private final Event event;
    private final Room room;

    public EventAdapter(Event event, Room room) {
        this.event = event;
        this.room = room;
    }

    public Event getEvent() {
        return event;
    }

    public String getId() {
        return Integer.toString(event.getId());
    }

    public String getTitle() {
        return event.getTitle();
    }

    public String getSpeaker() {
        return event.getSpeakerUsername();
    }

    public String getRoom() {
        return Integer.toString(event.getRoomNumber());
    }

    public String getTime() {
        return Integer.toString(event.getStartingTime());
    }

    public String getCapacity() {
        return event.getAttendeeUNs().size() + " / " + room.getCapacity();
    }
}
