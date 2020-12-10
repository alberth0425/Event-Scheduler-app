import entities.*;
import gateway.PersistenceStorage;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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
        primaryStage.setTitle("Project Phase 2");
        primaryStage.setWidth(800);
        primaryStage.setHeight(600);
        primaryStage.setOnCloseRequest(event -> {
            // Display loading indicator
            primaryStage.getScene().setRoot(createIndicator("Saving data..."));

            new Thread(() -> {
                save();
                Platform.exit();
            }).start();

            // Prevent exit before save is done
            event.consume();
        });

        // Show loading screen
        Scene loadingScene = new Scene(createIndicator("Loading data..."));
        primaryStage.setScene(loadingScene);
        primaryStage.show();

        new Thread(() -> {
            load();
            Platform.runLater(() -> {
                // Show login screen
                try {
                    Scene scene = NavigationController.initialize(LoginViewController.class);
                    primaryStage.setScene(scene);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }).start();
    }

    private Parent createIndicator(String text) {
        ProgressIndicator indicator = new ProgressIndicator();
        Label label = new Label(text);

        VBox vbox = new VBox(indicator, label);
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(10);

        HBox hbox = new HBox(vbox);
        hbox.setAlignment(Pos.CENTER);

        return hbox;
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static void load() {
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
