package src.use_cases;

import src.entities.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AuthService {
    private static final int PASSWORD_MINIMUM_LENGTH = 6;
    
    HashMap<String, User> users = new HashMap<>();

    private User currentUser;

    public static AuthService shared = new AuthService();
    private AuthService() {}

    public void setUsers(HashMap<String, User> users) {
        this.users = users;
    }

    public List<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }
    /**
     * Create a new user with given username, password, first name, and last name.
     * 
     * @param username username of the user
     * @param password password of the user
     * @param firstName first name of the user
     * @param lastName last name of the user
     * @throws UsernameAlreadyTakenException if the username is already taken by another user
     * @throws InvalidFieldException if one of the fields are invalid
     */
    public void createUser(String username, String password, String firstName, String lastName, UserType userType) throws AuthException {
        // Validate user fields
        if (!validateUsername(username)) {
            throw new InvalidFieldException(UserField.USERNAME);
        } else if (!validatePassword(password)) {
            throw new InvalidFieldException(UserField.PASSWORD);
        } else if (!Savable.isStringSavable(firstName)) {
            throw new InvalidFieldException(UserField.FIRST_NAME);
        } else if (!Savable.isStringSavable(lastName)) {
            throw new InvalidFieldException(UserField.LAST_NAME);
        }
        
        // Check whether username already exists
        if (users.containsKey(username)) {
            throw new UsernameAlreadyTakenException();
        }
        
        // Create the new user
        //noinspection EnhancedSwitchMigration
        switch (userType) {
            case SPEAKER:
                Speaker speaker = new Speaker(username, password, firstName, lastName);
                users.put(username, speaker);
                break;
            case ORGANIZER:
                Organizer organizer = new Organizer(username, password, firstName, lastName);
                users.put(username, organizer);
                break;
            case ATTENDEE:
                Attendee attendee = new Attendee(username, password, firstName, lastName);
                users.put(username, attendee);
                break;
        }
    }

    /**
     * Find and return a User by username.
     *
     * @param username the username of the user
     * @return the user
     * @throws AuthException if the user with the specified username does not exist
     */
    public User getUserByUsername(String username) throws AuthException {
        // Check that the user with given username exists
        if (!users.containsKey(username)) {
            throw new UserDoesNotExistException();
        }
        return users.get(username);
    }
    
    /**
     * Modify the login credentials of a user.
     * 
     * @param username the current username of the user whose credentials are to be modified
     * @param newUsername the new username for the user
     * @param newPassword the new password for the user
     * @throws UserDoesNotExistException if the user with the specified username does not exist
     * @throws UsernameAlreadyTakenException if the new username is already taken by another user
     * @throws InvalidFieldException if either the new username or password is invalid
     */
    public void modifyCredentials(String username, String newUsername, String newPassword) throws AuthException {
        // Check that the user with given username exists
        if (!users.containsKey(username)) {
            throw new UserDoesNotExistException();
        }

        // Check that the new username and password are valid
        if (!validateUsername(newUsername)) {
            throw new InvalidFieldException(UserField.USERNAME);
        } else if (!validatePassword(newPassword)) {
            throw new InvalidFieldException(UserField.PASSWORD);
        }
        
        // Check whether the new username is taken, if it is different from the old username
        if (users.containsKey(newUsername) && !username.equals(newUsername)) {
            throw new UsernameAlreadyTakenException();
        }
        
        // Update the user
        User user = users.remove(username);
        user.setUsername(newUsername);
        user.setPassword(newPassword);
        users.put(newUsername, user);
    }

    /**
     * Validate username for a user.
     * 
     * @param username the username to be validated
     * @return true if the username is valid, i.e. it is savable and is not empty
     */
    private static boolean validateUsername(String username) {
        return Savable.isStringSavable(username) && username.length() > 0;
    }
    
    /**
     * Validate password for a user.
     * 
     * @param password the password to be validated
     * @return true if the password is valid, i.e. it is savable and has length of at least PASSWORD_MINIMUM_LENGTH
     */
    private static boolean validatePassword(String password) {
        return Savable.isStringSavable(password) && password.length() >= PASSWORD_MINIMUM_LENGTH;
    }
    
    /**
     * Login a user using username and password.
     *
     * @param username username of the user
     * @param password password of the user
     * @return true if the login was successful, otherwise false
     */
    public boolean loginUser(String username, String password) {
        //Check if the username exists in the hashmap.
        if (!users.containsKey(username))  {
            return false;
        }
        //If the username is in the hashmap,
        //check if the user's password matches the password store in the hashmap.
        User user = users.get(username);
        if (user.getPassword().equals(password)) {
            currentUser = user;
            return true;
        } else {
            //If the passwords do not match, the login failed.
            return false;
        }
    }

    public User getCurrentUser() {
        return currentUser;
    }

    // Custom Exception Classes

    public static class AuthException extends Exception {}
    public static class UserDoesNotExistException extends AuthException {}
    public static class UsernameAlreadyTakenException extends AuthException {}

    public enum UserField {
        USERNAME,
        PASSWORD,
        FIRST_NAME,
        LAST_NAME,
    }

    public enum UserType {
        ATTENDEE,
        ORGANIZER,
        SPEAKER
    }

    public static class InvalidFieldException extends AuthException {
        private final UserField field;

        private InvalidFieldException(UserField field) {
            this.field = field;
        }

        public UserField getField() {
            return field;
        }
    }
}
