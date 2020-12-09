package ui.event;

import entities.*;
import use_cases.AuthService;
import use_cases.EventService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        if (event instanceof Talk){
            return EventService.shared.castToTalk(event).getSpeakerUsername();
        }
        else if(event instanceof PanelDiscussion){
            List<String> speakerUNs = EventService.shared.castToPD(event).getSpeakerUNs();
            return speakerUNs.toString();
        }
        else{
            return "No speakers in parties";
        }
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
    public String getStartTime() {
        return Integer.toString(event.getStartingTime());
    }

    /**
     * Get the end time of the event
     * @return the end time in string of the event
     */
    public String getEndTime(){
        return Integer.toString(event.getEndTime());
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
     * Get the rate of the speaker.
     *
     * @return the rate of the speaker
     */
    public String getRate() throws AuthService.AuthException {
        if (event instanceof Talk) {
            Speaker speaker = (Speaker) AuthService.shared.getUserByUsername(
                    EventService.shared.castToTalk(event).getSpeakerUsername());
            return Double.toString(speaker.getAverageRate());
        }
        return "NaN";
    }

    /**
     *
     * @return The list of ratings for the speakers in this panel discussion
     * @throws AuthService.AuthException
     */
    public List<String> getRates() throws AuthService.AuthException{
        if (event instanceof PanelDiscussion){
            List<Speaker> speakers = new ArrayList<>();
            List<String> ratings = new ArrayList<>();
            PanelDiscussion pd = EventService.shared.castToPD(event);
            //get the list of speakers
            for (String s: pd.getSpeakerUNs()){
                Speaker speaker = (Speaker) AuthService.shared.getUserByUsername(s);
                speakers.add(speaker);
            }
            //get the list of speaker ratings
            for (Speaker s: speakers){
                ratings.add(Double.toString(s.getAverageRate()));
            }
            return ratings;
        }
        return Arrays.asList("Event not a Panel Discussion");
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
