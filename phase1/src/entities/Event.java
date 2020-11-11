package src.entities;

public class Event implements Savable {
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

    public Event(String dataEntry) {
        String[] entries = dataEntry.split(",");
        this.title = entries[0];
        this.speakerId = Integer.parseInt(entries[1]);
        this.startingTime = Integer.parseInt(entries[2]);
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

    @Override
    public String toSavableString() {
        return getTitle() + "," + getSpeakerId() + "," + getStartingTime();
    }
}
