package src.controllers;

import src.entities.Event;
import src.entities.Speaker;
import src.entities.User;
import src.use_cases.AuthService;
import src.use_cases.EventService;
import src.use_cases.RoomService;

import java.util.ArrayList;

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

    // Two different ways to assign speaker to event.
    void assignSpeakerToEvent() {
        while (true) {
            System.out.println("Select an action:");
            System.out.println("1. Assign speaker to one specific event ");
            System.out.println("2. Assign speaker to multiple events in a list");
            System.out.println("3. Exit");

            int choice = scanner.nextInt();

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
                    System.out.println("Unknown action.");
                    break;
            }

            save();

            if (exit) {
                run();
                break;
            }

        }
    }

    void assignSpeakerToOneEvent() {
        System.out.println("Enter speaker username:");

        String speakerUN = scanner.nextLine();

        try {
            Speaker speaker = (Speaker) AuthService.shared.getUserByUsername(speakerUN);

            System.out.println("Enter event id:");

            try {
                int eventId = Integer.parseInt(scanner.nextLine());

                try {
                    Event event = EventService.shared.getEventById(eventId);

                    EventService.shared.setEventSpeaker(speaker, event);

                } catch (EventService.EventDoesNotExistException e) {
                    System.out.println("Event does not exist.");
                } catch (EventService.SpeakerDoubleBookException e) {
                    System.out.println("Speaker not available at this event's time");
                } catch (Exception e) {
                    System.out.println("Unknown Exception: " + e.toString());
                }
            } catch (NumberFormatException e) {
                System.out.println("Event ID must be a number.");
            }
        } catch (AuthService.UserDoesNotExistException e) {
            System.out.println("User with username " + speakerUN + " does not exist.");
        } catch (Exception e) {
            System.out.println("Unknown exception: " + e.toString());
        }
    }

    void assignSpeakerToMultipleEvents() {
        System.out.println("Enter speaker username:");

        String speakerUN = scanner.nextLine();

        try {
            Speaker speaker = (Speaker) AuthService.shared.getUserByUsername(speakerUN);

            System.out.println("Enter a list of event id separated using comma:");
            String eventIds = scanner.nextLine();
            String[] listOfEventId = eventIds.split(",");
            for (String id: listOfEventId){
                try{
                    int eventId = Integer.parseInt(id);
                    try {
                        Event event = EventService.shared.getEventById(eventId);

                        EventService.shared.setEventSpeaker(speaker, event);

                    } catch (EventService.EventDoesNotExistException e) {
                        System.out.println("Event does not exist.");
                    } catch (EventService.SpeakerDoubleBookException e) {
                        System.out.println("Speaker not available at this event's time");
                    } catch (Exception e) {
                        System.out.println("Unknown Exception: " + e.toString());
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Event ID must be a number.");
                }
            }

        } catch (AuthService.UserDoesNotExistException e) {
            System.out.println("User with username " + speakerUN + " does not exist.");
        } catch (Exception e) {
            System.out.println("Unknown exception: " + e.toString());
        }
    }
}