package ui.rate.ViewSpeakerRate;

import javafx.event.ActionEvent;
import ui.BaseViewController;
import ui.user.UserActionViewController;

public class ViewSpeakerRateViewController extends BaseViewController<Void> {

    public void backButtonAction(ActionEvent actionEvent) {
        getNavigationController().navigate(UserActionViewController.class);
    }
}
