package ui.user.create_account;

import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TextField;
import ui.message.send_message.SendMessagePresenter;
import ui.rate.RatePresenter;
import use_cases.AuthService;

public class CreateAccountPresenter {

    int userTypeIndex = -1;


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
     * Handle that a receiver type is selected.
     *
     * @param index the index of the receiver type
     */
    public void onSelectUserType(int index) {
        userTypeIndex = index;
    }


    /**
     * Handle the ok button action.
     */
    public void handleOkButton(int userTypeIndex) {
        switch (userTypeIndex) {
            case 0:
                try {
                    //Call createUser method in AuthService to create a speaker account.
                    AuthService.shared.createUser(view.getUsername(), view.getPassword(), view.getFirstName(),
                            view.getLastName(), AuthService.UserType.SPEAKER);

                    view.navigateToCreateAccountSuccessfully();

                } catch (AuthService.InvalidFieldException e) {
                    view.setError("Invalid " + e.getField() + " entered. Speaker does not create successfully");
                } catch (AuthService.UsernameAlreadyTakenException e) {
                    view.setError("Username " + view.getUsername() + " speaker already taken.");
                } catch (Exception e) {
                    view.setError("Unknown exception: " + e.toString() + ". Speaker does not create " +
                            "successfully.");
                }
                break;
            case 1:
                try {
                    //Call createUser method in AuthService to create an Organizer account.
                    AuthService.shared.createUser(view.getUsername(), view.getPassword(), view.getFirstName(),
                            view.getLastName(), AuthService.UserType.ORGANIZER);

                    view.navigateToCreateAccountSuccessfully();

                } catch (AuthService.InvalidFieldException e) {
                    view.setError("Invalid " + e.getField() + " entered. Organizer does not create successfully");
                } catch (AuthService.UsernameAlreadyTakenException e) {
                    view.setError("Username " + view.getUsername() + " organizer already taken.");
                } catch (Exception e) {
                    view.setError("Unknown exception: " + e.toString() + ". Organizer does not create " +
                            "successfully.");
                }
                break;
            case 2:
                try {
                    //Call createUser method in AuthService to create an attendee account.
                    AuthService.shared.createUser(view.getUsername(), view.getPassword(), view.getFirstName(),
                            view.getLastName(), AuthService.UserType.ATTENDEE);

                    view.navigateToCreateAccountSuccessfully();

                } catch (AuthService.InvalidFieldException e) {
                    view.setError("Invalid " + e.getField() + " entered. Attendee does not create successfully");
                } catch (AuthService.UsernameAlreadyTakenException e) {
                    view.setError("Username " + view.getUsername() + " attendee already taken.");
                } catch (Exception e) {
                    view.setError("Unknown exception: " + e.toString() + ". Attendee does not create " +
                            "successfully.");
                }
                break;
        }
    }

    public void onOkButtonPressed() {
        handleOkButton(userTypeIndex);
    }

    public interface CreateAccountView {
        String getUsername();
        String getPassword();
        String getFirstName();
        String getLastName();
        void setError(String error);
        void navigateToCreateAccountSuccessfully();
    }
}
