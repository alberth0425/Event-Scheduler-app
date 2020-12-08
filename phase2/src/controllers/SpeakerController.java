package controllers;

import entities.*;
import entities.User;
import use_cases.MessageService;
import use_cases.EventService;
import use_cases.AuthService;
import use_cases.RoomService;

import java.util.ArrayList;
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
                System.out.println("1. Browse my events");
                System.out.println("2. View messages");
                System.out.println("3. Send messages");
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
                        System.out.println("Unknown action. Please enter digit between 1 and 4.");
                        break;
                }
                save();

                if (exit)
                    break;
            } catch (NumberFormatException e) {
                System.out.println("Unknown action. Please enter digit between 1 and 4.");
                break;
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
                        System.out.println("Unknown action. Please enter digit between 1 and 4.");
                        break;
                }
                if (exit)
                    break;
            } catch (NumberFormatException e) {
                System.out.println("Unknown action. Please enter digit between 1 and 4.");
                break;
            }
        }
    }

    /**
     * See the list of events that this speaker holds a talk at
     */
    void browseMyEvents() {
        try {
            //get the list of events for this speaker
            List<Event> listOfMyEvents =
                    EventService.shared.getEventsBySpeaker(AuthService.shared.getCurrentUser().getUsername());

            StringBuilder sb = new StringBuilder();
            //traverse through the list of my events
            for (Event event : listOfMyEvents) {
                //if the event is a talk
                if (event instanceof Talk) {
                    Talk talk = (Talk) event;
                    try {
                        String eStr = "Event ID: " + event.getId() + ", Title: " + event.getTitle() +
                                ", Speaker: " +
                                AuthService.shared.getUserByUsername(talk.getSpeakerUsername()).getFullname() +
                                ", Remaining Seats: " + EventService.shared.getEventAvailability(event) + "\n";
                        sb.append(eStr);
                    } catch (AuthService.AuthException e) {
                        System.out.println("Speaker of event <" + event.getTitle() +
                                "> with username: <" + talk.getSpeakerUsername() + "> does not exist.");
                    } catch (Exception e) {
                        System.out.println("Unknown exception: " + e.toString());
                    }
                }
                //if the event is a party
                else if (event instanceof Party) {
                    try {
                        String eStr = "Event ID: " + event.getId() + ", Title: " + event.getTitle() +
                                ", Remaining Seats: " + EventService.shared.getEventAvailability(event) + "\n";
                        sb.append(eStr);
                    } catch (Exception e) {
                        System.out.println("Unknown exception: " + e.toString());
                    }
                }
                //if the event is a Panel Discussion
                else {
                    PanelDiscussion pd = (PanelDiscussion) event;
                    List<String> res = new ArrayList<>();
                    try {
                        //Get the list of speaker names
                        List<Speaker> speakers = AuthService.shared.getListOfSpeakersByUNs(pd.getSpeakerUNs());
                        for (Speaker sp : speakers) {
                            res.add(sp.getFullname());
                        }
                        String eStr = "Event ID: " + event.getId() + ", Title: " + event.getTitle() +
                                ", Speakers: ["
                                + res.toString() +
                                "], Remaining Seats: " + EventService.shared.getEventAvailability(event) + "\n";
                        sb.append(eStr);
                    } catch (AuthService.AuthException e) {
                        System.out.println("One of the speaker usernames" + res + " of event <" + event.getTitle() +
                                "> does not exist.");
                    }
                }
            }
            System.out.println(sb.toString().trim());
        } catch (NullPointerException e) {
            System.out.println("There is no events available for this speaker");
        }
    }


    // -- private helpers --

    /**
     * sends a message to all the attendees in all of this speaker's events
     */
    private void sendToAllAttendeesAllEvents() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter message to send: ");
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
        } catch (NullPointerException e) {
            System.out.println("User must log in to send message. Message does not send successfully.");
        } catch (Exception e) {
            System.out.println("Unknown exception: " + e.toString() + " Message does not send successfully.");
        }
    }

    /**
     * sends a message to all the attendees in one of this speaker's events.
     */
    private void sendToAllAttendeesOneEvent() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter event id: ");
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
                System.out.println("Enter message to send: ");
                String message = scan.nextLine();
                for (String userName : event.getAttendeeUNs()) {
                    MessageService.shared.sendMessage(message, AuthService.shared.getCurrentUser(),
                            AuthService.shared.getUserByUsername(userName));
                }
                System.out.println("Message sent successfully.");
            } else {
                System.out.println("Event with event id " + content + " is not your event, " +
                        "Message does not send successfully.");
            }

        } catch (EventService.EventDoesNotExistException e) {
            System.out.println("Event with event id " + content + " does not exist. " +
                    "Message does not send successfully.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid event id, please enter only digits. Message does not send successfully.");
        } catch (NullPointerException e) {
            System.out.println("User must log in to send message. Message does not send successfully.");
        } catch (Exception e) {
            System.out.println("Unknown exception: " + e.toString() + " Message does not send successfully.");
        }
    }

    /**
     * controller for sending a message to a particular person
     */
    private void sendToOne() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter receiver username:");
        String receiverUN = scan.nextLine();

        try {
            User receiver = AuthService.shared.getUserByUsername(receiverUN);

            System.out.println("Enter message to send:");
            String messageContent = scan.nextLine();

            MessageService.shared.sendMessage(messageContent, AuthService.shared.getCurrentUser(), receiver);

            System.out.println("Message sent successfully.");
        } catch (AuthService.UserDoesNotExistException e) {
            System.out.println("User with username " + receiverUN + " does not exist. " +
                    "Message does not send successfully.");
        } catch (NullPointerException e) {
            System.out.println("User must log in to send message. Message does not send successfully.");
        } catch (Exception e) {
            System.out.println("Unknown exception: " + e.toString() + " Message does not send successfully.");
        }
    }
}
