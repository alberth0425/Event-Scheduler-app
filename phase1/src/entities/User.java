package src.entities;

public class User implements Savable {
    protected static int numUser;

    protected String password;
    protected String username;
    protected final int id;

    protected String firstName;
    protected String lastName;

    public User(String username, String password, String firstName, String lastName) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.id = numUser;

        numUser += 1;
    }

    public User(String dataEntry) {
        String[] entries = dataEntry.split(Savable.DELIMITER);
        this.id = Integer.parseInt(entries[0]);
        this.username = entries[1];
        this.password = entries[2];
        this.firstName = entries[3];
        this.lastName = entries[4];

        numUser += 1;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFullname() {
        return firstName + " " + lastName;
    }

    public void setUsername(String newUsername) {
        this.username = newUsername;
    }

    public void setPassword(String newPassword) {
        this.password = newPassword;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toSavableString() {
        return id + Savable.DELIMITER + username + Savable.DELIMITER + password + Savable.DELIMITER + firstName + Savable.DELIMITER + lastName;
    }
}
