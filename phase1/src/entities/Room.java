package src.entities;

public class Room {

    private final int capacity;
    private final int roomNumber;

    public Room(int capacity, int roomNumber) {
        this.capacity = capacity;
        this.roomNumber = roomNumber;
    }

    public int getCapacity(){
        return capacity;
    }

    public int getRoomNumber(){
        return roomNumber;
    }
}
