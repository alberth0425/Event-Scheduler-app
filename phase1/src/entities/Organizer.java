package src.entities;

import java.util.List;

public class Organizer extends User {

    /**
     * the constructor for organizer class.
     *
     * @param username the username of this organizer
     * @param password the password this organizer needs to login
     * @param firstName the firstname of this organizer
     * @param lastName the lastname of this organizer
     */
    public Organizer(String username, String password, String firstName, String lastName) {
        super(username, password, firstName, lastName);
    }

    /**
     * construct organizer from a dataEntry.
     *
     * @param dataEntry the savable string that represents organizer
     */
    public Organizer(String dataEntry) {
        super(dataEntry);
    }
}
