package entities;

import java.text.MessageFormat;

public class Room implements Savable {

    private final int capacity;
    private final int roomNumber;

    /**
     * constructor for room class.
     *
     * @param capacity the capacity of the room: how many attendee this room can take
     * @param roomNumber the number of the room
     */
    public Room(int capacity, int roomNumber) {
        this.capacity = capacity;
        this.roomNumber = roomNumber;
    }

    /**
     * getter for capacity.
     *
     * @return the capacity of the room
     */
    public int getCapacity(){
        return capacity;
    }

    /**
     * getter for room number.
     *
     * @return the room number of the room
     */
    public int getRoomNumber(){
        return roomNumber;
    }

    @Override
    public String toString() {
        return "Room{" +
                "capacity=" + capacity +
                ", roomNumber=" + roomNumber +
                '}';
    }

    /**
     * turn all the information for this room into a savable string.
     *
     * @return a savable string contains the room number, delimiter and capacity of the room
     */
    @Override
    public String toSavableString() {
        return MessageFormat.format("\"room_number\": {0},\"capacity\": {1}",roomNumber, capacity);

    }
}
