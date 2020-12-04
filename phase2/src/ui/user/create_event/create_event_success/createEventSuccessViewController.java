package ui.user.create_event.create_event_success;

import javafx.event.ActionEvent;
import ui.BaseViewController;
import ui.navigation.FXMLFile;
import ui.user.UserActionViewController;

@FXMLFile("rateSuccess.fxml")
public class createEventSuccessViewController extends BaseViewController<Void> {

    public void exitButtonAction(ActionEvent actionEvent) {
        getNavigationController().navigate(UserActionViewController.class);
    }
}
