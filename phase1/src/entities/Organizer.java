package src.entities;

import java.util.List;

public class Organizer extends User {
    private static List<Organizer> allOrganizers;

    public Organizer(String username, String password, String firstName, String lastName) {
        super(username, password, firstName, lastName);
    }

    public Organizer(String dataEntry) {
        super(dataEntry);
    }

    public List<Organizer> getAllOrganizers() {
        return allOrganizers;
    }
}
