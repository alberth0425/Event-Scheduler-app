package controllers;

import entities.Event;
import entities.Message;
import entities.Speaker;
import use_cases.*;

import java.util.List;

abstract public class UserController extends BaseController {
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
                        RateService.shared.getAverageRate(speaker.getUsername()) + "\n";
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
