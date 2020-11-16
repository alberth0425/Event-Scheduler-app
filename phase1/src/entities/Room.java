package src.entities;

public class Room implements Savable {

    private final int capacity;
    private final int roomNumber;

    public Room(int capacity, int roomNumber) {
        this.capacity = capacity;
        this.roomNumber = roomNumber;
    }

    public Room(String dataEntry) {
        String[] entries = dataEntry.split(Savable.DELIMITER);
        this.capacity = Integer.parseInt(entries[1]);
        this.roomNumber = Integer.parseInt(entries[0]);
    }

    public int getCapacity(){
        return capacity;
    }

    public int getRoomNumber(){
        return roomNumber;
    }

    @Override
    public String toSavableString() {
        return roomNumber + Savable.DELIMITER + capacity;
    }
}
