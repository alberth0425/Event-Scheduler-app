package src.use_cases;

import src.entities.Attendee;
import src.entities.Event;

import java.util.List;

public class SignupService {

    public static SignupService shared = new SignupService();
    private SignupService() {}

    /**
     * Fetch and return all events' String representation.
     *
     * @return a String representing all events; events are seperated by new lines
     */
    public String fetchAllEvents() {
        EventService es = new EventService();
        List<Event> allEvents = es.getAllEvents();

        StringBuilder sb = new StringBuilder();
        for (Event event : allEvents) {
            try {
                String eStr = "Title: " + event.getTitle() +
                        ", Speaker: " + AuthService.shared.getUserByUsername(event.getSpeakerUsername()).getFullname() +
                        ", Remaining Seats: " + es.getEventAvailability(event) + "\n";
                sb.append(eStr);
            } catch (AuthService.AuthException e) {
                System.out.println("Speaker of event <" + event.getTitle() +
                        "> with username: <" + event.getSpeakerUsername() + "> does not exists.");
            } catch (EventService.EventException e) {
                System.out.println("Event <" + event.getTitle() + "> does not exists.");
            }
        }

        return sb.toString().trim();
    }

    /**
     * Sign up an attendee to an event.
     *
     * @param attendee the attendee
     * @param event the event
     * @return true iff sucessfully signed up the attendee to the event
     */
    @SuppressWarnings("UnusedReturnValue")
    public boolean addAttendeeToEvent(Attendee attendee, Event event) {
        EventService es = new EventService();

        try {
            es.addEventAttendee(attendee, event);
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
     * @param event the event
     * @return true iff sucessfully removed the attendee from the event
     */
    @SuppressWarnings("UnusedReturnValue")
    public boolean removeAttendeeFromEvent(Attendee attendee, Event event) {
        EventService es = new EventService();

        try {
            es.removeEventAttendee(attendee, event);
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
