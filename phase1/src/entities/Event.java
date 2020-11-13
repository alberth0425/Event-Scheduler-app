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

    public Event(String title, String speakerUN, int startingTime, int roomNumber) {
        this.title = title;
        this.speakerUN = speakerUN;
        this.roomNumber = roomNumber;

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
        this.speakerUN = entries[2];
        this.startingTime = Integer.parseInt(entries[3]);
        this.roomNumber = Integer.parseInt(entries[4]);
        this.attendeeUNs = Arrays.asList(entries[5].split("\\|"));

        eventCount += 1;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return this.title;
    }

    public String getSpeakerUsername() {
        return this.speakerUN;
    }

    public int getStartingTime() {
        return this.startingTime;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public List<String> getAttendeeUNs() {
        return attendeeUNs;
    }

    public void addAttendee(String attendeeUN) {
        attendeeUNs.add(attendeeUN);
    }

    public void removeAttendee(String attendeeUN) {
        attendeeUNs.remove(attendeeUN);
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public void setSpeakerUN(String speakerUN) {
        this.speakerUN = speakerUN;
    }

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

    @Override
    public String toSavableString() {
        return getId() + "," + getTitle() + "," + getSpeakerUsername() + "," + getStartingTime() + "," + getRoomNumber() +
                "," + String.join("|", getAttendeeUNs());
    }
}
