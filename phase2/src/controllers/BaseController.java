package controllers;

import entities.*;
import gateway.PersistenceStorage;
import use_cases.AuthService;
import use_cases.EventService;
import use_cases.MessageService;
import use_cases.RoomService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BaseController {

    void load() {
        // Initialize entity lists
        List<User> users = new ArrayList<>();
        List<Message> messages = new ArrayList<>();
        List<Event> events = new ArrayList<>();
        List<Room> rooms = new ArrayList<>();
        try {
            users = PersistenceStorage.getRequest(User.class);
            messages = PersistenceStorage.getRequest(Message.class);
            events = PersistenceStorage.getRequest(Event.class);
            rooms = PersistenceStorage.getRequest(Room.class);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        // Create users HashMap and set to AuthService
        HashMap<String, User> usersMap = new HashMap<>();
        for (User user : users) {
            usersMap.put(user.getUsername(), user);
        }
        AuthService.shared.setUsers(usersMap);

        // Create messages Hashmap and set to MessageService
        HashMap<String, List<Message>> messageRepository = new HashMap<>();
        for (Message message : messages) {
            String username = message.getReceiverUsername();
            System.out.println("Hi");
            if (!messageRepository.containsKey(username)) {
                messageRepository.put(username, new ArrayList<>());
            }
            messageRepository.get(username).add(message);
        }
        MessageService.shared.setMessageRepository(messageRepository);

        // Store events into EventService
        EventService.shared.setAllEvents(events);

        // Create rooms Hashmap and set to RoomService
        HashMap<Integer, Room> roomsMap = new HashMap<>();
        for (Room room: rooms) {
            roomsMap.put(room.getRoomNumber(), room);
        }
        RoomService.shared.setRooms(roomsMap);
    }

    void save() {
        // get lists of to-be-saved entities
        List<Event> events = EventService.shared.getAllEvents();
        List<User> users = AuthService.shared.getAllUsers();
        List<Room> rooms = RoomService.shared.getAllRooms();
        List<Message> messages = MessageService.shared.getAllMessages();

        // save lists of entities to remote DB
        try {
            PersistenceStorage.putRequest(events, Event.class);
            PersistenceStorage.putRequest(users, User.class);
            PersistenceStorage.putRequest(rooms, Room.class);
            PersistenceStorage.putRequest(messages, Message.class);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
