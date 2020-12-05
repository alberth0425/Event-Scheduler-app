package entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PanelDiscussion extends Event {
    private List<String> speakerUNs;
    private List<String> panelDiscussionAttendeeUNs;

    public PanelDiscussion(String title, List<String> speakerUNs, int startingTime, int roomNumber, int duration){
        super(title, startingTime, roomNumber, duration);
        this.speakerUNs = speakerUNs;
    }

    public PanelDiscussion(String dataEntry){
        super(dataEntry);
        String[] entries = dataEntry.split(Savable.DELIMITER);
        speakerUNs = new ArrayList<>(Arrays.asList(entries[2].split("\\|")));
        panelDiscussionAttendeeUNs = entries.length < 7 ? new ArrayList<>() :
                new ArrayList<>(Arrays.asList(entries[6].split("\\|")));
    }

    /**
     * add a new attendee to this event.
     * @param attendeeUN the username of this attendee
     */
    @Override
    public void addAttendee(String attendeeUN) {
        panelDiscussionAttendeeUNs.add(attendeeUN);
    }

    /**
     * remove an attendee from this event.
     * @param attendeeUN the username of this attendee
     */
    @Override
    public void removeAttendee(String attendeeUN) {
        panelDiscussionAttendeeUNs.remove(attendeeUN);
    }

    /**
     * set the speaker usernames for this event.
     * @param speakerUNs the username of the speaker for this event
     */
    public void setSpeakerUN(List<String> speakerUNs) {
        this.speakerUNs = speakerUNs;
    }

    /**
     * add a speaker for this event
     * @param speakerUN
     */
    public void addSpeakerUN(String speakerUN){
        speakerUNs.add(speakerUN);
    }

    /**
     * remove a speaker in this event
     * @param speakerUN
     */
    public void removeSpeakerUN(String speakerUN){
        speakerUNs.remove(speakerUN);
    }

    /**
     * get the list of speaker usernames for this panel discussion
     * @return a list of speaker usernames
     */
    public List<String> getSpeakerUNs(){
        return speakerUNs;
    }

    /**
     * get a list of attendees for this panel discussion
     * @return a list of attendees
     */
    public List<String> getAttendeeUNs(){
        return panelDiscussionAttendeeUNs;
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
                ", speakerUNs=" + speakerUNs +
                ", attendeeUNs=" + panelDiscussionAttendeeUNs +
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
                String.join("|",getSpeakerUNs()) + Savable.DELIMITER + getStartingTime() + Savable.DELIMITER + getRoomNumber() +
                Savable.DELIMITER + String.join("|", getAttendeeUNs());
    }

}
