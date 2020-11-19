package src.use_cases;

import src.entities.Attendee;
import src.entities.Event;
import src.entities.Room;
import src.entities.Speaker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

// TODO: double check this

public class EventService {

    /**
     * singleton implementation.
     */
    public static EventService shared = new EventService();
    private EventService() {}

    private List<Event> allEvents = new ArrayList<>();

    /**
     * Setter for allEvents.
     *
     * @param allEvents a list that contains all of the events
     */
    public void setAllEvents(List<Event> allEvents) {
        this.allEvents = allEvents;
    }

    private Room getRoom(int roomNumber) throws RoomService.RoomException {
        return RoomService.shared.getRoom(roomNumber);
    }

    /**
     * Add an attendee to an event.
     *
     * @param attendee the attendee
     * @param event the event
     * @throws EventException if the event does not exists, i.e., is not in allEvents List
     */
    public void addEventAttendee(Attendee attendee, Event event) throws EventException, RoomService.RoomException {
        // Check that the event exists, i.e., is in allEvents List
        validateEvent(event);

        Room room = getRoom(event.getRoomNumber());

        // Check if event room is already full
        int capacity = room.getCapacity();
        int occupancy = event.getAttendeeUNs().size();
        if (capacity <= occupancy) throw new RoomFullException();

        // Check if attendee has another event at the same time
        for (Event e : this.getEventsByStartTime(event.getStartingTime())) {
            if (e.getId() != event.getId()) {
                for (String a : event.getAttendeeUNs()) {
                    if (a.equals(attendee.getUsername())) throw new AttendeeScheduleConflictException();
                }
            }
        }

        event.addAttendee(attendee.getUsername());
    }

    /**
     * Remove an attendee from an event.
     *
     * @param attendee the attendee
     * @param event the event
     * @throws EventException if the event does not exists, i.e., is not in allEvents List
     */
    public void removeEventAttendee(Attendee attendee, Event event) throws EventException {
        // Check that the event exists, i.e., is in allEvents List
        validateEvent(event);

        event.removeAttendee(attendee.getUsername());
    }

    /**
     * Set a room to an event.
     *
     * @param newRoom the room that will be assigned to the event
     * @param event the event
     * @throws EventException if the event does not exists, i.e., is not in allEvents List
     */
    public void setEventRoom(Room newRoom, Event event) throws EventException {
        // Check that the event exists, i.e., is in allEvents List
        validateEvent(event);

        // Check for double booking
        for (Event e : this.getEventsByStartTime(event.getStartingTime())) {
            if (e.getRoomNumber() == newRoom.getRoomNumber() && e.getId() != event.getId())
                throw new RoomDoubleBookException();
        }

        event.setRoomNumber(newRoom.getRoomNumber());
    }

    /**
     * Set a speaker to an event.
     *
     * @param newSpeaker the speaker that will be assigned to the event
     * @param event the event
     * @throws EventException if the event does not exists, i.e., is not in allEvents List
     */
    public void setEventSpeaker(Speaker newSpeaker, Event event) throws EventException {
        // Check that the event exists, i.e., is in allEvents List
        validateEvent(event);

        // Check for double booking
        for (Event e : this.getEventsByStartTime(event.getStartingTime())) {
            if (e.getSpeakerUsername().equals(newSpeaker.getUsername()) && e.getId() != event.getId())
                throw new SpeakerDoubleBookException();
        }

        event.setSpeakerUN(newSpeaker.getUsername());
    }

    /**
     * Get all events stored.
     *
     * @return a list of events
     */
    public List<Event> getAllEvents() {
        return allEvents;
    }

    /**
     * Get all events that start at the input time.
     *
     * @param time the starting time
     * @return a list of events that start at the input time.
     */
    public List<Event> getEventsByStartTime(int time) {
        List<Event> events = new ArrayList<>();
        for (Event event : allEvents) {
            if (event.getStartingTime() == time) events.add(event);
        }

        return events;
    }

    /**
     * Get an event by input id.
     *
     * @param id id of the event
     * @return the event represented by the input id
     * @throws EventException if all stored events do not have the input id
     */
    public Event getEventById(Integer id) throws EventException {
        for (Event event: allEvents) {
            if (id == event.getId()) return event;
        }

        throw new EventDoesNotExistException();
    }

    /**
     * Get the list of events that a user signed up for.
     *
     * @param username the username of the attendee
     * @return the list of events that the user signed up for
     */
    public List<Event> getEventsWithAttendee(String username) {
        List<Event> events = new ArrayList<>();
        for (Event event : allEvents) {
            if (event.getAttendeeUNs().contains(username)) {
                events.add(event);
            }
        }

        return events;
    }

    /**
     * get the list of events that a speaker is speaking at
     * @param username
     * @return
     */
    public List<Event> getEventsBySpeaker(String username){
        List<Event> events = new ArrayList<>();
        for (Event event: allEvents){
            if (event.getSpeakerUsername().equals(username))
                events.add(event);
        }

        return events;
    }


    /**
     * Create a new event, add the event object to allEvents List, and return the event object.
     *
     * @param title title of the event
     * @param startingTime starting time of the event
     * @param speaker speaker of the event
     * @param room room of the event
     * @return the created event object
     * @throws SpeakerDoubleBookException if the input speaker is already scheduled to another event at the same time
     * @throws RoomDoubleBookException if the input room is already scheduled to another event at the same time
     */
    public Event createEvent(String title, int startingTime, Speaker speaker, Room room) throws EventException,
            RoomService.RoomException {
        // Check double booking exceptions (both speaker and room)
        for (Event event : this.getEventsByStartTime(startingTime)) {
            if (event.getSpeakerUsername().equals(speaker.getUsername())) throw new SpeakerDoubleBookException();
            if (getRoom(event.getRoomNumber()).getRoomNumber() == room.getRoomNumber()) throw new
                    RoomDoubleBookException();
        }

        // Check event starting time
        if (startingTime < 9 || startingTime >= 17) throw new InvalidEventTimeException();

        Event event = new Event(title, speaker.getUsername(), startingTime, room.getRoomNumber());
        allEvents.add(event);

        return event;
    }

    /**
     * Get the number of empty seats of a given event.
     *
     * @param event the event
     * @return number of seats that are empty in the event's room
     */
    public int getEventAvailability(Event event) throws RoomService.RoomException {
        Room room = getRoom(event.getRoomNumber());
        return room.getCapacity() - event.getAttendeeUNs().size();
    }

    // --- Private helpers ---

    /**
     * Check if an event is in allEvents list, if not, throw an exception.
     *
     * @param event the event
     * @throws EventException if the event is not in allEvents list
     */
    private void validateEvent(Event event) throws EventException {
        if (!allEvents.contains(event)) throw new EventDoesNotExistException();
    }

    // --- Custom Exceptions ---

    public static class EventException extends Exception {}
    public static class InvalidEventTimeException extends EventException {}
    public static class EventDoesNotExistException extends EventException {}
    public static class RoomDoubleBookException extends EventException {}
    public static class SpeakerDoubleBookException extends EventException {}
    public static class RoomFullException extends EventException {}
    public static class AttendeeScheduleConflictException extends EventException {}

}
