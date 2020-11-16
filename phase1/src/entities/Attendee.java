package src.entities;

import java.util.List;

public class Attendee extends User {

    public Attendee(String username, String password, String firstName, String lastName) {
        super(username, password, firstName, lastName);
    }

    public Attendee(String dataEntry) {
        super(dataEntry);
    }
}
