package ui.user.create_room;

import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import ui.BaseViewController;
import ui.navigation.FXMLFile;
import ui.user.UserActionViewController;
import ui.user.create_event.createEventPresenter;
import ui.user.create_event.create_event_success.createEventSuccessViewController;
import ui.user.create_room.create_room_success.createRoomSuccessViewController;

@FXMLFile("createRoom.fxml")
public class createRoomViewController extends BaseViewController<Void> implements createRoomPresenter.createRoomView{
    public TextField roomNumberTextField;
    public TextField roomCapacityTextField;
    public Label errorLabel;

    private final createRoomPresenter presenter = new createRoomPresenter(this);

    public void confirmButtonAction(ActionEvent actionEvent) {
        presenter.onConfirmButtonPressed();
    }

    public void backButtonAction(ActionEvent actionEvent) {
        getNavigationController().navigate(UserActionViewController.class);
    }

    @Override
    public int getRoomNumber() {
        return Integer.parseInt(roomNumberTextField.getText());
    }

    @Override
    public int getRoomCapacity() {
        return Integer.parseInt(roomCapacityTextField.getText());
    }

    @Override
    public void setError(String error) {
        errorLabel.setText(error);
    }

    @Override
    public void navigateToSuccessCreateRoom() {
        getNavigationController().navigate(createRoomSuccessViewController.class);
    }
}
