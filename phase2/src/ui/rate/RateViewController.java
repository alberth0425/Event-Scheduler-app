package ui.rate;

import com.sun.xml.internal.ws.util.StringUtils;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import ui.BaseViewController;
import ui.rate.RatePresenter;
import ui.navigation.FXMLFile;
import ui.user.UserActionViewController;

@FXMLFile("rate.fxml")
public class RateViewController extends BaseViewController<Void> implements RatePresenter.RateView{
    public TextField speakerUNTextField;
    public TextField rateGivenTextField;
    public Label errorLabel;

    private final RatePresenter presenter = new RatePresenter(this);

    public void OkButtonAction(ActionEvent actionEvent) {
        presenter.onOkButtonPressed();
    }

    public void backButtonAction(ActionEvent actionEvent) {
        getNavigationController().navigate(UserActionViewController.class);
    }

    @Override
    public String getSpeakerUsername() {
        return speakerUNTextField.getText();
    }

    @Override
    public int getRate() {
        return Integer.parseInt(rateGivenTextField.getText());
    }

    @Override
    public void setError(String error) {
        errorLabel.setText(error);
    }

    @Override
    public void navigateToSuccessRateView() {

    }
}
