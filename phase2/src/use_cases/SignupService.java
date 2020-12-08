package use_cases;

import entities.*;

import java.util.ArrayList;
import java.util.List;

public class SignupService {

    /**
     * singleton implementation.
     */
    public static SignupService shared = new SignupService();

    private SignupService() {
    }

    /**
     * Fetch and return all events' String representation.
     *
     * @return a String representing all events; events are seperated by new lines
     */
    public String fetchAllEvents() {
        List<Event> allEvents = EventService.shared.getAllEvents();

        StringBuilder sb = new StringBuilder();
        for (Event event : allEvents) {
            //if the event is a talk
            if (event instanceof Talk) {
                Talk talk = (Talk) event;
                try {
                    String eStr = "Event ID: " + event.getId() + ", Title: " + event.getTitle() +
                            ", Speaker: "
                            + AuthService.shared.getUserByUsername(talk.getSpeakerUsername()).getFullname() +
                            ", Remaining Seats: " + EventService.shared.getEventAvailability(event) + "\n";
                    sb.append(eStr);
                } catch (AuthService.AuthException e) {
                    System.out.println("Speaker of event <" + event.getTitle() +
                            "> with username: <" + talk.getSpeakerUsername() + "> does not exist.");
                }
            }
            //if the event is a party
            else if (event instanceof Party) {
                Party talk = (Party) event;

                String eStr = "Event ID: " + event.getId() + ", Title: " + event.getTitle() +
                        ", Remaining Seats: " + EventService.shared.getEventAvailability(event) + "\n";
                sb.append(eStr);

            }
            //if the event is a panel discussion
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

        return sb.toString().trim();
    }

    /**
     * Sign up an attendee to an event.
     *
     * @param attendee the attendee
     * @param event    the event
     * @return true iff sucessfully signed up the attendee to the event
     */
    @SuppressWarnings("UnusedReturnValue")
    public boolean addAttendeeToEvent(Attendee attendee, Event event) {
        try {
            EventService.shared.addEventAttendee(attendee, event);
        } catch (EventService.EventDoesNotExistException e) {
            System.out.println("Event <" + event.getTitle() + "> does not exist.");
            return false;
        } catch (Exception e) {
            System.out.println("Unknown Exception: " + e.toString());
            return false;
        }
        return true;
    }

    /**
     * Remove up an attendee from an event.
     *
     * @param attendee the attendee
     * @param event    the event
     * @return true iff sucessfully removed the attendee from the event
     */
    @SuppressWarnings("UnusedReturnValue")
    public boolean removeAttendeeFromEvent(Attendee attendee, Event event) {
        try {
            EventService.shared.removeEventAttendee(attendee, event);
        } catch (EventService.EventDoesNotExistException e) {
            System.out.println("Event <" + event.getTitle() + "> does not exist.");
            return false;
        } catch (Exception e) {
            System.out.println("Unknown Exception: " + e.toString());
            return false;
        }
        return true;
    }
}
