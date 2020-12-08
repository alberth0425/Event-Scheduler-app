package ui.rate.ViewRate;

import entities.Speaker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import ui.BaseViewController;
import ui.navigation.FXMLFile;
import ui.user.UserActionViewController;
import use_cases.AuthService;

@FXMLFile("ViewRate.fxml")
public class ViewRateViewController extends BaseViewController<Void> implements ViewRatePresenter.ViewRateView {
    @FXML
    public Label rateLabel;

    @FXML
    public void initialize(){
        Speaker speaker = (Speaker) AuthService.shared.getCurrentUser();
        rateLabel.setText(Double.toString(speaker.getAverageRate()));
    }

    public void backButtonAction(ActionEvent actionEvent) {
        getNavigationController().navigate(UserActionViewController.class);
    }
}
