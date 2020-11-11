package src.entities;

public class Event implements Savable {
    private static int eventCount;

    private String title;
    private int startingTime;
    private final int speakerId;
    private final int id;

    public Event(String title, int speakerId, int startingTime) {
        this.title = title;
        this.speakerId = speakerId;

        // Check if start time is valid
        if (startingTime < 9 || startingTime >= 17) {
            throw new RuntimeException("Invalid event starting time: " + startingTime);
        } else {
            this.startingTime = startingTime;
        }

        id = eventCount;
        eventCount += 1;
    }

    public Event(String dataEntry) {
        String[] entries = dataEntry.split(",");
        this.id = Integer.parseInt(entries[0]);
        this.title = entries[1];
        this.speakerId = Integer.parseInt(entries[1]);
        this.startingTime = Integer.parseInt(entries[2]);

        eventCount += 1;
    }

    public int getId() {
        return id;
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
        return getId() + "," + getTitle() + "," + getSpeakerId() + "," + getStartingTime();
    }
}
