package entities;

import java.text.MessageFormat;

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
        String type;
        if (this instanceof Attendee) {
            type = "attendee";
            return MessageFormat.format("\"username\": \"{0}\",\"password\": \"{1}\",\"first_name\": \"{2}\", " +
                    "\"last_name\": \"{3}\",\"user_type\": \"{4}\"",username, password, firstName, lastName, type);
        } else if (this instanceof Organizer) {
            type = "organizer";
            return MessageFormat.format("\"username\": \"{0}\",\"password\": \"{1}\",\"first_name\": \"{2}\", " +
                    "\"last_name\": \"{3}\",\"user_type\": \"{4}\"",username, password, firstName, lastName, type);
        } else if (this instanceof Speaker) {
            type = "Speaker";

            StringBuilder rateBuilder = new StringBuilder();
            rateBuilder.append("[");
            for (String rate : ((Speaker) this).allRate) {
                rateBuilder.append(MessageFormat.format("\"{0}\",", rate));
            }

            // If there are at least 1 attendee, remove the last ","
            if (((Speaker) this).allRate.size() >= 1) rateBuilder.deleteCharAt(rateBuilder.length() - 1);
            rateBuilder.append("]");
            String rateStr = rateBuilder.toString();

            return MessageFormat.format("\"username\": \"{0}\",\"password\": \"{1}\",\"first_name\": \"{2}\", " +
                    "\"last_name\": \"{3}\",\"user_type\": \"{4}\",\"rate\": {5}",
                    username, password, firstName, lastName, type, rateStr);

        } else if (this instanceof Rater) {
            type = "Rater";

            StringBuilder rateBuilder = new StringBuilder();
            rateBuilder.append("[");
            for (String id : ((Rater) this).speakerIdRated) {
                rateBuilder.append(MessageFormat.format("\"{0}\",", id));
            }

            // If there are at least 1 attendee, remove the last ","
            if (((Rater) this).speakerIdRated.size() >= 1) rateBuilder.deleteCharAt(rateBuilder.length() - 1);
            rateBuilder.append("]");
            String speakerIdRatedStr = rateBuilder.toString();

            return MessageFormat.format("\"username\": \"{0}\",\"password\": \"{1}\",\"first_name\": \"{2}\", " +
                            "\"last_name\": \"{3}\",\"user_type\": \"{4}\",\"speaker_id_rated\": {5}",
                    username, password, firstName, lastName, type, speakerIdRatedStr);

        } else {
            type = "undetermined";
            return MessageFormat.format("\"username\": \"{0}\",\"password\": \"{1}\",\"first_name\": \"{2}\", " +
                    "\"last_name\": \"{3}\",\"user_type\": \"{4}\"",username, password, firstName, lastName, type);
        }


    }

    @Override
    public String toString() {
        String userType;
        if (this instanceof Attendee) {
            userType = "Attendee";
            return userType + "{" +
                    "password='" + password + '\'' +
                    ", username='" + username + '\'' +
                    ", id=" + id +
                    ", firstName='" + firstName + '\'' +
                    ", lastName='" + lastName + '\'' +
                    '}';
        } else if (this instanceof Organizer) {
            userType = "Organizer";
            return userType + "{" +
                    "password='" + password + '\'' +
                    ", username='" + username + '\'' +
                    ", id=" + id +
                    ", firstName='" + firstName + '\'' +
                    ", lastName='" + lastName + '\'' +
                    '}';
        } else if (this instanceof Speaker) {
            userType = "Speaker";

            StringBuilder rateBuilder = new StringBuilder();
            rateBuilder.append("[");
            for (String rate : ((Speaker) this).allRate) {
                rateBuilder.append(MessageFormat.format("\"{0}\",", rate));
            }

            // If there are at least 1 attendee, remove the last ","
            if (((Speaker) this).allRate.size() >= 1) rateBuilder.deleteCharAt(rateBuilder.length() - 1);
            rateBuilder.append("]");
            String rateStr = rateBuilder.toString();

            return userType + "{" +
                    "password='" + password + '\'' +
                    ", username='" + username + '\'' +
                    ", id=" + id +
                    ", firstName='" + firstName + '\'' +
                    ", lastName='" + lastName + '\'' +
                    ", rate='" + rateStr + '\'' +
                    '}';
        } else if (this instanceof Rater) {
            userType = "Rater";

            StringBuilder rateBuilder = new StringBuilder();
            rateBuilder.append("[");
            for (String id : ((Rater) this).speakerIdRated) {
                rateBuilder.append(MessageFormat.format("\"{0}\",", id));
            }

            // If there are at least 1 attendee, remove the last ","
            if (((Rater) this).speakerIdRated.size() >= 1) rateBuilder.deleteCharAt(rateBuilder.length() - 1);
            rateBuilder.append("]");
            String speakerIdRatedStr = rateBuilder.toString();

            return userType + "{" +
                    "password='" + password + '\'' +
                    ", username='" + username + '\'' +
                    ", id=" + id +
                    ", firstName='" + firstName + '\'' +
                    ", lastName='" + lastName + '\'' +
                    ", speaker rated='" + speakerIdRatedStr + '\'' +
                    '}';

        } else {
            userType = "Unknown User";
            return userType + "{" +
                    "password='" + password + '\'' +
                    ", username='" + username + '\'' +
                    ", id=" + id +
                    ", firstName='" + firstName + '\'' +
                    ", lastName='" + lastName + '\'' +
                    '}';
        }
    }
}
