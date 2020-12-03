package entities;

public class User implements Savable {
    protected static int numUser;

    protected String password;
    protected String username;
    protected final int id;

    protected String firstName;
    protected String lastName;

    /**
     * the constructor for user class.
     *
     * @param username the username of this user
     * @param password the password this user needs to login
     * @param firstName the firstname of this user
     * @param lastName the lastname of this user
     */
    public User(String username, String password, String firstName, String lastName) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.id = numUser;

        numUser += 1;
    }

    /**
     * construct user from a dataEntry.
     *
     * @param dataEntry the savable string that represents this user
     *                  the order of the string is: id, username, password, first name and last name
     */
    public User(String dataEntry) {
        this.id = Integer.parseInt(dataEntry.split(DELIMITER)[0]);
        this.username = dataEntry.split(DELIMITER)[1];
        this.password = dataEntry.split(DELIMITER)[2];
        this.firstName = dataEntry.split(DELIMITER)[3];
        this.lastName = dataEntry.split(DELIMITER)[4];

        numUser += 1;
    }

    /**
     *  getter for the username.
     *
     * @return the name of the user
     */
    public String getUsername() {
        return username;
    }

    /**
     *  getter for the password.
     *
     * @return the password of the user
     */
    public String getPassword() {
        return password;
    }

    /**
     *  getter for the full name of this user.
     *
     * @return the first name followed by the last name
     */
    public String getFullname() {
        return firstName + " " + lastName;
    }

    /**
     * setter for the username.
     *
     * @param newUsername a new username to this user
     */
    public void setUsername(String newUsername) {
        this.username = newUsername;
    }

    /**
     * setter for the password.
     *
     * @param newPassword a new password to this user
     */
    public void setPassword(String newPassword) {
        this.password = newPassword;
    }

    /**
     * getter for this id of this user.
     *
     * @return the id of this user
     */
    public int getId() {
        return id;
    }

    /**
     * turn the information of this user into a savable string.
     *
     * @return a savable string in the order of: id, username, password, firstname, lastname
     */
    @Override
    public String toSavableString() {
        return id + DELIMITER + username + DELIMITER + password + DELIMITER + firstName + DELIMITER + lastName;
    }
}
