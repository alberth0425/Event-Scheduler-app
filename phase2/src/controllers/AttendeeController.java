package controllers;

import entities.*;
import use_cases.AuthService;
import use_cases.EventService;
import use_cases.MessageService;
import use_cases.RoomService;

import java.util.List;
import java.util.Scanner;

public class AttendeeController extends UserController {

    @Override
    void run() {
        while (true) {
            System.out.println("Select an action:");
            System.out.println("1. Browse events");
            System.out.println("2. Browse events you signed up");
            System.out.println("3. Sign up for an event");
            System.out.println("4. Cancel event");
            System.out.println("5. Send messages");
            System.out.println("6. View messages");
            System.out.println("7. Exit");

            Scanner input = new Scanner(System.in);
            String content = input.nextLine();

            try {
                int choice = Integer.parseInt(content);

                boolean exit = false;

                switch (choice) {
                    case 1:
                        browseEvents();
                        break;
                    case 2:
                        browseEventsSignedUp();
                        break;
                    case 3:
                        signUpEvent();
                        break;
                    case 4:
                        cancelEvent();
                        break;
                    case 5:
                        sendMessages();
                        break;
                    case 6:
                        viewMessages();
                        break;
                    case 7:
                        exit = true;
                        break;
                    default:
                        System.out.println("Unknown action. Please enter digit between 1 and 7.");
                        break;
                }

                save();

                if (exit) {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Unknown action. Please enter digit between 1 and 7.");
                break;
            }
            }

    }

    private void browseEventsSignedUp() {
        String username = AuthService.shared.getCurrentUser().getUsername();
        List<Event> events = EventService.shared.getEventsWithAttendee(username);

        System.out.println("The events you signed up for:");
        for (Event event : events) {
            try {
                String eventStr = "ID: " + event.getId() + ", Title: " + event.getTitle() +
                        ", Speaker: " + AuthService.shared.getUserByUsername(event.getSpeakerUsername()).getFullname() +
                        ", Remaining Seats: " + EventService.shared.getEventAvailability(event);
                System.out.println(eventStr);
            } catch (Exception e) {
                System.out.println("Unknown error: " + e.getMessage());
            }
        }
    }

    void signUpEvent() {
        Scanner scanner = new Scanner(System.in);
        Attendee attendee = (Attendee) AuthService.shared.getCurrentUser();

        System.out.println("Enter event ID: ");

        try {
            int eventId = Integer.parseInt(scanner.nextLine());
            Event event = EventService.shared.getEventById(eventId);
            EventService.shared.addEventAttendee(attendee, event);

        } catch (NumberFormatException e) {
            System.out.println("Event ID must be a number.");
        } catch (EventService.EventDoesNotExistException e) {
            System.out.println("Event does not exist.");
        } catch (EventService.RoomFullException e) {
            System.out.println("The event is full.");
        } catch (EventService.AttendeeScheduleConflictException e) {
            System.out.println("Event time conflicted.");
        } catch (EventService.EventException | RoomService.RoomException e) {
            System.out.println("Unknown Exception: " + e.toString());
        }
    }

    private void cancelEvent() {
        Scanner scanner = new Scanner(System.in);
        String username = AuthService.shared.getCurrentUser().getUsername();
        List<Event> events = EventService.shared.getEventsWithAttendee(username);

        // User has not not signed up for any events
        if (events.size() == 0) {
            System.out.println("You have not signed up for any events yet.");
            return;
        }

        browseEventsSignedUp();

        System.out.println("Enter the ID of the event to cancel:");

        try {
            int eventId = Integer.parseInt(scanner.nextLine());
            Event event = EventService.shared.getEventById(eventId);
            EventService.shared.removeEventAttendee((Attendee) AuthService.shared.getCurrentUser(), event);

        } catch (EventService.EventDoesNotExistException e) {
            System.out.println("The event with given ID does not exist.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid event ID: must be an integer.");
        } catch (Exception e) {
            System.out.println("Unknown error: " + e.getMessage());
        }
    }

    private void sendMessages() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter receiver username:");
        String receiverUN = scanner.nextLine();

        try {
            User receiver = AuthService.shared.getUserByUsername(receiverUN);

            System.out.println("Enter message to send:");
            String content = scanner.nextLine();

            // TODO: this is not done. need to check for whether can message this user

            MessageService.shared.sendMessage(content, AuthService.shared.getCurrentUser(), receiver);
            System.out.println("Message sent successfully.");

        } catch (AuthService.UserDoesNotExistException e) {
            System.out.println("User with username " + receiverUN + " does not exist. " +
                    "Message does not send successfully.");
        } catch (Exception e) {
            System.out.println("Unknown exception: " + e.toString() +
                    " Message does not send successfully.");
        }
    }
}

    private void setroomcapacityforcurrentEvent(){
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the eventid of the event:");

        String content = scanner.nextLine();
        System.out.println("Please enter the capacity of the event");
        String input = scanner.nextLine();
        try{

            //enter the eventid of the event
            int eventId = Integer.parseInt(content);
            int capacity = Integer.parseInt(input)
            EventService.shared.getCapacity(eventId);


            String message = scanner.nextLine();


        } catch (IllegalArgumentException capacity) {

            System.out.println("The capacity entered is out of range and should be greater than 0.");
        }

    }

}
