package controllers;

import entities.*;
import use_cases.AuthService;
import use_cases.EventService;
import use_cases.MessageService;
import use_cases.RoomService;

import java.util.ArrayList;
import java.util.List;
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
                break;
            }

        }
    }

    /**
     * The createSpeaker method implements an application that
     * let Organizers create Speaker account.
     */
    private void createSpeaker() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Please enter your username: ");
        String username = scan.nextLine();

        System.out.println("Please enter your password: ");
        String password = scan.nextLine();

        System.out.println("Please enter your first name: ");
        String firstName = scan.nextLine();

        System.out.println("PLease enter your last name: ");
        String lastName = scan.nextLine();

        try {
            //Call createUser method in AuthService to create a speaker account.
            AuthService.shared.createUser(username, password, firstName,
                    lastName, AuthService.UserType.SPEAKER);
            System.out.println("Speaker created successfully.");

        } catch (AuthService.UserDoesNotExistException e) {
            System.out.println("User with username " + username + " does not exist. " + "Speaker does not create " +
                    "successfully.");
        } catch (AuthService.InvalidFieldException e) {
            System.out.println("Invalid " + e.getField() + " entered. Speaker does not create successfully");
        } catch (AuthService.UsernameAlreadyTakenException e) {
            System.out.println("Username " + username + "already taken.");
        } catch (Exception e) {
            System.out.println("Unknown exception: " + e.toString() + ". Speaker does not create " +
                    "successfully.");
        }

    }

    private void createEvent() {
        while (true) {
            System.out.println("Select the type of event that you want to create");
            System.out.println("1. Talk");
            System.out.println("2. Party");
            System.out.println("3. Panel Discussion");
            System.out.println("4. exit");

            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();
            try {
                int num = Integer.parseInt(input);
                boolean exit = false;

                switch (num) {
                    case 1:
                        createTalk();
                        break;
                    case 2:
                        createParty();
                        break;
                    case 3:
                        createPD();
                        break;
                    case 4:
                        exit = true;
                        break;
                    default:
                        System.out.println("Unknown action, please enter a digit between 1-3");
                }
                save();
                if (exit) {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Unknown action, please enter a digit between 1-3");
            }
        }
    }

    /**
     * The createEvent method implements an application that
     * let Organizers create Event.
     */
    private void createTalk() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter the event title: ");
        String title = scanner.nextLine();

        System.out.println("Please enter the starting time: ");
        String startingTime = scanner.nextLine();

        System.out.println("Please enter the speaker username: ");
        String speaker = scanner.nextLine();

        System.out.println("Please enter the room number: ");
        String roomNumber = scanner.nextLine();

        System.out.println("Please enter the duration: ");
        String duration = scanner.nextLine();
        try {
            int st = Integer.parseInt(startingTime);
            int rm = Integer.parseInt(roomNumber);
            int d = Integer.parseInt(duration);

            //Get the Speaker by searching the username of the Speaker.
            Speaker sp = (Speaker) AuthService.shared.getUserByUsername(speaker);

            //Get the Room by searching the room number.
            Room room = RoomService.shared.getRoom(rm);

            try {
                //Call createEvent method in EventService to create an Event.
                EventService.shared.createTalk(title, st, sp, room, d);
                System.out.println("Event created successfully.");


            } catch (EventService.EventDoesNotExistException e) {
                System.out.println("Event does not exist." + " Event does not create successfully.");
            } catch (EventService.SpeakerDoubleBookException e) {
                System.out.println("The speaker is not available at this time." +
                        " Event does not create successfully.");
            } catch (EventService.RoomFullException e) {
                System.out.println("The event is full." + " Event does not create successfully.");
            } catch (EventService.InvalidEventTimeException e) {
                System.out.println("The event starting time (" + startingTime + ") is invalid." +
                        " Event does not create successfully.");
            } catch (EventService.InvalidEndTimeException e) {
                System.out.println("Event must end before 12 midnight");
            } catch (EventService.RoomDoubleBookException e) {
                System.out.println("The room is not available at this time." +
                        " Event does not create successfully.");
            } catch (Exception e) {
                System.out.println("Unknown Exception: " + e.toString() + ". Event does not create successfully.");
            }
        } catch (AuthService.AuthException e) {
            System.out.println("User with username " + speaker + " does not exist." +
                    " Event does not create successfully.");
        } catch (NumberFormatException e) {
            System.out.println("Starting time, room number and duration must all be numbers." +
                    " Event does not create successfully.");
        } catch (RoomService.RoomException e) {
            System.out.println("Room with room number " + roomNumber + " does not exist." +
                    " Event does not create successfully.");
        }
    }

    /**
     * Controller method for creating a party event
     */
    private void createParty() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter the event title: ");
        String title = scanner.nextLine();

        System.out.println("Please enter the starting time: ");
        String startingTime = scanner.nextLine();

        System.out.println("Please enter the room number: ");
        String roomNumber = scanner.nextLine();

        System.out.println("Please enter the duration: ");
        String duration = scanner.nextLine();

        try {
            int st = Integer.parseInt(startingTime);
            int rm = Integer.parseInt(roomNumber);
            int d = Integer.parseInt(duration);

            //Get the Room by searching the room number.
            Room room = RoomService.shared.getRoom(rm);

            try {
                //Call createEvent method in EventService to create an Event.
                EventService.shared.createParty(title, st, room, d);
                System.out.println("Event created successfully.");


            } catch (EventService.EventDoesNotExistException e) {
                System.out.println("Event does not exist." + " Event does not create successfully.");
            } catch (EventService.RoomFullException e) {
                System.out.println("The event is full." + " Event does not create successfully.");
            } catch (EventService.InvalidEventTimeException e) {
                System.out.println("The event starting time (" + startingTime + ") is invalid." +
                        " Event does not create successfully.");
            } catch (EventService.InvalidEndTimeException e) {
                System.out.println("Event must end before 12 midnight");
            } catch (EventService.RoomDoubleBookException e) {
                System.out.println("The room is not available at this time." +
                        " Event does not create successfully.");
            } catch (Exception e) {
                System.out.println("Unknown Exception: " + e.toString() + ". Event does not create successfully.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Starting time must be a number." + " Event does not create successfully.");
            System.out.println("Room number must be a number" + " Event does not create successfully.");
        } catch (RoomService.RoomException e) {
            System.out.println("Room with room number " + roomNumber + " does not exist." +
                    " Event does not create successfully.");
        }
    }

    private void createPD() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter the event title: ");
        String title = scanner.nextLine();

        System.out.println("Please enter the starting time: ");
        String startingTime = scanner.nextLine();

        List<String> speakerUNs = new ArrayList<>();
        while (true) {
            System.out.println("Please enter the list of speaker usernames one by one:");
            System.out.println("enter '1' to stop");
            String username = scanner.nextLine();
            if (username.equals("1")) {
                break;
            }
            try {
                //check if user with username exists
                AuthService.shared.getUserByUsername(username);
                speakerUNs.add(username);
            } catch (AuthService.AuthException e) {
                System.out.println("user with username '" + username + "' is not found");
            }
        }
        //check that there is at least a valid speaker in this panel discussion
        try {
            if (speakerUNs.size() < 1) {
                throw new EventService.NotEnoughSpeakersException();
            }

            System.out.println("Please enter the room number: ");
            String roomNumber = scanner.nextLine();

            System.out.println("Please enter the duration ");
            String duration = scanner.nextLine();

            try {
                int st = Integer.parseInt(startingTime);
                int rm = Integer.parseInt(roomNumber);
                int d = Integer.parseInt(duration);

                //Get the list of speakers by searching the list of usernames of the Speakers.
                List<Speaker> speakers = AuthService.shared.getListOfSpeakersByUNs(speakerUNs);

                //Get the Room by searching the room number.
                Room room = RoomService.shared.getRoom(rm);

                try {
                    //Call createPD method in EventService to create an Event.
                    EventService.shared.createPD(title, st, speakers, room, d);
                    System.out.println("Event created successfully.");

                } catch (EventService.EventDoesNotExistException e) {
                    System.out.println("Event does not exist." + " Event does not create successfully.");
                } catch (EventService.SpeakerDoubleBookException e) {
                    System.out.println("The speaker is not available at this time." +
                            " Event does not create successfully.");
                } catch (EventService.RoomFullException e) {
                    System.out.println("The event is full." + " Event does not create successfully.");
                } catch (EventService.InvalidEventTimeException e) {
                    System.out.println("The event starting time (" + startingTime + ") is invalid." +
                            " Event does not create successfully.");
                } catch (EventService.RoomDoubleBookException e) {
                    System.out.println("The room is not available at this time." +
                            " Event does not create successfully.");
                } catch (EventService.InvalidEndTimeException e) {
                    System.out.println("Event must end before 12 midnight");
                } catch (Exception e) {
                    System.out.println("Unknown Exception: " + e.toString() + ". Event does not create successfully.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Starting time must be a number." + " Event does not create successfully.");
                System.out.println("Room number must be a number" + " Event does not create successfully.");
            } catch (RoomService.RoomException e) {
                System.out.println("Room with room number " + roomNumber + " does not exist." +
                        " Event does not create successfully.");
            } catch (AuthService.AuthException e) {
                System.out.println("User with username is not found");
            }
        } catch (EventService.NotEnoughSpeakersException e) {
            System.out.println("Number of speakers must be >= 1 in a Panel Discussion");
            System.out.println("Event creation was unsuccessful");
        }
    }

    /**
     * The createRoom method implements an application that
     * let Organizers create Room.
     */
    private void createRoom() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Please enter the room number: ");
        String roomNumber = scan.nextLine();

        System.out.println("Please enter the room capacity: ");
        String roomCapacity = scan.nextLine();


        try {
            int rn = Integer.parseInt(roomNumber);
            int rc = Integer.parseInt(roomCapacity);

            //Check room's existence
            if (!RoomService.shared.createRoom(rn, rc)) {
                System.out.println("Room number already exist.");
            } else {
                System.out.println("Room created successfully.");
            }

        } catch (NumberFormatException e) {
            System.out.println("Room number must be a number." + " Room does not create successfully.");
            System.out.println("Room capacity must be a number." + " Room does not create successfully.");
        }

    }

    /**
     * The assignSpeakerToEvent method implements two different ways that
     * let Organizers assign Speaker to Event.
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
                    break;
                }

            }
        } catch (NumberFormatException e) {
            System.out.println("Unknown action. Please enter digit between 1 and 3.");
            assignSpeakerToEvent();
        }

    }

    /**
     * The assignSpeakerToEvent method implements an application that
     * let Organizers assign Speaker to one Event.
     */
    private void assignSpeakerToOneEvent() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter speaker username:");

        String speakerUN = scanner.nextLine();

        try {
            //Get Speaker by searching the username of the Speaker.
            Speaker speaker = (Speaker) AuthService.shared.getUserByUsername(speakerUN);

            System.out.println("Enter event id:");

            try {
                int eventId = Integer.parseInt(scanner.nextLine());

                try {
                    //Get Event by searching the event id of the Event.
                    Event event = EventService.shared.getEventById(eventId);

                    //Set speaker to talk
                    if (event instanceof Talk) {
                        EventService.shared.setTalkSpeaker(speaker, event);
                        System.out.println("Speaker " + speakerUN + " is assigned to event successfully.");
                    }
                    //add a speaker to a panel discussion
                    else if (event instanceof PanelDiscussion) {
                        EventService.shared.addSpeakerToPD(speaker, event);
                    } else {
                        throw new EventService.CannotAddSpeakerToPartyException();
                    }
                } catch (EventService.EventDoesNotExistException e) {
                    System.out.println("Event with ID " + eventId + " does not exist. Speaker assigned " +
                            "unsuccessfully.");
                } catch (EventService.SpeakerDoubleBookException e) {
                    System.out.println("Speaker is not available at this event with event ID "
                            + eventId + "'s time. Speaker assigned unsuccessfully.");
                } catch (EventService.CannotAddSpeakerToPartyException e) {
                    System.out.println("Speaker cannot be added to a party");
                } catch (Exception e) {
                    System.out.println("Unknown Exception: " + e.toString());
                }
            } catch (NumberFormatException e) {
                System.out.println("Event ID must be a number. Speaker assigned unsuccessfully.");
            }
        } catch (AuthService.AuthException e) {
            System.out.println("User with username " + speakerUN +
                    " does not exist. Speaker assigned unsuccessfully.");
        } catch (Exception e) {
            System.out.println("Unknown exception: " + e.toString());
        }
    }

    /**
     * The assignSpeakerToEvent method implements an application that
     * let Organizers assign Speaker to multiple Event.
     */
    private void assignSpeakerToMultipleEvents() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter speaker username:");
        String speakerUN = scanner.nextLine();

        try {

            //Get Speaker by searching the username of the Speaker.
            Speaker speaker = (Speaker) AuthService.shared.getUserByUsername(speakerUN);

            System.out.println("Enter a list of event id separated using comma:");
            String eventIds = scanner.nextLine();

            //Split the list of Event ID into separate Event ID.
            String[] listOfEventId = eventIds.split(",");

            boolean assigned = true;
            for (String id : listOfEventId) {
                try {
                    int eventId = Integer.parseInt(id);
                    try {
                        Event e = EventService.shared.getEventById(eventId);
                        if (e instanceof Talk) {
                            //Get Event by searching the Event ID of the Event.
                            Event event = EventService.shared.getEventById(eventId);

                            //set the speaker to talk
                            EventService.shared.setTalkSpeaker(speaker, event);
                        } else if (e instanceof PanelDiscussion) {
                            //Get Event by searching the Event ID of the Event.
                            Event event = EventService.shared.getEventById(eventId);

                            //assign the speaker to the panel discussion
                            EventService.shared.addSpeakerToPD(speaker, event);
                        } else {
                            assigned = false;
                            System.out.println("Cannot assign speakers to a party");
                        }
                    } catch (EventService.EventDoesNotExistException e) {
                        System.out.println("Event with event id " + eventId + " does not exist. Speaker assigned " +
                                "unsuccessfully.");
                        assigned = false;
                    } catch (EventService.SpeakerDoubleBookException e) {
                        System.out.println("Speaker is not available at this event with event ID"
                                + eventId + "'s time. Speaker assigned unsuccessfully.");
                        assigned = false;
                    } catch (Exception e) {
                        System.out.println("Unknown Exception: " + e.toString());
                        assigned = false;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Event ID must be a number. Speaker assigned unsuccessfully.");
                    assigned = false;
                }
            }
            if (assigned)
                System.out.println("Speaker " + speakerUN + " is assigned to events successfully.");

        } catch (AuthService.AuthException e) {
            System.out.println("User with username " + speakerUN +
                    " does not exist. Speaker assigned unsuccessfully.");
        } catch (Exception e) {
            System.out.println("Unknown exception: " + e.toString());
        }

    }

    /**
     * The sendMessages method implements an application that
     * let Organizers send messages.
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
                        String receiverUN = scanner.nextLine();

                        try {

                            //Get User by searching the receiver username.
                            User receiver = AuthService.shared.getUserByUsername(receiverUN);

                            System.out.println("Enter message to send:");
                            String messageContent = scanner.nextLine();

                            //Call sendMessage method that enables organizer send messages.
                            MessageService.shared.sendMessage(messageContent, AuthService.shared.getCurrentUser(),
                                    receiver);

                            System.out.println("Message sent successfully.");

                        } catch (AuthService.UserDoesNotExistException e) {
                            System.out.println("User with username " + receiverUN + " does not exist. " +
                                    "Message does not send successfully.");
                        } catch (NullPointerException e) {
                            System.out.println("User must log in to send message." +
                                    " Message does not send successfully.");
                        } catch (Exception e) {
                            System.out.println("Unknown exception: " + e.toString() +
                                    " Message does not send successfully.");
                        }

                        sendMessages();
                    case 6:
                        exit = true;
                        break;

                    default:
                        System.out.println("Unknown action. Please enter digit between 1 and 6.");
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
                if (user instanceof Speaker || user instanceof Attendee) {
                    MessageService.shared.sendMessage(messageContent, AuthService.shared.getCurrentUser(), user);
                }
            }
            System.out.println("Message sent successfully.");
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
            System.out.println("Message sent successfully.");
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
            System.out.println("Message sent successfully.");
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
                System.out.println("Message sent successfully.");

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





