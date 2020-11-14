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

    public void validateRoom(int roomNum) throws RoomException {
        if (!rooms.containsKey(roomNum)) throw new RoomDoesNotExistException();
    }

    public Room getRoom(int roomNumber) throws RoomException {
        Room rm = rooms.get(roomNumber);

        if (rm == null) throw new RoomDoesNotExistException();
        return rm;
    }

    public static class RoomException extends Exception {};
    public static class RoomDoesNotExistException extends RoomException {}
}
