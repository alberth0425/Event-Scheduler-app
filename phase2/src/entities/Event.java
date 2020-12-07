package entities;

import java.util.ArrayList;
import java.util.List;

public abstract class Event implements Savable {
    int id;
    static int eventCount;
    String title;
    int startingTime;
    int roomNumber;
    int duration; //must be in full hours
    //double duration;

    public Event(String title, int startingTime, int roomNumber, int duration){
        this.title = title;
        this.startingTime = startingTime;
        this.roomNumber = roomNumber;
        this.duration = duration;
        id = eventCount;
        eventCount += 1;
    }

    public Event(){

    }

    /**
     *  getter for the title.
     *
     * @return the title of the event
     */
    public String getTitle() {
        return this.title;
    }

    /**
     *  getter for the id.
     *
     * @return the id of the event
     */
    public int getId() {
        return id;
    }

    /**
     *  getter for the start time of the event.
     *
     * @return the the start time of the even
     */
    public int getStartingTime() {
        return this.startingTime;
    }

    /**
     *  getter for the room number.
     *
     * @return the room number this event is going to happen
     */
    public int getRoomNumber() {
        return roomNumber;
    }

    /**
     * setter of the room number.
     * @param roomNumber set the room number of where this event is going to happen
     */
    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public abstract List<String> getAttendeeUNs();

    /**
     * An abstract method for adding a new attendee to this event.
     * @param attendeeUN the username of this attendee
     */
    public abstract void addAttendee(String attendeeUN);

    /**
     * abstract method for removing an attendee from this event.
     * @param attendeeUN the username of this attendee
     */

    public abstract void removeAttendee(String attendeeUN);

    public int getEndTime(){
        return startingTime + duration;
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
