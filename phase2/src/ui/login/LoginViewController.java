package ui.login;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import ui.BaseViewController;
import ui.navigation.Destination;

import java.io.IOException;

public class LoginViewController extends BaseViewController implements LoginPresenter.LoginView {
    public TextField usernameTextField;
    public PasswordField passwordTextField;
    public Label errorLabel;

    private final LoginPresenter presenter = new LoginPresenter(this);

    public void loginButtonAction(ActionEvent actionEvent) {
        presenter.onLoginButtonPressed();
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
    public void setError(String error) {
        errorLabel.setText(error);
    }

    @Override
    public void navigateToUserView() {
        getNavigationController().navigateTo(Destination.USER_ACTIONS);
    }
}
