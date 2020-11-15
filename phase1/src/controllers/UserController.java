package src.controllers;

import src.entities.Event;
import src.entities.Message;
import src.use_cases.AuthService;
import src.use_cases.EventService;
import src.use_cases.MessageService;
import src.use_cases.RoomService;

import java.util.List;

abstract public class UserController extends BaseController {
    abstract void run();

    void browseEvents() {
        List<Event> allEvents = EventService.shared.getAllEvents();

        StringBuilder sb = new StringBuilder();
        for (Event event : allEvents) {
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
}
