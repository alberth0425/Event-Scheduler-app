package ui.user.create_account;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import ui.BaseViewController;
import ui.navigation.FXMLFile;
import ui.user.UserActionViewController;
import ui.user.create_account.create_account_successfully.CreateAccountSucessViewController;

@FXMLFile("create_account.fxml")
public class CreateAccountViewController extends BaseViewController<Void> implements CreateAccountPresenter.CreateAccountView {

    private CreateAccountPresenter presenter = new CreateAccountPresenter(this);

    public ComboBox<String> userComboBox;
    public TextField usernameTextField;
    public TextField passwordTextField;
    public TextField firstNameTextField;
    public TextField lastNameTextField;
    public Label errorLabel;
    public CheckBox agreementCheckBox;



    @Override
    public void initializeWithParameters(Void parameters) {

        // TODO: maybe replace the receiver text field with a combo box

        userComboBox.setItems(presenter.getUserTypes());
        userComboBox.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            int selectedIndex = (int) newValue;
            userComboBox.getSelectionModel().select(selectedIndex);
            presenter.onSelectUserType(selectedIndex);
        });

        agreementCheckBox.setVisible(false);

        agreementCheckBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue){
                    presenter.selectCheckBox(true);
                }
            }
        });


    }

    @Override
    public String getUsername() {
        return usernameTextField.getText();
    }

    @Override
    public String getPassword() {
        return passwordTextField.getText();
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
    public void setError(String error) {
        errorLabel.setText(error);
    }

    @Override
    public void setCheckBoxText(String agreement) {
        agreementCheckBox.setText(agreement);
    }


    @Override
    public void navigateToCreateAccountSuccessfully() {
        getNavigationController().navigate(CreateAccountSucessViewController.class);
    }


    @Override
    public void setCheckbox() {
        agreementCheckBox.setVisible(true);
    }

    public void OkButtonAction(ActionEvent actionEvent) {
        presenter.onOkButtonPressed();
    }

    public void backButtonAction(ActionEvent actionEvent) {
        getNavigationController().navigate(UserActionViewController.class);
    }

}

