package src.entities;

import java.util.List;

public class Attendee extends User {
    private static List<Attendee> allAttendees;

    public Attendee(String username, String password, String firstName, String lastName) {
        super(username, password, firstName, lastName);
    }

    public Attendee(String dataEntry) {
        super(dataEntry);
    }

    public List<Attendee> getAllAttendees() {
        return allAttendees;
    }
}
