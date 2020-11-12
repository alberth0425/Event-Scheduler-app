package src.use_cases;

import src.entities.Attendee;
import src.entities.Event;
import src.entities.Room;
import src.entities.Speaker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EventService {

    private static HashMap<Integer, List<Attendee>> eventToAttendee;
    private static HashMap<Integer, Room> eventToRoom;
    private static HashMap<Integer, Speaker> eventToSpeaker;
    private static final List<Event> allEvent = new ArrayList<>();

    public HashMap<Integer, List<Attendee>> getEventToAttendee() {
        return eventToAttendee;
    }

    public HashMap<Integer, Room> getEventToRoom() {
        return eventToRoom;
    }

    public void setEventToRoom(Room newRoom, Event event) {
        eventToRoom.put(event.getId(), newRoom);
    }

    public HashMap<Integer, Speaker> getEventToSpeaker() {
        return eventToSpeaker;
    }

    public void setEventToSpeaker(Speaker newSpeaker, Event event) {
        eventToSpeaker.put(event.getId(), newSpeaker);
    }

    public Event getEventById(Integer id) {
        for (Event event: allEvent) {
            if (id == event.getId())return event;
        }
        return null;
    }

    public void addEvent(String title, Speaker speaker, int startingTime, Room room) {
        Event event = new Event(title, speaker.getId(), startingTime);
        getEventToRoom().put(event.getId(), room);
        getEventToSpeaker().put(event.getId(),speaker);
        allEvent.add(event);
    }

    public void addNewAttendee(Attendee attendee, Event event) {
        if (eventToAttendee.containsKey(event.getId())) {
            eventToAttendee.get(event.getId()).add(attendee);
        } else {
            List<Attendee> listOfAttendee = new ArrayList<>();
            listOfAttendee.add(attendee);
            eventToAttendee.put(event.getId(), listOfAttendee);
        }
    }

    public void removeAttendee(Attendee attendee, Event event) {
        eventToAttendee.get(event.getId()).remove(attendee);
    }

}
