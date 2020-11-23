package use_cases;

import entities.Room;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RoomService {
    private HashMap<Integer, Room> rooms = new HashMap<>();

    /**
     * singleton implementation.
     */
    public final static RoomService shared = new RoomService();
    private RoomService() {}

    /**
     * Initialize the rooms HashMap.
     *
     * @param rooms HashMap mapping room number to room
     */
    public void setRooms(HashMap<Integer, Room> rooms) {
        this.rooms = rooms;
    }

    /**
     * Get an ArrayList containing all rooms.
     *
     * @return the list of rooms
     */
    public List<Room> getAllRooms() {
        return new ArrayList<>(this.rooms.values());
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

    /**
     * Validate that the room with given room number exists.
     *
     * @param roomNum the room number
     * @throws RoomException if the room does not exist
     */
    public void validateRoom(int roomNum) throws RoomException {
        if (!rooms.containsKey(roomNum)) throw new RoomDoesNotExistException();
    }

    /**
     * Get the room with the given room number.
     *
     * @param roomNumber room number of the room
     * @return the room with the given room number
     * @throws RoomException if the room does not exist
     */
    public Room getRoom(int roomNumber) throws RoomException {
        Room rm = rooms.get(roomNumber);

        if (rm == null) throw new RoomDoesNotExistException();
        return rm;
    }

    public static class RoomException extends Exception {}
    public static class RoomDoesNotExistException extends RoomException {}
}
