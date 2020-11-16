package src.controllers;

import src.entities.Event;
import src.entities.User;
import src.use_cases.MessageService;
import src.use_cases.EventService;
import src.use_cases.AuthService;
import src.use_cases.RoomService;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class SpeakerController extends UserController {
    Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {
        SpeakerController sc = new SpeakerController();
        sc.run();
    }

    void run() {
        while (true) {
            try {
                System.out.println("Select an action:");
                System.out.println("1. browse my events");
                System.out.println("2. View messages");
                System.out.println("3. Send a message");
                System.out.println("4. exit");

                String action = scan.nextLine();
                int num = Integer.parseInt(action);

                boolean exit = false;

                switch (num) {
                    case 1:
                        browseMyEvents();
                        break;
                    case 2:
                        viewMessages();
                        break;
                    case 3:
                        sendMessages();
                        break;
                    case 4:
                        exit = true;
                        break;
                    default:
                        System.out.println("Unknown action. Try again");
                        break;
                }
                save();

                if (exit)
                    break;
            } catch (NumberFormatException e){
                System.out.println("Wrong user input, please enter a digit from 1 - 4");
            }
        }
    }

    void sendMessages() {
        Scanner scan = new Scanner(System.in);

        while (true) {
            try {
                System.out.println("Please choose a receiver type");
                System.out.println("1. Send message to all attendees in all of your events");
                System.out.println("2. Send message to all attendees in one of your event");
                System.out.println("3. Send message to a specific user");
                System.out.println("4. Exit");

                String input = scan.nextLine();
                int num = Integer.parseInt(input);
                boolean exit = false;

                switch (num) {
                    case 1:
                        sendToAllAttendeesAllEvents();
                        break;
                    case 2:
                        sendToAllAttendeesOneEvent();
                        break;
                    case 3:
                        sendToOne();
                        break;
                    case 4:
                        exit = true;
                        break;
                    default:
                        System.out.println("Unknown action, choose again");
                        break;
                }
                if (exit)
                    break;
            } catch (NumberFormatException e){
                System.out.println("Wrong user input, please enter a digit from 1 - 4");
            }
        }
    }

    /**
     * See the list of events that this speaker holds a talk at
     */
    void browseMyEvents() {
        //get the list of events for this speaker
        List<Event> listOfMyEvents =
                EventService.shared.getEventsBySpeaker(AuthService.shared.getCurrentUser().getUsername());
        StringBuilder sb = new StringBuilder();
        //traverse through the list of my events
        for (Event event : listOfMyEvents) {
            try {
                String eStr = "Event ID: " + event.getId() + ", Title: " + event.getTitle() +
                        ", Speaker: " + AuthService.shared.getUserByUsername(event.getSpeakerUsername()).getFullname() +
                        ", Remaining Seats: " + EventService.shared.getEventAvailability(event) + "\n";
                sb.append(eStr);
            } catch (AuthService.AuthException e) {
                System.out.println("Speaker of event <" + event.getTitle() +
                        "> with username: <" + event.getSpeakerUsername() + "> does not exist.");
            } catch (RoomService.RoomException e) {
                System.out.println("Room with room number " + event.getRoomNumber() + " does not exist.");
            } catch (Exception e) {
                System.out.println("Unknown exception: " + e.toString());
            }
        }
        System.out.println(sb.toString().trim());
    }


    // -- private helpers --
    private void sendToAllAttendeesAllEvents() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Please enter your message: ");
        String input = scan.nextLine();

        try {
            for (Event event :
                    EventService.shared.getEventsBySpeaker(AuthService.shared.getCurrentUser().getUsername())) {
                for (String attendeeUN : event.getAttendeeUNs()) {
                    User attendee = AuthService.shared.getUserByUsername(attendeeUN);
                    MessageService.shared.sendMessage(input, AuthService.shared.getCurrentUser(), attendee);
                }
            }
            System.out.println("Message sent successfully.");
        } catch(NullPointerException e) {
            System.out.println("User must log in to send message. Message does not send successfully.");
        } catch (Exception e) {
            System.out.println("Unknown exception: " + e.toString() + "Message does not send successfully.");
        }
    }

    private void sendToAllAttendeesOneEvent() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Please enter the event id: ");
        String content = scan.nextLine();

        try {
            //check if content is a digit
            int eventId = Integer.parseInt(content);

            //check entered event id's existence
            EventService.shared.getEventById(eventId);

            //check if the event's speaker is this speaker
            Event event = EventService.shared.getEventById(eventId);
            if (EventService.shared.getEventsBySpeaker(AuthService.shared.getCurrentUser().getUsername()).contains(
                    event)) {
                System.out.println("Please enter the message: ");
                String message = scan.nextLine();
                for (String userName : event.getAttendeeUNs()) {
                    MessageService.shared.sendMessage(message, AuthService.shared.getCurrentUser(),
                            AuthService.shared.getUserByUsername(userName));
                }
                System.out.println("Message sent successfully.");
            }
            else{
                System.out.println("Event id: " + content + " is not your event, Unsuccessful message sending.");
            }

        } catch (EventService.EventDoesNotExistException e) {
            System.out.println("Event with event id: " + content + " does not exist. ");
        } catch (NumberFormatException e) {
            System.out.println("Incorrect Event Id, please enter digits ONLY! Message does not send successfully.");
        } catch (NullPointerException e){
            System.out.println("User must log in to send message. Message does not send successfully.");
        } catch (Exception e) {
            System.out.println("Unknown exception: " + e.toString() + "Message does not send successfully.");
        }
    }

    private void sendToOne(){
        Scanner scan = new Scanner(System.in);
        System.out.println("Please enter the receiver User Name");
        String receiverUN = scan.nextLine();

        try{
            User receiver = AuthService.shared.getUserByUsername(receiverUN);

            System.out.println("Enter message to send:");
            String messageContent = scan.nextLine();

            MessageService.shared.sendMessage(messageContent, AuthService.shared.getCurrentUser(), receiver);

            System.out.println("Message sent successfully.");
        } catch (AuthService.UserDoesNotExistException e) {
            System.out.println("User with username " + receiverUN + " does not exist.");
        } catch(NullPointerException e){
            System.out.println("User must log in to send message. Message does not send successfully.");
        } catch (Exception e) {
            System.out.println("Unknown exception: " + e.toString() + "Message does not send successfully.");
        }
    }
}
