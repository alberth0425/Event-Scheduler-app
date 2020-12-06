package controllers;

import entities.Event;
import entities.Message;
import entities.Speaker;
import use_cases.*;
import java.util.Scanner;

import java.util.List;

abstract public class UserController extends BaseController {
    Scanner scanner = new Scanner(System.in);

    abstract void run();

    void browseEvents() {
        List<Event> allEvents = EventService.shared.getAllEvents();

        StringBuilder sb = new StringBuilder();
        for (Event event : allEvents) {
            try {
                Speaker speaker = (Speaker) AuthService.shared.getUserByUsername(event.getSpeakerUsername());

                String eStr = "Event ID: " + event.getId() + ", Title: " + event.getTitle() +
                        ", Speaker: " + AuthService.shared.getUserByUsername(event.getSpeakerUsername()).getFullname() +
                        ", Remaining Seats: " + EventService.shared.getEventAvailability(event) + ", Speaker rate: " +
                        speaker.getAverageRate() + "\n";
                sb.append(eStr);
            } catch (AuthService.AuthException e) {
                System.out.println("Speaker of event <" + event.getTitle() +
                        "> with username: <" + event.getSpeakerUsername() + "> does not exist.");
            } catch (RoomService.RoomException e) {
                System.out.println("Room with room number " + event.getRoomNumber() + " does not exist.");
            }
        }

        System.out.println(sb.toString().trim());
    }

    void viewMessages() {
        List<Message> messages = MessageService.shared.getReceivedMessages(AuthService.shared.getCurrentUser().getUsername());

        for (Message message : messages) {
            System.out.println("From: " + message.getSenderUsername() + ", content: " + message.getText());
        }
    }
    void changeMessageStatus() {
        viewMessages();
        while (true) {
            System.out.println("Select an action:");
            System.out.println("1. mark as unread");
            System.out.println("2. delete this message");
            System.out.println("3. archive this message");
            System.out.println("4. unarchive this message");

            String content = scanner.nextLine();
            try {
                int choice = Integer.parseInt(content);

                boolean exit = false;

                switch (choice) {
                    case 1:
                        markAsUnread();
                        break;
                    case 2:
                        deleteMessage();
                        break;
                    case 3:
                        archiveMessage();
                        break;
                    case 4:
                        unArchivedMessage();
                        break;
                    case 5:
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

    private void markAsUnread(){
        System.out.println("This message is marked as unread.");
    }

    private void deleteMessage(){
        MessageService.shared.deleteMessages(AuthService.shared.getCurrentUser().getUsername());
        System.out.println("The messages are deleted.");
    }

    private void archiveMessage(){
        MessageService.shared.archiveMessage(AuthService.shared.getCurrentUser().getUsername());
        System.out.println("The messages are archived.");

    }

    private void unArchivedMessage(){
        MessageService.shared.unArchiveMessage(AuthService.shared.getCurrentUser().getUsername());
        System.out.println("The messages are not archived.");
    }

}


