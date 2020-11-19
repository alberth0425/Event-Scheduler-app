package src.controllers;

import src.entities.*;
import src.use_cases.AuthService;
import src.use_cases.EventService;
import src.use_cases.MessageService;
import src.use_cases.RoomService;

import java.util.Scanner;

public class OrganizerController extends UserController {

    Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        OrganizerController oc = new OrganizerController();
        oc.run();
        }


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

            String content = scanner.nextLine();
            try {
                int choice = Integer.parseInt(content);

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
                        System.out.println("Unknown action. Please enter digit between 1 and 8.");
                        break;
                }

                save();

                if (exit) {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Unknown action. Please enter digit between 1 and 8. ");
                run();
            }

        }
    }
    /**
     * The createSpeaker method implements an application that
     * let Organizers create Speaker account.
     *
     */
    private void createSpeaker() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Please enter your username: ");
        //Print "Please enter your username: ".
        String username = scan.nextLine();
        //Let Organizer enter the username for the speaker.

        System.out.println("Please enter your password: ");
        //Print "Please enter your password: ".
        String password = scan.nextLine();
        //Let Organizer enter the password for the speaker.

        System.out.println("Please enter your first name: ");
        //Print "Please enter your first name: ".
        String firstName = scan.nextLine();
        //Let Organizer enter the first name for the speaker.

        System.out.println("PLease enter your last name: ");
        //Print "PLease enter your last name: ".
        String lastName = scan.nextLine();
        //Let Organizer enter the lase name for the speaker.

        try {
            AuthService.shared.createUser(username, password, firstName,
                    lastName, AuthService.UserType.SPEAKER);
            //Call createUser method in AuthService to create a speaker account.
            System.out.println("Speaker created successfully.");
            //If it works, print "Speaker created successfully.".
        } catch (AuthService.UserDoesNotExistException e) {
            System.out.println("User with username " + username + " does not exist. " + "Speaker does not create " +
                    "successfully.");
            //If catches UserDoesNotExistException, it will show that the creation is failed and tell the reason.
        } catch (AuthService.InvalidFieldException e) {
            System.out.println("Invalid " + e.getField() + " entered. Speaker does not create successfully");
            //If catches InvalidFieldException, it will show that the creation is failed and tell the reason.
        } catch (AuthService.UsernameAlreadyTakenException e) {
            System.out.println("Username " + username + "already taken.");
            //If catches UsernameAlreadyTakenException, it will show that the creation is failed and tell the reason.
        } catch (Exception e) {
            System.out.println("Unknown exception: " + e.toString() + ". Speaker does not create " +
                    "successfully.");
            //If catches Other Exception, it will show that the creation is failed and tell the reason.
        }

    }
    /**
     * The createEvent method implements an application that
     * let Organizers create Event.
     *
     */
    private void createEvent() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter the event title: ");
        //Print "Please enter the event title: ".
        String title = scanner.nextLine();
        //Let Organizer enter the title of the event.

        System.out.println("Please enter the starting time: ");
        //Print "Please enter the starting time: ".
        String startingTime = scanner.nextLine();
        //Let Organizer enter the starting time of the event.

        System.out.println("Please enter the speaker username: ");
        //Print "Please enter the speaker username: ".
        String speaker = scanner.nextLine();
        //Let Organizer enter the speaker name of the event.

        System.out.println("Please enter the room number: ");
        //Print "Please enter the room number: ".
        String roomNumber = scanner.nextLine();
        //Let Organizer enter the room number of the event.

        try {
            int st = Integer.parseInt(startingTime);
            //Convert string starting time into integer.
            int rm = Integer.parseInt(roomNumber);
            //Convert string room number into integer.
            Speaker sp = (Speaker) AuthService.shared.getUserByUsername(speaker);
            //Get the Speaker by searching the username of the Speaker.
            Room room = RoomService.shared.getRoom(rm);
            //Get the Room by searching the room number.

            try {
                EventService.shared.createEvent(title, st, sp, room);
                //Call createEvent method in EventService to create an Event.
                System.out.println("Event created successfully." );
                //If it works, print "Event created successfully.".

            } catch (EventService.EventDoesNotExistException e) {
                System.out.println("Event does not exist." + " Event does not create successfully.");
                //If catches EventDoesNotExistException, it will show that the creation is failed and tell the reason.
            } catch (EventService.SpeakerDoubleBookException e) {
                System.out.println("The speaker is not available at this time." +
                        " Event does not create successfully.");
                //If catches SpeakerDoubleBookException, it will show that the creation is failed and tell the reason.
            } catch (EventService.RoomFullException e) {
                System.out.println("The event is full." + " Event does not create successfully.");
                //If catches RoomFullException, it will show that the creation is failed and tell the reason.
            } catch (EventService.InvalidEventTimeException e) {
                System.out.println("The event starting time (" + startingTime + ") is invalid." +
                        " Event does not create successfully.");
                //If catches InvalidEventTimeException, it will show that the creation is failed and tell the reason.
            }catch(EventService.RoomDoubleBookException e){
                System.out.println("The room is not available at this time."  +
                        " Event does not create successfully.");
                //If catches RoomDoubleBookException, it will show that the creation is failed and tell the reason.
            }catch (Exception e) {
                System.out.println("Unknown Exception: " + e.toString() + ". Event does not create successfully.");
                //If catches other Exception, it will show that the creation is failed and tell the reason.
            }
        } catch (AuthService.AuthException e) {
            System.out.println("User with username " + speaker + " does not exist." +
                    " Event does not create successfully.");
            //If catches AuthException, it will show that the creation is failed and tell the reason.
        } catch (NumberFormatException e) {
            System.out.println("Starting time must be a number." + " Event does not create successfully." );
            System.out.println("Room number must be a number" + " Event does not create successfully.");
            //If starting time and room number that entered are not number, it will show
            // that the creation is failed and tell the reason.
        } catch (RoomService.RoomException e) {
            System.out.println("Room with room number " + roomNumber + " does not exist." +
                    " Event does not create successfully.");
            //If catches RoomException, it will show that the creation is failed and tell the reason.
        }
    }

    /**
     * The createRoom method implements an application that
     * let Organizers create Room.
     *
     */
    private void createRoom() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Please enter the room number: ");
        //Print "Please enter the room number: ".
        String roomNumber = scan.nextLine();
        //Let Organizer enter the room number of the room.

        System.out.println("Please enter the room capacity: ");
        //Print "Please enter the room capacity: ".
        String roomCapacity = scan.nextLine();
        //Let Organizer enter the room capacity of the room.


        try {
            int rn = Integer.parseInt(roomNumber);
            //Convert string room number into integer.
            int rc = Integer.parseInt(roomCapacity);
            //Convert string room capacity into integer.
            if (!RoomService.shared.createRoom(rn, rc)) {
                System.out.println("Room number already exist.");
                //Call createRoom method in RoomService, and print "Room number already exist." if the creation
                //is failed.
            } else {
                System.out.println("Room created successfully.");
                //If Room created successfully, print "Room created successfully.".
            }

        } catch (NumberFormatException e) {
            System.out.println("Room number must be a number." + " Room does not create successfully.");
            System.out.println("Room capacity must be a number." + " Room does not create successfully.");
            //If room capacity and room number that entered are not number, it will show
            // that the creation is failed and tell the reason.
        }

    }

    /**
     * The assignSpeakerToEvent method implements two different ways that
     * let Organizers assign Speaker to Event.
     *
     */
    private void assignSpeakerToEvent() {
        try {
            while (true) {
                System.out.println("Select an action:");
                System.out.println("1. Assign speaker to one specific event ");
                System.out.println("2. Assign speaker to multiple events in a list");
                System.out.println("3. Exit");

                String content = scanner.nextLine();
                int choice = Integer.parseInt(content);

                boolean exit = false;

                switch (choice) {
                    case 1:
                        assignSpeakerToOneEvent();
                        break;
                    case 2:
                        assignSpeakerToMultipleEvents();
                        break;
                    case 3:
                        exit = true;
                        break;

                    default:
                        System.out.println("Unknown action. Please enter digit between 1 and 3.");
                        break;
                }

                save();
                if (exit) {
                    run();
                    break;
                }

            }
        } catch (NumberFormatException e) {
            System.out.println("Unknown action. Please enter digit between 1 and 3.");
            //If the digit entered is not between 1 and 3, catches NumberFormatException and
            // print "Unknown action. Please enter digit between 1 and 3.".
            assignSpeakerToEvent();
        }

    }

    /**
     * The assignSpeakerToEvent method implements an application that
     * let Organizers assign Speaker to one Event.
     *
     */
    private void assignSpeakerToOneEvent() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter speaker username:");
        //Print "Enter speaker username:".

        String speakerUN = scanner.nextLine();
        //Let organizer enter the username of the speaker.

        try {
            Speaker speaker = (Speaker) AuthService.shared.getUserByUsername(speakerUN);
            //Get Speaker by searching the username of the Speaker.

            System.out.println("Enter event id:");
            //Print "Enter event id:".

            try {
                int eventId = Integer.parseInt(scanner.nextLine());
                //Convert string event id into integer.

                try {
                    Event event = EventService.shared.getEventById(eventId);
                    //Get Event by searing the event id of the Event.
                    EventService.shared.setEventSpeaker(speaker, event);
                    //Set certain Speaker to the certain Event.

                    System.out.println("Speaker " + speakerUN + " is assigned to event successfully.");
                    //It will show that the creation is successful.

                } catch (EventService.EventDoesNotExistException e) {
                    System.out.println("Event with ID " + eventId +  " does not exist. Speaker assigned " +
                            "unsuccessfully.");
                    //If catches EventDoesNotExistException,
                    // it will show that the assignment is failed and tell the reason.
                } catch (EventService.SpeakerDoubleBookException e) {
                    System.out.println("Speaker is not available at this event with event ID "
                            + eventId + "'s time. Speaker assigned unsuccessfully.");
                    //If catches SpeakerDoubleBookException,
                    // it will show that the assignment is failed and tell the reason.
                } catch (Exception e) {
                    System.out.println("Unknown Exception: " + e.toString());
                    //If catches an Exception,
                    // it will show that the assignment is failed and tell the reason.
                }
            } catch (NumberFormatException e) {
                System.out.println("Event ID must be a number. Speaker assigned unsuccessfully.");
                //If the Event ID entered is not a number, it catches NumberFormatException
                // and it will show that the assignment is failed.
            }
        } catch (AuthService.AuthException e) {
            System.out.println("User with username " + speakerUN + " does not exist. Speaker assigned unsuccessfully.");
            //If catches AuthException,
            // it will show that the assignment is failed and tell the reason.
        } catch (Exception e) {
            System.out.println("Unknown exception: " + e.toString());
            //If catches an Exception,
            // it will show that the assignment is failed and tell the reason.
        }
    }
    /**
     * The assignSpeakerToEvent method implements an application that
     * let Organizers assign Speaker to multiple Event.
     *
     */
    private void assignSpeakerToMultipleEvents() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter speaker username:");
        //Print "Enter speaker username:".
        String speakerUN = scanner.nextLine();
        //Let organizer enter the username of the speaker.

        try {
            Speaker speaker = (Speaker) AuthService.shared.getUserByUsername(speakerUN);
            //Get Speaker by searching the username of the Speaker.

            System.out.println("Enter a list of event id separated using comma:");
            //Print "Enter a list of event id separated using comma:".
            String eventIds = scanner.nextLine();
            //Let the organizer enter the Event IDs.
            String[] listOfEventId = eventIds.split(",");
            //Split the list of Event ID into separate Event ID.
            boolean assigned = true;
            for (String id: listOfEventId){
                try{
                    int eventId = Integer.parseInt(id);
                    //Convert string Event ID in to integer.
                    try {
                        Event event = EventService.shared.getEventById(eventId);
                        //Get Event by searching the Event ID of the Event.

                        EventService.shared.setEventSpeaker(speaker, event);
                        //Get the speaker of a certain Event.

                    } catch (EventService.EventDoesNotExistException e) {
                        System.out.println("Event with event id " + eventId + " does not exist. Speaker assigned " +
                                "unsuccessfully.");
                        //If catches EventDoesNotExistException,
                        // it will show that the assignment is failed and tell the reason.
                        assigned = false;
                    } catch (EventService.SpeakerDoubleBookException e) {
                        System.out.println("Speaker is not available at this event with event ID"
                                + eventId + "'s time. Speaker assigned unsuccessfully.");
                        //If catches SpeakerDoubleBookException,
                        // it will show that the assignment is failed and tell the reason.
                        assigned = false;
                    } catch (Exception e) {
                        System.out.println("Unknown Exception: " + e.toString());
                        //If catches an Exception,
                        // it will show that the assignment is failed and tell the reason.
                        assigned = false;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Event ID must be a number. Speaker assigned unsuccessfully.");
                    //If the Event ID entered is not a number,
                    // it will show that the assignment is failed and tell the reason.
                    assigned = false;
                }
            }
            if (assigned)
                System.out.println("Speaker " + speakerUN + " is assigned to events successfully.");
            //The assignment is successful.

        } catch (AuthService.AuthException e) {
            System.out.println("User with username " + speakerUN + " does not exist. Speaker assigned unsuccessfully.");
            //If catches AuthException,
            // it will show that the assignment is failed and tell the reason.
        } catch (Exception e) {
            System.out.println("Unknown exception: " + e.toString());
            //If catches an Exception,
            // it will show that the assignment is failed and tell the reason.
        }
    }

    /**
     * The sendMessages method implements an application that
     * let Organizers send messages.
     *
     */
    private void sendMessages() {

        System.out.println("Choose receiver type");
        System.out.println("1. Send message to all users");
        System.out.println("2. Send message to all speakers");
        System.out.println("3. Send message to all attendees");
        System.out.println("4. Send message to all attendees in a specific event");
        System.out.println("5. Send message to a specific user");
        System.out.println("6. Exit");

        try {
            while (true) {
                String content = scanner.nextLine();
                int choice = Integer.parseInt(content);

                boolean exit = false;

                switch (choice) {
                    case 1:
                        sendMessagesAllUsers();

                        sendMessages();
                    case 2:
                        sendMessagesAllSpeakers();

                        sendMessages();
                    case 3:
                        sendMessagesAllAttendees();

                        sendMessages();
                    case 4:
                        sendMessagesAllAttendeesSpecificEvent();

                        sendMessages();
                    case 5:
                        Scanner scanner = new Scanner(System.in);

                        System.out.println("Enter receiver username:");
                        //Print "Enter receiver username:".
                        String receiverUN = scanner.nextLine();
                        //Let organizer enter the receiver username.

                        try {
                            User receiver = AuthService.shared.getUserByUsername(receiverUN);
                            //Get User by searching the receiver username.

                            System.out.println("Enter message to send:");
                            //Print "Enter message to send:".
                            String messageContent = scanner.nextLine();
                            //Let organizer enter the content of the message.

                            MessageService.shared.sendMessage(messageContent, AuthService.shared.getCurrentUser(),
                                    receiver);
                            //Call sendMessage method that enables organizer send messages.

                            System.out.println("Message send successfully.");
                            //Print "Message send successfully.".

                        } catch (AuthService.UserDoesNotExistException e) {
                            System.out.println("User with username " + receiverUN + " does not exist. " +
                                    "Message does not send successfully.");
                            //If catches UserDoesNotExistException,
                            // it will show that the message is sending failed and tell the reason.
                        } catch (NullPointerException e) {
                            System.out.println("User must log in to send message. Message does not send successfully.");
                            //If catches NullPointerException,
                            // it will show that the message is sending failed and tell the reason.
                        } catch (Exception e) {
                            System.out.println("Unknown exception: " + e.toString() +
                                    " Message does not send successfully.");
                            //If catches an Exception,
                            // it will show that the message is sending failed and tell the reason.
                        }

                        sendMessages();
                    case 6:
                        exit = true;
                        break;

                    default:
                        System.out.println("Unknown action. Please enter digit between 1 and 6.");
                        //If the digit entered is not between 1 and 6,
                        //it will print "Unknown action. Please enter digit between 1 and 6.".
                        run();
                }

                save();
                if (exit) {
                    run();
                    break;
                }
            }

        } catch (NumberFormatException e) {
            System.out.println("Unknown action. Please enter digit between 1 and 6.");
            //If the digit entered is not between 1 and 6, it catches NumberFormatException,
            //it will print "Unknown action. Please enter digit between 1 and 6.".
            sendMessages();
        }
    }

    // --- Private helpers ---

    // helper for send message to all existing users
    private void sendMessagesAllUsers() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter message to send:");
        String messageContent = scanner.nextLine();

        try {
            for (User user : AuthService.shared.getAllUsers()) {
                if (user instanceof Speaker || user instanceof Attendee){
                    MessageService.shared.sendMessage(messageContent, AuthService.shared.getCurrentUser(), user);
                }
            }
            System.out.println("Message send successfully.");
        } catch (NullPointerException e) {
            System.out.println("User must log in to send message. Message does not send successfully.");
        }
    }

    // helper for send message to all existing speakers
    private void sendMessagesAllSpeakers() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter message to send:");
        String messageContent = scanner.nextLine();

        try {
            for (User user : AuthService.shared.getAllUsers()) {
                if (user instanceof Speaker)
                    MessageService.shared.sendMessage(messageContent, AuthService.shared.getCurrentUser(), user);

            }
            System.out.println("Message send successfully.");
        } catch (NullPointerException e) {
            System.out.println("User must log in to send message. Message does not send successfully.");
        }
    }

    // helper for send message to all existing attendee
    private void sendMessagesAllAttendees() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter message to send:");
        String messageContent = scanner.nextLine();

        try {
            for (User user : AuthService.shared.getAllUsers()) {
                if (user instanceof Attendee)
                    MessageService.shared.sendMessage(messageContent, AuthService.shared.getCurrentUser(), user);
            }
            System.out.println("Message send successfully.");
        } catch (NullPointerException e) {
            System.out.println("User must log in to send message. Message does not send successfully.");
        }
    }

    // helper for send message to all the attendee in a specific event
    private void sendMessagesAllAttendeesSpecificEvent() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter event id:");
        String content = scanner.nextLine();

        try {
            //check entered content is a digit
            int eventId = Integer.parseInt(content);

            //check entered event id's existence
            EventService.shared.getEventById(eventId);

            System.out.println("Enter message to send:");
            String message = scanner.nextLine();
            try {
                for (String attendeeUN : EventService.shared.getEventById(eventId).getAttendeeUNs()) {
                    MessageService.shared.sendMessage(message, AuthService.shared.getCurrentUser(),
                            AuthService.shared.getUserByUsername(attendeeUN));
                }
                System.out.println("Message send successfully.");

            } catch (Exception e) {
                System.out.println("Unknown exception: " + e.toString() + "Message does not send successfully.");
            }

        } catch (NumberFormatException e) {
            System.out.println("Invalid event id, please enter only digits. Message does not send successfully.");
        } catch (EventService.EventException e) {
            System.out.println("Event with event id " + content + " does not exist. " +
                    "Message does not send successfully.");
        } catch (NullPointerException e) {
            System.out.println("User must log in to send message. Message does not send successfully.");
        }
    }
}





