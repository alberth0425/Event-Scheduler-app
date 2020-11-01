package src.entities;

import java.util.List;

public class Event {
    private String title;
    private int speakerId;
    private int roomNumber;
    private int startingTime;
    private List<Integer> eventAttendees;

    public Event(String title, int speakerId, int roomNumber, int startingTime){
        this.title = title;
        this.speakerId = speakerId;
        this.roomNumber = roomNumber;
        this.startingTime = startingTime;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public int getSpeakerId(){
        return this.speakerId;
    }

    public int getRoomNumber(){
        return this.roomNumber;
    }

    public int getStartingTime(){
        return this.startingTime;
    }

    public List<Integer> getEventAttendees(){
        return eventAttendees;
    }
}
