package entities;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class Event implements Savable {

    private final String title;
    private int roomNumber;
    private final int startingTime;
    private String speakerUN;
    private List<String> attendeeUNs = new ArrayList<>();
    private String uuid;

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

        uuid = UUID.randomUUID().toString();
    }

    /**
     *  getter for the uuid.
     *
     * @return the uuid of the event
     */
    public String getUUID() {
        return uuid;
    }

    /**
     *  setter for the uuid.
     */
    public void setUUID(String uuid) {
        this.uuid = uuid;
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
     * setter for attendee usernames list, should only be used in PersistenceStorage
     * @param attendeeUNs the list of attendee user names
     */
    public void setAttendeeUNs(List<String> attendeeUNs) {
        this.attendeeUNs = attendeeUNs;
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
                ", uuid=" + uuid +
                '}';
    }

    /**
     * turn the information of this event into a savable string.
     *
     * @return a savable string that contains all the information of this event
     */
    @Override
    public String toSavableString() {
        StringBuilder attendeeUNBuilder = new StringBuilder();
        attendeeUNBuilder.append("[");
        for (String un : attendeeUNs) {
            attendeeUNBuilder.append(MessageFormat.format("\"{0}\",", un));
        }
        // If there are at least 1 attendee, remove the last ","
        if (attendeeUNs.size() >= 1) attendeeUNBuilder.deleteCharAt(attendeeUNBuilder.length() - 1);
        attendeeUNBuilder.append("]");
        String attendeesStr = attendeeUNBuilder.toString();

        return MessageFormat.format("\"room_number\": {0},\"speaker_un\": \"{1}\",\"uuid\": \"{2}\", " +
                "\"attendee_uns\": {3},\"starting_time\": {4},\"title\": \"{5}\"",roomNumber, speakerUN, uuid, attendeesStr, startingTime, title);

    }
}
