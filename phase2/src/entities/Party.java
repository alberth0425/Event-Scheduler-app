package entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Party extends Event{
    private List<String> partyAttendeeUNs = new ArrayList<>();

    public Party(String title, int startingTime, int roomNumber, int duration, int capacity){
        super(title, startingTime, roomNumber, duration, capacity);
    }

    public Party(String dataEntry){
        String[] entries = dataEntry.split(Savable.DELIMITER);
        id = Integer.parseInt(entries[0]);
        title = entries[1];
        startingTime = Integer.parseInt(entries[3]);
        roomNumber = Integer.parseInt(entries[4]);
        duration = Integer.parseInt(entries[5]);
        eventCount += 1;
        capacity = Integer.parseInt(entries[6]);
        partyAttendeeUNs = entries.length < 8 ? new ArrayList<>() :
                new ArrayList<>(Arrays.asList(entries[7].split("\\|")));
    }

    /**
     *  getter for the attendees' usernames.
     *
     * @return the username of all the attendees of this event
     */
    @Override
    public List<String> getAttendeeUNs() {
        return partyAttendeeUNs;
    }

    /**
     * add a new attendee to this event.
     * @param attendeeUN the username of this attendee
     */
    @Override
    public void addAttendee(String attendeeUN){
        partyAttendeeUNs.add(attendeeUN);
    }

    /**
     * remove a new attendee to this event.
     * @param attendeeUN the username of this attendee
     */
    @Override
    public void removeAttendee(String attendeeUN){
        partyAttendeeUNs.remove(attendeeUN);
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
                ", attendeeUNs=" + partyAttendeeUNs +
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
                "null" + Savable.DELIMITER + getStartingTime() + Savable.DELIMITER + getRoomNumber() +
                Savable.DELIMITER + duration + Savable.DELIMITER + getCapacity() +
                Savable.DELIMITER + String.join("|", getAttendeeUNs());
    }
}
