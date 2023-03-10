package src.entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Event implements Savable {
    private static int eventCount;

    private final String title;
    private int roomNumber;
    private final int startingTime;
    private String speakerUN;
    private List<String> attendeeUNs = new ArrayList<>();
    private final int id;

    private int capacity;

    /**
     * constructor for the event class.
     *
     * @param title the title of this event
     * @param speakerUN the speaker username of this event
     * @param startingTime the starting time of this event
     * @param roomNumber the room number of the room that this event is going to happen
     */
    public Event(String title, String speakerUN, int startingTime, int roomNumber) {
        this.title = title;
        this.speakerUN = speakerUN;
        this.roomNumber = roomNumber;
        this.startingTime = startingTime;

        id = eventCount;
        eventCount += 1;
    }

    /**
     * construct event from a dataEntry.
     *
     * @param dataEntry the savable string that contains all the information of this event
     */
    public Event(String dataEntry) {
        String[] entries = dataEntry.split(DELIMITER);
        this.id = Integer.parseInt(entries[0]);
        this.title = entries[1];
        this.speakerUN = entries[2];
        this.startingTime = Integer.parseInt(entries[3]);
        this.roomNumber = Integer.parseInt(entries[4]);
        this.attendeeUNs = entries.length < 6 ? new ArrayList<>() :
                                                new ArrayList<>(Arrays.asList(entries[5].split("\\|")));

        eventCount += 1;
    }

    /**
     *  getter for the id.
     *
     * @return the id of the event
     */
    public int getId() {
        return id;
    }

    /**
     *  getter for the title.
     *
     * @return the title of the event
     */
    public String getTitle() {
        return this.title;
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
     *  getter for the start time of the event.
     *
     * @return the the start time of the even
     */
    public int getStartingTime() {
        return this.startingTime;
    }

    /**
     *  getter for the room number.
     *
     * @return the room number this event is going to happen
     */
    public int getRoomNumber() {
        return roomNumber;
    }

    /**
     *  getter for the attendees' username.
     *
     * @return the username of all the attndees of this event
     */
    public List<String> getAttendeeUNs() {
        return attendeeUNs;
    }

    public int getCapacity(){
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    /**
     * add a new attendee to this event.
     * @param attendeeUN the username of this attendee
     */
    public void addAttendee(String attendeeUN) {
        attendeeUNs.add(attendeeUN);
    }

    /**
     * remove an attendee from this event.
     * @param attendeeUN the username of this attendee
     */
    public void removeAttendee(String attendeeUN) {
        attendeeUNs.remove(attendeeUN);
    }

    /**
     * setter of the room number.
     * @param roomNumber set the room number of where this event is going to happen
     */
    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
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
                ", attendeeUNs=" + attendeeUNs +
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
        return getId() + DELIMITER + getTitle() + DELIMITER + getSpeakerUsername() + DELIMITER + getStartingTime() + DELIMITER + getRoomNumber() +
                DELIMITER + String.join("|", getAttendeeUNs());
    }
}
