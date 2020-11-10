package src.entities;

import java.util.List;

public class Event {
    private String title;
    private final int speakerId;
    private int startingTime;

    public Event(String title, int speakerId, int startingTime) {
        this.title = title;
        this.speakerId = speakerId;

        // Check if start time is valid
        if (startingTime < 9 || startingTime >= 17) {
            throw new RuntimeException("Invalid event starting time: " + startingTime);
        } else {
            this.startingTime = startingTime;
        }
    }

    public String getTitle() {
        return this.title;
    }

    public int getSpeakerId() {
        return this.speakerId;
    }

    public int getStartingTime() {
        return this.startingTime;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setStartingTime(int startingTime) {
        this.startingTime = startingTime;
    }
}
