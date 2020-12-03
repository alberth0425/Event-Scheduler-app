package use_cases;

import javafx.scene.layout.Pane;
import entities.*;

import java.lang.reflect.Array;
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
    public void setTalkSpeaker(Speaker newSpeaker, Event event) throws EventException {
        // Check that the event exists, i.e., is in allEvents List and the event is not a party
        validateEvent(event);
        checkEventIsTalk(event);

        // Check for double booking
        for (Event e : this.getEventsByStartTime(event.getStartingTime())) {
            if (e instanceof Talk) {
                Talk talk = (Talk) e;
                checkDoubleBookSpeakerTalk(talk, newSpeaker);
            } else if(e instanceof  PanelDiscussion){
                PanelDiscussion pd = (PanelDiscussion) e;
                checkDoubleBookSpeakerPD(pd, newSpeaker);
            }
        }
        Talk t = (Talk) event;
        t.setSpeakerUN(newSpeaker.getUsername());
    }

    public void addSpeakerToPD(Speaker newSpeaker, Event event) throws EventException {
        //check that event exists
        validateEvent(event);
        //check that event is a panel discussion
        checkEventIsPD(event);

        // Check for double booking
        for (Event e : this.getEventsByStartTime(event.getStartingTime())) {
            if (e instanceof Talk) {
                Talk talk = (Talk) e;
                checkDoubleBookSpeakerTalk(talk, newSpeaker);
            } else if(e instanceof PanelDiscussion){
                PanelDiscussion pd = (PanelDiscussion) e;
                checkDoubleBookSpeakerPD(pd, newSpeaker);
            }
        }
        //assign the speaker to event
        PanelDiscussion pd = (PanelDiscussion) event;
        pd.addSpeakerUN(newSpeaker.getUsername());

    }

    public void setPDSpeaker(List<Speaker> newSpeakers, Event event) throws EventException {
        //check that event exists
        validateEvent(event);
        //check that event is a panel discussion
        checkEventIsPD(event);

        //check for double booking
        for (Event e : this.getEventsByStartTime(event.getStartingTime())) {
            if (e instanceof Talk) {
                Talk talk = (Talk) e;
                for (Speaker speaker: newSpeakers) {
                    checkDoubleBookSpeakerTalk(talk, speaker);
                }
            }
            else if(e instanceof PanelDiscussion){
                PanelDiscussion pd = (PanelDiscussion) e;
                for (Speaker speaker: newSpeakers) {
                    checkDoubleBookSpeakerPD(pd, speaker);
                }
            }
        }
        PanelDiscussion pd = (PanelDiscussion) event;
        List<String> speakerUNs = new ArrayList<>();
        for (Speaker sp: newSpeakers){
            speakerUNs.add(sp.getUsername());
        }
        pd.setSpeakerUN(speakerUNs);
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
        List<Event> speakerEvents = new ArrayList<>();
        List<Event> resultEvents = new ArrayList<>();

        //get list of events with speakers
        for (Event e: allEvents){
            if (e instanceof Talk | e instanceof PanelDiscussion)
                speakerEvents.add(e);
        }

        for (Event event: speakerEvents){
            if (event instanceof Talk){
                if (((Talk) event).getSpeakerUsername().equals(username))
                    resultEvents.add(event);
            }
            //match the speaker with
            else if(event instanceof PanelDiscussion){
                for (String speakerUN: ((PanelDiscussion) event).getSpeakerUNs()){
                    if (speakerUN.equals(username)){
                        resultEvents.add(event);
                        break;
                    }
                }
            }
        }
        return resultEvents;
    }


    /**
     * Create a new talk, add the talk to allEvents List.
     *
     * @param title title of the event
     * @param startingTime starting time of the event
     * @param speaker speaker of the event
     * @param room room of the event
     * @return the created event object
     * @throws SpeakerDoubleBookException if the input speaker is already scheduled to another event at the same time
     * @throws RoomDoubleBookException if the input room is already scheduled to another event at the same time
     */
    public void createTalk(String title, int startingTime, Speaker speaker, Room room, int duration)
            throws EventException, RoomService.RoomException {
        //check for all events starting at startingTime
        for (Event event : this.getEventsByStartTime(startingTime)) {
            //check for double booking of room
            if (getRoom(event.getRoomNumber()).getRoomNumber() == room.getRoomNumber()) throw new
                    RoomDoubleBookException();
            //check for double booking of speaker
            if (event instanceof Talk) {
                Talk talk = (Talk) event;
                checkDoubleBookSpeakerTalk(talk, speaker);
            } else if(event instanceof PanelDiscussion){
                PanelDiscussion pd = (PanelDiscussion) event;
                checkDoubleBookSpeakerPD(pd, speaker);
            }
        }

        // Check event starting time
        if (startingTime < 9 || startingTime >= 17) throw new InvalidEventTimeException();
        // create talk
        Event talk = new Talk(title, speaker.getUsername(), startingTime, room.getRoomNumber(), duration);
        allEvents.add(talk);
    }

    /**
     * Create a new panel discussion, add the panel discussion to allEvents List.
     * @param title
     * @param startingTime
     * @param speakers
     * @param room
     * @throws EventException
     * @throws RoomService.RoomException
     */
    public void createPD(String title,int startingTime, List<Speaker> speakers, Room room, int duration)
            throws EventException, RoomService.RoomException{
        //check for all events starting at startingTime
        for (Event event : this.getEventsByStartTime(startingTime)) {
            //check for double booking of room
            if (getRoom(event.getRoomNumber()).getRoomNumber() == room.getRoomNumber()) throw new
                    RoomDoubleBookException();
            //check for double booking of speaker
            if (event instanceof Talk) {
                Talk talk = (Talk) event;
                for (Speaker speaker: speakers){
                    checkDoubleBookSpeakerTalk(talk, speaker);
                }
            } else if(event instanceof PanelDiscussion){
                PanelDiscussion pd = (PanelDiscussion) event;
                for (Speaker speaker: speakers) {
                    checkDoubleBookSpeakerPD(pd, speaker);
                }
            }
        }

        // Check event starting time
        if (startingTime < 9 || startingTime >= 17) throw new InvalidEventTimeException();
        List<String> speakerUNs = getListOfUNsBySpeakers(speakers);
        Event event = new PanelDiscussion(title, speakerUNs, startingTime, room.getRoomNumber(), duration);
        allEvents.add(event);
    }

    /**
     * creates a party and put it to the allEvents list
     * @param title
     * @param startingTime
     * @param room
     * @throws EventException
     * @throws RoomService.RoomException
     */
    public void createParty(String title, int startingTime, Room room, int duration)
            throws EventException, RoomService.RoomException{
        //check for all events starting at startingTime
        for (Event event : this.getEventsByStartTime(startingTime)){
            //check for double booking of room
            if (getRoom(event.getRoomNumber()).getRoomNumber() == room.getRoomNumber())
                throw new RoomDoubleBookException();
        }

        //Check event starting time
        if (startingTime < 9 || startingTime >= 17) throw new InvalidEventTimeException();
        Event party = new Party(title, startingTime, room.getRoomNumber(), duration);
        allEvents.add(party);
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

    private void checkEventIsTalk(Event event) throws EventException{
        if (event instanceof Party | event instanceof PanelDiscussion)
            throw new NotATalkException();
    }

    private void checkEventIsPD(Event event) throws EventException{
        if (event instanceof Party | event instanceof Talk)
            throw new NotAPanelDiscussionException();
    }

    /**
     * check for double booking of a speaker for a talk
     * @param talk
     * @param newSpeaker
     * @throws EventException
     */
    private void checkDoubleBookSpeakerTalk(Talk talk, Speaker newSpeaker) throws EventException {
        if (talk.getSpeakerUsername().equals(newSpeaker.getUsername()))
            throw new SpeakerDoubleBookException();
    }

    /**
     * check for double booking of a speaker for a panel discussion
     * @param pd
     * @param newSpeaker
     * @throws EventException
     */
    private void checkDoubleBookSpeakerPD(PanelDiscussion pd, Speaker newSpeaker)
            throws EventException {
        for (String speakerUN: pd.getSpeakerUNs()){
            if (speakerUN.equals(newSpeaker.getUsername())){
                throw new SpeakerDoubleBookException();
            }
        }
    }

    /**
     * Private helper for getting a list of speaker usernames
     * @param speakers
     * @return
     */
    private List<String> getListOfUNsBySpeakers(List<Speaker> speakers){
        List<String> res = new ArrayList<>();
        for (Speaker speaker: speakers){
            res.add(speaker.getUsername());
        }
        return res;
    }

    // --- Custom Exceptions ---

    public static class EventException extends Exception {}
    public static class InvalidEventTimeException extends EventException {}
    public static class EventDoesNotExistException extends EventException {}
    public static class RoomDoubleBookException extends EventException {}
    public static class SpeakerDoubleBookException extends EventException {}
    public static class RoomFullException extends EventException {}
    public static class AttendeeScheduleConflictException extends EventException {}
    public static class NotATalkException extends EventException{}
    public static class NotAPanelDiscussionException extends EventException{}
    public static class NotEnoughSpeakersException extends EventException{}
    public static class CannotAddSpeakerToPartyException extends EventException{}
}
