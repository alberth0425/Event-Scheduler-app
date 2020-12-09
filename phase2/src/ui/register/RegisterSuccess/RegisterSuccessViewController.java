package ui.register.RegisterSuccess;

import ui.BaseViewController;
import ui.login.LoginViewController;
import ui.navigation.FXMLFile;

import javafx.event.ActionEvent;

@FXMLFile("registerSuccess.fxml")
public class RegisterSuccessViewController extends BaseViewController {

    public void exitButtonAction(ActionEvent actionEvent) {
        getNavigationController().navigate(LoginViewController.class);
    }
}
