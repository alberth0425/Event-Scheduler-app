package src.controllers;

import src.entities.Attendee;
import src.entities.Event;
import src.entities.Message;
import src.entities.User;
import src.use_cases.AuthService;
import src.use_cases.EventService;
import src.use_cases.MessageService;

import java.util.List;

public class AttendeeController extends UserController {
    @Override
    void run() {
        while (true) {
            System.out.println("Select an action:");
            System.out.println("1. Browse events");
            System.out.println("2. Sign up for an event");
            System.out.println("3. Cancel event");
            System.out.println("4. Send messages");
            System.out.println("5. View messages");
            System.out.println("6. Exit");

            int choice = scanner.nextInt();

            boolean exit = false;

            switch (choice) {
                case 1:
                    browseEvents();
                    break;
                case 2:
                    signUpEvent();
                    break;
                case 3:
                    cancelEvent();
                    break;
                case 4:
                    sendMessages();
                    break;
                case 5:
                    viewMessages();
                    break;
                case 6:
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

    void signUpEvent() {
        Attendee attendee = (Attendee) AuthService.shared.getCurrentUser();

        System.out.println("Enter event ID: ");

        try {
            int eventId = Integer.parseInt(scanner.nextLine());

            try {
                Event event = EventService.shared.getEventById(eventId);

                EventService.shared.addEventAttendee(attendee, event);

            } catch (EventService.EventDoesNotExistException e) {
                System.out.println("Event does not exist.");
            } catch (EventService.RoomFullException e) {
                System.out.println("The event is full.");
            } catch (Exception e) {
                System.out.println("Unknown Exception: " + e.toString());
            }

        } catch (NumberFormatException e) {
            System.out.println("Event ID must be a number.");
        }
    }

    private void cancelEvent() {
        // TODO
    }

    private void sendMessages() {
        System.out.println("Enter receiver username:");
        String receiverUN = scanner.nextLine();

        try {
            User receiver = AuthService.shared.getUserByUsername(receiverUN);

            System.out.println("Enter message to send:");
            String content = scanner.nextLine();

            // TODO: this is not done. need to check for whether can message this user

            MessageService.shared.sendMessage(content, AuthService.shared.getCurrentUser(), receiver);

        } catch (AuthService.UserDoesNotExistException e) {
            System.out.println("User with username " + receiverUN + " does not exist.");
        } catch (Exception e) {
            System.out.println("Unknown exception: " + e.toString());
        }
    }
}
