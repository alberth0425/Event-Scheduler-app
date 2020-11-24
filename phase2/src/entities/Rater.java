package entities;

public class Rater extends User {

    protected int agreementId;

    /**
     * the constructor for rater class.
     *
     * @param username the username of this rater
     * @param password the password this rater needs to login
     * @param firstName the firstname of this rater
     * @param lastName the lastname of this rater
     * @param agreementId the agreement id for this rater
     */
    public Rater(String username, String password, String firstName, String lastName, int agreementId) {
        super(username, password, firstName, lastName);
        this.agreementId = agreementId;
    }

    /**
     * construct organizer from a dataEntry.
     *
     * @param dataEntry the savable string that represents rater
     */
    public Rater(String dataEntry) {
        super(dataEntry);

        String[] entries = dataEntry.split(DELIMITER);

        this.agreementId = Integer.parseInt(entries[5]);
    }

    /**
     * getter for the agreement id
     * @return the agreement id for this rater
     */
    public int getAgreementId(){
        return this.agreementId;
    }

    /**
     * turn the information of this user into a savable string.
     *
     * @return a savable string in the order of: id, username, password, firstname, lastname, agreementid
     */
    @Override
    public String toSavableString() {
        return id + DELIMITER + username + DELIMITER + password + DELIMITER + firstName + DELIMITER + lastName +
                DELIMITER + agreementId;
    }
}
