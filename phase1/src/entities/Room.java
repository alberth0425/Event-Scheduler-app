package src.entities;

public class Room {

    private int capacity;
    private int roomNumber;

    public Room(int roomNumber){
        this.capacity = 2;
        this.roomNumber = roomNumber;
    }

    public int getCapacity(){
        return capacity;
    }

    public int getRoomNumber(){
        return roomNumber;
    }
}
