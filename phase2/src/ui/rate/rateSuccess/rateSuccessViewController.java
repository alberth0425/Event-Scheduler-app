package ui.rate.rateSuccess;

import javafx.event.ActionEvent;
import ui.BaseViewController;
import ui.navigation.FXMLFile;
import ui.user.UserActionViewController;

@FXMLFile("rateSuccess.fxml")
public class rateSuccessViewController extends BaseViewController<Void> {

    public void exitButtonAction(ActionEvent actionEvent) {
        getNavigationController().navigate(UserActionViewController.class);
    }
}
