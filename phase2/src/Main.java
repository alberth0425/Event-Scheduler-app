import entities.*;
import gateway.PersistenceStorage;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ui.login.LoginViewController;
import ui.navigation.NavigationController;
import use_cases.AuthService;
import use_cases.EventService;
import use_cases.MessageService;
import use_cases.RoomService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        load();

        Scene scene = NavigationController.initialize(LoginViewController.class);
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(scene);
        primaryStage.setOnCloseRequest(event -> save());
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static void load() {
        // TODO: save/load different type of events
        // probably need to change read/write for tosavablestring

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

        // TODO: initialize contact book?

        for (Message message : messages) {
            String username = message.getReceiverUsername();
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

    public static void save() {
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
