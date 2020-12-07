package ui.user.create_account.rater_agreement;

import ui.rate.RatePresenter;
import ui.user.create_account.CreateAccountPresenter;
import ui.user.create_account.CreateAccountViewController;
import ui.user.create_account.create_account_successfully.CreateAccountSucessViewController;
import use_cases.AgreementService;
import use_cases.AuthService;

public class RaterAgreementPresenter {

    private final AgreementView view;

    public RaterAgreementPresenter(AgreementView view) {
        this.view = view;
    }

    public void onDisagreeButtonPressed() {
        view.setError("To become a rater, agreement needs to be signed.");
        view.setError("Rater does not create successfully.");
    }


    public void onAgreeButtonPressed(){
        try{
            AgreementService.shared.signAgreement(view.getUsername(), view.getFirstName(), view.getLastName());
            System.out.println("Agreement signed successfully.");
//            try {
//                //Call createUser method in AuthService to create a Rater account.
//                AuthService.shared.createUser(username, password, firstName,
//                        lastName, AuthService.UserType.RATER);
//                System.out.println("Rater created successfully.");
//
//            } catch (AuthService.InvalidFieldException e) {
//                System.out.println("Invalid " + e.getField() + " entered. Rater does not create successfully");
//            } catch (AuthService.UsernameAlreadyTakenException e) {
//                System.out.println("Username " + username + " already taken.");
//            } catch (Exception e) {
//                System.out.println("Unknown exception: " + e.toString() + ". Rater does not create " +
//                        "successfully.");
            //}
        } catch (AgreementService.AgreementAlreadyExistException e) {
            System.out.println("Agreement already exists, no need to sign again.");
        }
    }
//    public void onAgreeButtonPressed() {
//        try{
//            AgreementService.shared.signAgreement(CreateAccountPresenter.view.getUsername(), firstName, lastName);
//            System.out.println("Agreement signed successfully.");
//        } catch (AgreementService.AgreementAlreadyExistException e) {
//            System.out.println("Agreement already exists, no need to sign again.");
//        }
//    }

    public interface AgreementView {
        void setError(String error);
        String getFirstName();
        String getLastName();
        String getUsername();
    }
}
