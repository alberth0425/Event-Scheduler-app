package ui.user.create_account.rater_agreement;

import javafx.event.ActionEvent;
import javafx.scene.control.Labeled;
import javafx.scene.control.TextField;
import ui.BaseViewController;
import ui.navigation.FXMLFile;
import javafx.scene.control.Label;
import ui.user.create_account.CreateAccountViewController;
import ui.user.create_account.create_account_successfully.CreateAccountSucessViewController;

@FXMLFile("rater_agreement.fxml")
public class RaterAgreementViewController extends BaseViewController implements RaterAgreementPresenter.AgreementView{

    public Label agreementLabel;
    public Label errorLabel;
    public TextField firstNameTextField;
    public TextField lastNameTextField;
    public TextField usernameTextField;
    private final RaterAgreementPresenter presenter = new RaterAgreementPresenter(this);

    public void initialize(){
        agreementLabel.setText("I agree to respect every speaker I rate, \" +\n" +
                "                    \"and to rate every speaker using professional attitude in which I will not give out rate on my own \" +\n" +
                "                    \"personal emotion.");
        agreementLabel.setMaxWidth(180);
        agreementLabel.setWrapText(true);

    }

    public void disagreeButtonAction(ActionEvent actionEvent) {
        presenter.onDisagreeButtonPressed();
    }

    public void agreeButtonAction(ActionEvent actionEvent) {

        // TODO: Check duplicate exception before creating
        presenter.onAgreeButtonPressed();
    }


    @Override
    public void setError(String error) {
        errorLabel.setText(error);
    }

    @Override
    public String getFirstName() {
        return firstNameTextField.getText();
    }

    @Override
    public String getLastName() {
        return lastNameTextField.getText();
    }

    @Override
    public String getUsername() {
        return usernameTextField.getText();
    }

    public void backButtonAction(ActionEvent actionEvent) {
        getNavigationController().navigate(CreateAccountViewController.class);
    }
}
