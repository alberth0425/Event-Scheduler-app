package entities;

public class Agreement implements Savable {

    protected String raterUserName;
    protected String firstName;
    protected String lastName;

    /**
     * the constructor for user class.
     *
     * @param raterUserName the username the rate signed the agreement
     * @param firstName the firstname of the rate signed the agreement
     * @param lastName the lastname of the rate signed the agreement
     */
    public Agreement(String raterUserName, String firstName, String lastName) {
        this.raterUserName = raterUserName;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    /**
     * construct agreement from a dataEntry.
     *
     * @param dataEntry the savable string that represents this user
     *                  the order of the string is: id, raterUserName, firstname, lastname
     */
    public Agreement(String dataEntry) {
        String[] entries = dataEntry.split(DELIMITER);
        this.raterUserName = entries[0];
        this.firstName = entries[1];
        this.lastName = entries[2];
    }

    /**
     *  getter for the username.
     *
     * @return the name of the user
     */
    public String getUsername() {
        return raterUserName;
    }

    /**
     *  getter for the full name of this user.
     *
     * @return the first name followed by the last name
     */
    public String getFullName() {
        return firstName + " " + lastName;
    }

    /**
     * turn the information of this user into a savable string.
     *
     * @return a savable string in the order of: raterUserName, firstname, lastname
     */
    @Override
    public String toSavableString() {
        return raterUserName + DELIMITER + firstName + DELIMITER + lastName;
    }

    /**
     * generator of the agreement
     *
     * @return a string representation of the agreement the rater signed
     */
    @Override
    public String toString() {
        return "I, " + getFullName() + ", agree to respect every speaker I rate, and to rate every speaker using " +
                "professional attitude in which I will not give out rate on my own personal emotion.";
    }
}
