package entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PanelDiscussion extends Event {
    private List<String> speakerUNs;

    public PanelDiscussion(String title, List<String> speakerUNs, int startingTime, int roomNumber, int duration,
                           int capacity){
        super(title, startingTime, roomNumber, duration, capacity);
        this.speakerUNs = speakerUNs;
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
     * @param speakerUN the username of the speaker for this event
     */
    public void addSpeakerUN(String speakerUN){
        speakerUNs.add(speakerUN);
    }

    /**
     * remove a speaker in this event
     * @param speakerUN the username of the speaker for this event
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
}
