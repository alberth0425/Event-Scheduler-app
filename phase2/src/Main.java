import entities.*;
import gateway.PersistenceStorage;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ui.login.LoginViewController;
import ui.navigation.NavigationController;
import use_cases.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        load();

        Scene scene = NavigationController.initialize(LoginViewController.class);
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    // TODO: temporary code, remove when DB is done in persistent storage

    void load() {
        List<Attendee> attendees = PersistenceStorage.readEntities(PersistenceStorage.ATTENDEE_STORAGE_PATH, Attendee.class);
        List<Organizer> organizers = PersistenceStorage.readEntities(PersistenceStorage.ORGANIZER_STORAGE_PATH, Organizer.class);
        List<Speaker> speakers = PersistenceStorage.readEntities(PersistenceStorage.SPEAKER_STORAGE_PATH, Speaker.class);
        List<Rater> raters = PersistenceStorage.readEntities(PersistenceStorage.RATER_STORAGE_PATH, Rater.class);
        List<Talk> talks = PersistenceStorage.readEntities(PersistenceStorage.TALK_STORAGE_PATH, Talk.class);
        List<Party> parties = PersistenceStorage.readEntities(PersistenceStorage.PARTY_STORAGE_PATH, Party.class);
        List<PanelDiscussion> PDs = PersistenceStorage.readEntities(PersistenceStorage.PD_STORAGE_PATH, PanelDiscussion.class);

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
        for (User user : raters) {
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

        //load events
        List<Event> events = new ArrayList<>();
        events.addAll(talks);
        events.addAll(parties);
        events.addAll(PDs);

        EventService.shared.setAllEvents(events);

        List<Room> rooms = PersistenceStorage.readEntities(PersistenceStorage.ROOM_STORAGE_PATH, Room.class);

        HashMap<Integer, Room> roomHashMap = new HashMap<>();
        for (Room room: rooms) {
            roomHashMap.put(room.getRoomNumber(), room);
        }
        RoomService.shared.setRooms(roomHashMap);

        List<Agreement> agreements =
                PersistenceStorage.readEntities(PersistenceStorage.AGREEMENT_STORAGE_PATH, Agreement.class);

        HashMap<String, Agreement> agreementHashMap = new HashMap<>();

        for (Agreement agreement: agreements) {
            agreementHashMap.put(agreement.getUsername(), agreement);
        }
        AgreementService.shared.setAgreements(agreementHashMap);
    }

    void save() {
        // save all events to the storage
        List<Event> events = new ArrayList<>(EventService.shared.getAllEvents());
        List<Savable> talks = new ArrayList<>();
        List<Savable> parties = new ArrayList<>();
        List<Savable> PDs = new ArrayList<>();
        for (Event event: events){
            if (event instanceof Talk){
                talks.add(event);
            } else if(event instanceof Party){
                parties.add(event);
            } else{
                PDs.add(event);
            }
        }
        PersistenceStorage.saveEntities(talks,PersistenceStorage.TALK_STORAGE_PATH);
        PersistenceStorage.saveEntities(parties,PersistenceStorage.PARTY_STORAGE_PATH);
        PersistenceStorage.saveEntities(PDs,PersistenceStorage.PD_STORAGE_PATH);

        // save all users to the storage
        List<User> users = new ArrayList<>(AuthService.shared.getAllUsers());
        List<Savable> attendees = new ArrayList<>();
        List<Savable> organizers = new ArrayList<>();
        List<Savable> speakers = new ArrayList<>();
        List<Savable> raters = new ArrayList<>();

        for (User u : users) {
            if (u instanceof Attendee) {
                attendees.add(u);
            } else if (u instanceof Organizer) {
                organizers.add(u);
            } else if (u instanceof Speaker) {
                speakers.add(u);
            } else if (u instanceof Rater) {
                raters.add(u);
            }
        }
        PersistenceStorage.saveEntities(attendees,PersistenceStorage.ATTENDEE_STORAGE_PATH);
        PersistenceStorage.saveEntities(organizers,PersistenceStorage.ORGANIZER_STORAGE_PATH);
        PersistenceStorage.saveEntities(speakers,PersistenceStorage.SPEAKER_STORAGE_PATH);
        PersistenceStorage.saveEntities(raters, PersistenceStorage.RATER_STORAGE_PATH);

        // save all rooms to the storage
        List<Savable> rooms = new ArrayList<>(RoomService.shared.getAllRooms());
        PersistenceStorage.saveEntities(rooms,PersistenceStorage.ROOM_STORAGE_PATH);

        // save all messages to the storage
        List<Savable> messages = new ArrayList<>(MessageService.shared.getAllMessages());
        PersistenceStorage.saveEntities(messages,PersistenceStorage.MESSAGE_STORAGE_PATH);

        // save all agreements to the storage
        List<Savable> agreements = new ArrayList<>(AgreementService.shared.getAllAgreements());
        PersistenceStorage.saveEntities(agreements,PersistenceStorage.AGREEMENT_STORAGE_PATH);

    }
}
