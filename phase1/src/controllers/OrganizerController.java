package src.controllers;

import src.entities.Message;
import src.entities.Speaker;
import src.entities.User;
import src.use_cases.AuthService;
import src.use_cases.EventService;
import src.use_cases.MessageService;
import src.use_cases.RoomService;

import java.util.List;

public class OrganizerController extends UserController {
    @Override
    void run() {
        while (true) {
            System.out.println("Select an action:");
            System.out.println("1. Browse events");
            System.out.println("2. Create speaker account");
            System.out.println("3. Create new event");
            System.out.println("4. Create new room");
            System.out.println("5. Assign speaker to event");
            System.out.println("6. View messages");
            System.out.println("7. Send messages");
            System.out.println("8. Exit");

            int choice = scanner.nextInt();

            boolean exit = false;

            switch (choice) {
                case 1:
                    browseEvents();
                    break;
                case 2:
                    createSpeaker();
                    break;
                case 3:
                    createEvent();
                    break;
                case 4:
                    createRoom();
                    break;
                case 5:
                    assignSpeakerToEvent();
                    break;
                case 6:
                    viewMessages();
                    break;
                case 7:
                    sendMessages();
                    break;
                case 8:
                    exit = true;
                    break;

                default:
                    System.out.println("Unknown action.");
                    break;
            }

            save();

            if (exit) {
                break;
            }
        }
    }

    void sendMessages() {
        System.out.println("Choose receiver type");
        System.out.println("1. Send message to all users");
        System.out.println("2. Send message to all speakers");
        System.out.println("3. Send message to all attendees");
        System.out.println("4. Send message to all attendees in a specific event");
        System.out.println("5. Send message to a specific user");
        System.out.println("6. Exit");

        int choice = scanner.nextInt();

        boolean exit = false;

        switch (choice) {
            case 1:
                sendMessagesAllUsers();

                break;
            case 2:
                sendMessagesAllSpeakers();

                break;
            case 3:
                sendMessagesAllAttendees();

                break;
            case 4:
                System.out.println("Enter event id:");
                String content = scanner.nextLine();

                try {
                    //check entered content is a digit
                    int eventId = Integer.parseInt(content);

                    //check entered event id's existence
                    EventService.shared.getEventById(eventId);

                    System.out.println("Enter message to send:");
                    String message = scanner.nextLine();
                    sendMessagesAllAttendeesSpecificRoom(message, eventId);

                } catch (NumberFormatException e) {
                    System.out.println("Invalid event id, please enter only digits.");
                } catch (EventService.EventException e) {
                    System.out.println("Event with event id " + content + " does not exist.");
                }

                break;
            case 5:
                System.out.println("Enter receiver username:");
                String receiverUN = scanner.nextLine();

                try {
                    User receiver = AuthService.shared.getUserByUsername(receiverUN);

                    System.out.println("Enter message to send:");
                    String messageContent = scanner.nextLine();

                    MessageService.shared.sendMessage(messageContent, AuthService.shared.getCurrentUser(), receiver);

                } catch (AuthService.UserDoesNotExistException e) {
                    System.out.println("User with username " + receiverUN + " does not exist.");
                } catch (Exception e) {
                    System.out.println("Unknown exception: " + e.toString());
                }

                break;
            case 6:
                exit = true;

                run();

            default:
                System.out.println("Unknown action.");
                run();
        }

    }

    // --- Private helpers ---

    private void sendMessagesAllUsers() {
        System.out.println("Enter message to send:");
        String messageContent = scanner.nextLine();

        for (User user : AuthService.shared.getAllUsers()) {
            MessageService.shared.sendMessage(messageContent, AuthService.shared.getCurrentUser(), user);
        }
    }

    private void sendMessagesAllSpeakers() {
        System.out.println("Enter message to send:");
        String messageContent = scanner.nextLine();

        for (User user : AuthService.shared.getAllUsers()) {
            if (user)
                MessageService.shared.sendMessage(messageContent, AuthService.shared.getCurrentUser(), user);
        }
    }

    private void sendMessagesAllAttendees() {
        System.out.println("Enter message to send:");
        String messageContent = scanner.nextLine();

        for (User user : AuthService.shared.getAllUsers()) {
            if (user)
                MessageService.shared.sendMessage(messageContent, AuthService.shared.getCurrentUser(), user);
        }
    }

    private void sendMessagesAllAttendeesSpecificRoom(String message, int eventId) {

        try {
            for (String attendeeUN : EventService.shared.getEventById(eventId).getAttendeeUNs()) {
                MessageService.shared.sendMessage(message, AuthService.shared.getCurrentUser(),
                        AuthService.shared.getUserByUsername(attendeeUN));
            }

        } catch (Exception e) {
            System.out.println("Unknown exception: " + e.toString());
        }
    }

}