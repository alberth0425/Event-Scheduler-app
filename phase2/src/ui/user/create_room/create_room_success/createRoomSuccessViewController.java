package ui.user.create_room.create_room_success;

import javafx.event.ActionEvent;
import ui.BaseViewController;
import ui.navigation.FXMLFile;
import ui.user.UserActionViewController;

@FXMLFile("createRoomSuccess.fxml")
public class createRoomSuccessViewController extends BaseViewController<Void> {

    public void exitButtonAction(ActionEvent actionEvent) {
        getNavigationController().navigate(UserActionViewController.class);
    }
}
