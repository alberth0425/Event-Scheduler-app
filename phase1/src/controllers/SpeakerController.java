package src.controllers;

import src.entities.Attendee;
import src.entities.Event;
import src.entities.Speaker;
import src.entities.User;
import src.use_cases.MessageService;
import src.use_cases.EventService;
import src.use_cases.AuthService;

import java.util.ArrayList;
import java.util.List;

public class SpeakerController extends UserController {

    private User user;

    void run() {

    }
    public SpeakerController(Speaker user){
        this.user = user;
    }

    /**
     * See the list of events that this speaker holds a talk at
     */
    void browseMyEvents(){
        List<Event> listOfMyEvents = new ArrayList<Event>();
        //find my events
        for (Event event : EventService.shared.getAllEvents()){
            if (event.getSpeakerUsername().equals(user.getUsername())){
                listOfMyEvents.add(event);
            }
        }
        StringBuilder sb = new StringBuilder();
        //traverse through the list of my events
        for (Event e : listOfMyEvents){
            sb.append(e.toSavableString());
        }
        System.out.println(sb.toString().trim());
    }

    /**
     * Send a message to all attendees in all of this speaker's events
     *
     * @param message
     * @return
     * @throws AuthService.AuthException
     */
    String sendMessageToAllAttendees(String message) throws AuthService.AuthException {

        for(Event event : EventService.shared.getAllEvents()){
            if (event.getSpeakerUsername().equals(user.getUsername())){
                List<String> listOfAttendees = event.getAttendeeUNs();
                for (String attendeeUN : listOfAttendees) {
                    User a = AuthService.shared.getUserByUsername(attendeeUN);
                    MessageService.shared.sendMessage(message, user, a);
                }
            }
        }
        return "process completed";
    }

    /**
     * send a message to a particular user
     *
     * @param message
     * @param receiverUN
     * @return
     */
    String sendMessageTo(String message, String receiverUN) throws AuthService.AuthException {
        if (MessageService.shared.sendMessage(message, user, AuthService.shared.getUserByUsername(receiverUN)))
            return "Message Sent";
        return "Unable to send message";
    }


}
