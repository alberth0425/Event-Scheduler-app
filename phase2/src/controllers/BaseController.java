package controllers;

import entities.*;
import gateway.PersistenceStorage;
import use_cases.AuthService;
import use_cases.EventService;
import use_cases.MessageService;
import use_cases.RoomService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BaseController {

    void load() {
        List<Attendee> attendees = PersistenceStorage.readEntities(PersistenceStorage.ATTENDEE_STORAGE_PATH, Attendee.class);
        List<Organizer> organizers = PersistenceStorage.readEntities(PersistenceStorage.ORGANIZER_STORAGE_PATH, Organizer.class);
        List<Speaker> speakers = PersistenceStorage.readEntities(PersistenceStorage.SPEAKER_STORAGE_PATH, Speaker.class);

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
            if (!messageRepository.containsKey(username)) {
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
        // save all event to the storage
        List<Savable> events = new ArrayList<>(EventService.shared.getAllEvents());
        PersistenceStorage.saveEntities(events,PersistenceStorage.EVENT_STORAGE_PATH);

        // save all users to the storage
        List<User> users = new ArrayList<>(AuthService.shared.getAllUsers());
        List<Savable> attendees = new ArrayList<>();
        List<Savable> organizers = new ArrayList<>();
        List<Savable> speakers = new ArrayList<>();
        for (User u : users) {
            if (u instanceof Attendee) {
                attendees.add(u);
            } else if (u instanceof Organizer) {
                organizers.add(u);
            } else if (u instanceof Speaker) {
                speakers.add(u);
            }
        }
        PersistenceStorage.saveEntities(attendees,PersistenceStorage.ATTENDEE_STORAGE_PATH);
        PersistenceStorage.saveEntities(organizers,PersistenceStorage.ORGANIZER_STORAGE_PATH);
        PersistenceStorage.saveEntities(speakers,PersistenceStorage.SPEAKER_STORAGE_PATH);

        // save all rooms to the storage
        List<Savable> rooms = new ArrayList<>(RoomService.shared.getAllRooms());
        PersistenceStorage.saveEntities(rooms,PersistenceStorage.ROOM_STORAGE_PATH);

        // save all messages to the storage
        List<Savable> messages = new ArrayList<>(MessageService.shared.getAllMessages());
        PersistenceStorage.saveEntities(messages,PersistenceStorage.MESSAGE_STORAGE_PATH);
    }
}
