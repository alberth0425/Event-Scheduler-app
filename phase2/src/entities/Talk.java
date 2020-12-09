package entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Talk extends Event {
    //private static int eventCount;
    private String speakerUN;
    //private final int id; moved to super class

    /**
     * constructor for the event class.
     *
     * @param title the title of this event
     * @param speakerUN the speaker username of this event
     * @param startingTime the starting time of this event
     * @param roomNumber the room number of the room that this event is going to happen
     */
    public Talk(String title, String speakerUN, int startingTime, int roomNumber, int duration, int capacity) {
        super(title, startingTime, roomNumber, duration, capacity);
        this.speakerUN = speakerUN;
    }

    /**
     *  getter for the speaker username.
     *
     * @return the username of the speaker for this event
     */
    public String getSpeakerUsername() {
        return this.speakerUN;
    }

    @Override
    public List<String> getSpeakerUNs() {
        List<String> list = new ArrayList<>();
        list.add(speakerUN);
        return list;
    }

    /**
     * setter for the speaker username.
     * @param speakerUN the username of the speaker for this event
     */
    public void setSpeakerUN(String speakerUN) {
        this.speakerUN = speakerUN;
    }
}
