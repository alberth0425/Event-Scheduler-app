package ui.user.create_account;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TextField;
import ui.message.send_message.SendMessagePresenter;
import ui.rate.RatePresenter;
import use_cases.AuthService;

public class CreateAccountPresenter {

    int userTypeIndex = -1;
    public String username = "";
    public String password = "";
    public String firstName = "";
    public String lastName = "";
    public String user = "";

    private final CreateAccountPresenter.CreateAccountView view;

    public CreateAccountPresenter(CreateAccountView view) {
        this.view = view;
    }

    /**
     * Get the list of strings, each representing the user type to create.
     *
     * @return the list of user types
     */

    public ObservableList<String> getUserTypes() {
        return FXCollections.observableArrayList(
                "Create Speaker",
                "Create Organizer",
                "Create Attendee",
                "Create Rater"
        );
    }

    /**
     * Handle that a user type is selected.
     *
     * @param index the index of the user type
     */
    public void onSelectUserType(int index) {
        userTypeIndex = index;
    }

    /**
     * Handle the ok button action.
     */
    /**
     * Handle the ok button action.
     */
    public void handleOkButton(int receiverTypeIndex, String username, String password, String firstName,
                               String lastName) {
        switch (receiverTypeIndex) {
            case 0:
                try {
                    //Call createUser method in AuthService to create a speaker account.
                    AuthService.shared.createUser(username, password, firstName,
                            lastName, AuthService.UserType.SPEAKER);
                    System.out.println("Speaker created successfully.");

                } catch (AuthService.UserDoesNotExistException e) {
                    System.out.println("User with username " + username + " does not exist. " + "Speaker does not create " +
                            "successfully.");
                    // TODO: 需要catch这个exception吗？This includes all user types.
                } catch (AuthService.InvalidFieldException e) {
                    System.out.println("Invalid " + e.getField() + " entered. Speaker does not create successfully");
                } catch (AuthService.UsernameAlreadyTakenException e) {
                    System.out.println("Username " + username + " already taken.");
                } catch (Exception e) {
                    System.out.println("Unknown exception: " + e.toString() + ". Speaker does not create " +
                            "successfully.");
                }
            case 1:
                try {
                    //Call createUser method in AuthService to create an Organizer account.
                    AuthService.shared.createUser(username, password, firstName,
                            lastName, AuthService.UserType.ORGANIZER);
                    System.out.println("Organizer created successfully.");

                } catch (AuthService.InvalidFieldException e) {
                    System.out.println("Invalid " + e.getField() + " entered. Organizer does not create successfully");
                } catch (AuthService.UsernameAlreadyTakenException e) {
                    System.out.println("Username " + username + " already taken.");
                } catch (Exception e) {
                    System.out.println("Unknown exception: " + e.toString() + ". Organizer does not create " +
                            "successfully.");
                }
            case 2:
                try {
                    //Call createUser method in AuthService to create an attendee account.
                    AuthService.shared.createUser(username, password, firstName,
                            lastName, AuthService.UserType.ATTENDEE);
                    System.out.println("Attendee created successfully.");

                } catch (AuthService.InvalidFieldException e) {
                    System.out.println("Invalid " + e.getField() + " entered. Attendee does not create successfully");
                } catch (AuthService.UsernameAlreadyTakenException e) {
                    System.out.println("Username " + username + " already taken.");
                } catch (Exception e) {
                    System.out.println("Unknown exception: " + e.toString() + ". Attendee does not create " +
                            "successfully.");
                }
        }
    }

    public void onOkButtonPressed() {
        handleOkButton(userTypeIndex, username, password, firstName, lastName);
    }

    public void onSelectReceiverType(int index) {
        userTypeIndex = index;
    }

    /**
     * Get the text prompt to show in the receiver text field for the receiver type at given index.
     * If a text field is not needed, return null.
     *
     * @param index the index of the selected receiver type
     * @return text prompt for the receiver text field if it is needed, otherwise null
     */
    public String getReceiverTextPrompt(int index) {
        return null;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setUser(String user) {
        this.user = user;
    }


    public interface CreateAccountView {
        void setError(String error);
    }
}
