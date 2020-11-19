package src.entities;

import java.util.List;

public class Speaker extends User {

    public Speaker(String username, String password, String firstName, String lastName) {
        super(username, password, firstName, lastName);
    }

    public Speaker(String dataEntry) {
        super(dataEntry);
    }
}
