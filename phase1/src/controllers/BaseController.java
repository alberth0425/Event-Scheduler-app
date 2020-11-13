package src.controllers;

import src.entities.*;
import src.gateway.PersistenceStorage;
import src.use_cases.AuthService;
import src.use_cases.EventService;
import src.use_cases.MessageService;
import src.use_cases.RoomService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class BaseController {
    Scanner scanner = new Scanner(System.in);

    void load() {
        List<Attendee> attendees = PersistenceStorage.readEntities(PersistenceStorage.ATTENDEE_STORAGE_PATH, Attendee.class);
        List<Organizer> organizers = PersistenceStorage.readEntities(PersistenceStorage.ORGANIZER_STORAGE_PATH, Organizer.class);
        List<Speaker> speakers = PersistenceStorage.readEntities(PersistenceStorage.EVENT_STORAGE_PATH, Speaker.class);

        HashMap<String, User> users = new HashMap<>();
        for (User user : attendees) {
            users.put(user.getUsername(), user);
        }
        for (User user : organizers) {
            users.put(user.getUsername(), user);
        }
        for (User user : speakers) {
            users.put(user.getUsername(), user);
        }
        AuthService.shared.setUsers(users);

        List<Message> messages = PersistenceStorage.readEntities(PersistenceStorage.MESSAGE_STORAGE_PATH, Message.class);

        HashMap<String, List<Message>> messageRepository = new HashMap<>();

        // TODO: initialize contact book?

        for (Message message : messages) {
            String username = message.getReceiverUsername();
            if (messageRepository.containsKey(username)) {
                messageRepository.put(username, new ArrayList<>());
            }
            messageRepository.get(username).add(message);
        }
        MessageService.shared.setMessageRepository(messageRepository);

        List<Event> events = PersistenceStorage.readEntities(PersistenceStorage.EVENT_STORAGE_PATH, Event.class);

        EventService.shared.setAllEvents(events);

        List<Room> rooms = PersistenceStorage.readEntities(PersistenceStorage.ROOM_STORAGE_PATH, Room.class);

        HashMap<Integer, Room> roomHashMap = new HashMap<>();
        for (Room room: rooms) {
            roomHashMap.put(room.getRoomNumber(), room);
        }
        RoomService.shared.setRooms(roomHashMap);
    }

    void save() {
        // TODO ok need getters for use cases too great!!!
    }
}
