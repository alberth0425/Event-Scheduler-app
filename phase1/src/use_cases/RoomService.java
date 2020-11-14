package src.use_cases;

import src.entities.Room;

import java.util.HashMap;

public class RoomService {
    private HashMap<Integer, Room> rooms = new HashMap<>();

    public final static RoomService shared = new RoomService();
    private RoomService() {}

    public void setRooms(HashMap<Integer, Room> rooms) {
        this.rooms = rooms;
    }

    /**
     * Create a new room with room number and capacity.
     *
     * @param roomNumber room number of the room
     * @param capacity capacity of the room
     * @return true if room was created, otherwise false
     */
    public boolean createRoom(int roomNumber, int capacity) {
        if (rooms.containsKey(roomNumber)) {
            return false;
        } else {
            Room room = new Room(capacity, roomNumber);
            rooms.put(roomNumber, room);
            return true;
        }
    }

    public Room getRoom(int roomNumber) {
        return rooms.get(roomNumber);
    }
}
