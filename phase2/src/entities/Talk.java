package entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Talk extends Event {
    //private static int eventCount;
    private String speakerUN;
    private List<String> talkAttendeeUNs = new ArrayList<>();
    //private final int id; moved to super class

    /**
     * constructor for the event class.
     *
     * @param title the title of this event
     * @param speakerUN the speaker username of this event
     * @param startingTime the starting time of this event
     * @param roomNumber the room number of the room that this event is going to happen
     */
    public Talk(String title, String speakerUN, int startingTime, int roomNumber, int duration) {
        super(title, startingTime, roomNumber, duration);
        this.speakerUN = speakerUN;
    }

    /**
     * construct event from a dataEntry.
     *
     * @param dataEntry the savable string that contains all the information of this event
     */
    public Talk(String dataEntry) {
        String[] entries = dataEntry.split(Savable.DELIMITER);
        id = Integer.parseInt(entries[0]);
        title = entries[1];
        speakerUN = entries[2];
        startingTime = Integer.parseInt(entries[3]);
        roomNumber = Integer.parseInt(entries[4]);
        duration = Integer.parseInt(entries[5]);
        eventCount += 1;
        talkAttendeeUNs = entries.length < 7 ? new ArrayList<>() :
                new ArrayList<>(Arrays.asList(entries[6].split("\\|")));
    }

    /**
     *  getter for the speaker username.
     *
     * @return the username of the speaker for this event
     */
    public String getSpeakerUsername() {
        return this.speakerUN;
    }


    /**
     *  getter for a list of attendees' usernames.
     *
     * @return a list of username of all the attendees of this event
     */
    @Override
    public List<String> getAttendeeUNs() {
        return talkAttendeeUNs;
    }

    /**
     * add a new attendee to this event.
     * @param attendeeUN the username of this attendee
     */
    @Override
    public void addAttendee(String attendeeUN) {
        talkAttendeeUNs.add(attendeeUN);
    }

    /**
     * remove an attendee from this event.
     * @param attendeeUN the username of this attendee
     */
    @Override
    public void removeAttendee(String attendeeUN) {
        talkAttendeeUNs.remove(attendeeUN);
    }


    /**
     * setter for the speaker username.
     * @param speakerUN the username of the speaker for this event
     */
    public void setSpeakerUN(String speakerUN) {
        this.speakerUN = speakerUN;
    }

    /**
     * turn the information of this event into a string.
     *
     * @return the string that contains all the information of this event
     */
    @Override
    public String toString() {
        return "Event{" +
                "title='" + title + '\'' +
                ", roomNumber=" + roomNumber +
                ", startingTime=" + startingTime +
                ", speakerUN='" + speakerUN + '\'' +
                ", attendeeUNs=" + talkAttendeeUNs +
                ", id=" + id +
                '}';
    }

    /**
     * turn the information of this event into a savable string.
     *
     * @return a savable string that contains all the information of this event
     */
    @Override
    public String toSavableString() {
        return getId() + Savable.DELIMITER + getTitle() + Savable.DELIMITER +
                getSpeakerUsername() + Savable.DELIMITER + getStartingTime() + Savable.DELIMITER + getRoomNumber() +
                Savable.DELIMITER + duration + Savable.DELIMITER + String.join("|", getAttendeeUNs());
    }
}
