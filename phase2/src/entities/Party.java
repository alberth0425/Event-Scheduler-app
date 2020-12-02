package entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Party extends Event{
    private List<String> partyAttendeeUNs = new ArrayList<>();

    public Party(String title, int startingTime, int roomNumber, int duration){
        super(title, startingTime, roomNumber, duration);
    }

    public Party(String dataEntry){
        super(dataEntry);
        String[] entries = dataEntry.split(Savable.DELIMITER);
        partyAttendeeUNs = entries.length < 7 ? new ArrayList<>() :
                new ArrayList<>(Arrays.asList(entries[6].split("\\|")));
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
                Savable.DELIMITER + String.join("|", getAttendeeUNs());
    }
}
