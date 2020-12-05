package ui.user.create_account.create_account_successfully;

import ui.BaseViewController;
import ui.navigation.FXMLFile;
import ui.user.UserActionViewController;

import java.awt.event.ActionEvent;

@FXMLFile("create_account_success.fxml")
public class CreateAccountSucessViewController extends BaseViewController {

    public void exitButtonAction(javafx.event.ActionEvent actionEvent) {
        getNavigationController().navigate(UserActionViewController.class);
    }
}
