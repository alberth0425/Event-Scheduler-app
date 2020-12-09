package ui.register;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ui.user.create_account.CreateAccountPresenter;
import use_cases.AgreementService;
import use_cases.AuthService;

public class RegisterPresenter {
    int userTypeIndex = -1;
    Boolean selectCheckBox = false;

    // TODO private?
    public RegisterPresenterView  view;

    public RegisterPresenter(RegisterPresenterView view) {
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

    public void selectCheckBox(Boolean selected){ selectCheckBox = selected;}


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

                    view.navigateToRegisterSuccess();

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

                    view.navigateToRegisterSuccess();

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

                    view.navigateToRegisterSuccess();

                } catch (AuthService.InvalidFieldException e) {
                    view.setError("Invalid " + e.getField() + " entered. Attendee does not create successfully");
                } catch (AuthService.UsernameAlreadyTakenException e) {
                    view.setError("Username " + view.getUsername() + " attendee already taken.");
                } catch (Exception e) {
                    view.setError("Unknown exception: " + e.toString() + ". Attendee does not create " +
                            "successfully.");
                }
                break;
            case 3:

                view.setError("To become a rater, agreement must be signed.");

                view.setCheckbox();

                view.setCheckBoxText( "I agree to respect every speaker I rate, " +
                        "and to rate using professional \n " +
                        "attitude.");

                if (selectCheckBox){
                    try{
                        AgreementService.shared.signAgreement(view.getUsername(), view.getFirstName(), view.getLastName());
                        try{

                            //Call createUser method in AuthService to create a Rater account.

                            AuthService.shared.createUser(view.getUsername(), view.getPassword(), view.getFirstName(),
                                    view.getLastName(), AuthService.UserType.RATER);

                            view.navigateToRegisterSuccess();

                        } catch (AuthService.InvalidFieldException e) {
                            view.setError("Invalid " + e.getField() + " entered. Rater does not create successfully");
                        } catch (AuthService.UsernameAlreadyTakenException e) {
                            view.setError("Username " + view.getUsername() + " already taken.");
                        } catch (Exception e) {
                            view.setError("Unknown exception: " + e.toString() + ". Rater does not create " +
                                    "successfully.");
                        }
                    } catch (AgreementService.AgreementAlreadyExistException e) {
                        view.setError("Agreement and rater already exists, no need to sign again.");
                    }
                } else {
                    view.setError("To become a rater, agreement must be signed.");
                }

                break;
        }
    }

    public void onOkButtonPressed() {
        handleOkButton(userTypeIndex);
    }

    public interface RegisterPresenterView {
        String getUsername();
        String getPassword();
        String getFirstName();
        String getLastName();
        void setError(String error);
        void setCheckBoxText(String agreement);
        void navigateToRegisterSuccess();
        void setCheckbox();
    }
}
