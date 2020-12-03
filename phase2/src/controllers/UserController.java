package controllers;

import entities.*;
import use_cases.AuthService;
import use_cases.EventService;
import use_cases.MessageService;
import use_cases.RoomService;

import java.util.ArrayList;
import java.util.List;

abstract public class UserController extends BaseController {
    abstract void run();

    void browseEvents() {
        List<Event> allEvents = EventService.shared.getAllEvents();

        StringBuilder sb = new StringBuilder();
        for (Event event : allEvents) {
            //if the event is a talk
            if(event instanceof Talk) {
                Talk talk = (Talk) event;
                //Talk display message
                try {
                    String eStr = "Event ID: " + event.getId() + ", Title: " + event.getTitle() +
                            ", Speaker: "
                            + AuthService.shared.getUserByUsername(talk.getSpeakerUsername()).getFullname() +
                            ", From: " + talk.getStartingTime() + " - " + talk.getEndTime() +
                            ", Remaining Seats: " + EventService.shared.getEventAvailability(event) + "\n";
                    sb.append(eStr);
                } catch (AuthService.AuthException e) {
                    System.out.println("Speaker of event <" + event.getTitle() +
                            "> with username: <" + talk.getSpeakerUsername() + "> does not exist.");
                } catch (RoomService.RoomException e) {
                    System.out.println("Room with room number " + event.getRoomNumber() + " does not exist.");
                }
            }
            //if the event is a party
            else if(event instanceof Party) {
                Party party = (Party) event;
                try {
                    String eStr = "Event ID: " + event.getId() + ", Title: " + event.getTitle() +
                            ", From: " + party.getStartingTime() + " - " + party.getEndTime() +
                            ", Remaining Seats: " + EventService.shared.getEventAvailability(event) + "\n";
                    sb.append(eStr);
                } catch (RoomService.RoomException e) {
                    System.out.println("Room with room number " + event.getRoomNumber() + " does not exist.");
                }
            }
            //if the event is a panel discussion
            else{
                PanelDiscussion pd = (PanelDiscussion) event;
                List<String> res = new ArrayList<>();
                try {
                    //Get the list of speaker names
                    List<Speaker> speakers = AuthService.shared.getListOfSpeakersByUNs(pd.getSpeakerUNs());
                    for (Speaker sp: speakers){
                        res.add(sp.getFullname());
                    }
                    String eStr = "Event ID: " + event.getId() + ", Title: " + event.getTitle() +
                            ", Speakers: ["
                            + res.toString() +
                            "], From: " + pd.getStartingTime() + " - " + pd.getEndTime() +
                            ", Remaining Seats: " + EventService.shared.getEventAvailability(event) + "\n";
                    sb.append(eStr);
                } catch (AuthService.AuthException e) {
                    System.out.println("One of the speaker usernames" + res + " of event <" + event.getTitle() +
                            "> does not exist.");
                } catch (RoomService.RoomException e) {
                    System.out.println("Room with room number " + event.getRoomNumber() + " does not exist.");
                } catch (NullPointerException e){
                    System.out.println("Currently there is no events available for viewing");
                }
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
