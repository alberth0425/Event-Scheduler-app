package ui.user.create_event;

import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import ui.BaseViewController;
import ui.navigation.FXMLFile;
import ui.user.UserActionViewController;
import ui.user.create_event.create_event_success.createEventSuccessViewController;

@FXMLFile("createEvent.fxml")
public class createEventViewController extends BaseViewController<Void> implements createEventPresenter.createEventView{
    public TextField eventTitleTextField;
    public TextField startingTimeTextField;
    public TextField speakerUNTextField;
    public TextField roomNumberTextField;
    public Label errorLabel;

    private final createEventPresenter presenter = new createEventPresenter(this);

    public void confirmButtonAction(ActionEvent actionEvent) {
        presenter.onConfirmButtonPressed();
    }

    public void backButtonAction(ActionEvent actionEvent) {
        getNavigationController().navigate(UserActionViewController.class);
    }

    @Override
    public String getEventTitle() {
        return eventTitleTextField.getText();
    }

    @Override
    public int getStartingTime() {
        return Integer.parseInt(startingTimeTextField.getText());
    }

    @Override
    public String getSpeakerUN() {
        return speakerUNTextField.getText();
    }

    @Override
    public int getRoomNumber() {
        return Integer.parseInt(roomNumberTextField.getText());
    }

    @Override
    public void setError(String error) {
        errorLabel.setText(error);
    }

    @Override
    public void navigateToSuccessCreateEvent() {
        getNavigationController().navigate(createEventSuccessViewController.class);
    }

}
