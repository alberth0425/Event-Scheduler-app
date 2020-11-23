package entities;

public class Attendee extends User {

    /**
     * the constructor for attendee class.
     *
     * @param username the username of this attendee
     * @param password the password this attendee needs to login
     * @param firstName the firstname of this attendee
     * @param lastName the lastname of this attendee
     */
    public Attendee(String username, String password, String firstName, String lastName) {
        super(username, password, firstName, lastName);
    }

    /**
     * construct attendee from a dataEntry.
     *
     * @param dataEntry the savable string that represents attendee
     */
    public Attendee(String dataEntry) {
        super(dataEntry);
    }
}
