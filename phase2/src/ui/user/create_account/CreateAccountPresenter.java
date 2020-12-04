package ui.user.create_account;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ui.event.EventPresenter;
import ui.message.send_message.SendMessagePresenter;
import ui.user.OrganizerActionPresenter;
import ui.user.OrganizerActionViewController;
import ui.user.UserActionPresenter;
import use_cases.AuthService;

public class CreateAccountPresenter extends OrganizerActionPresenter {

    private final ObservableList<String> actionList = FXCollections.observableArrayList(
            "Create Speaker", "Create Attendee", "Create Organizer", "Create Rater"
    );

    public CreateAccountPresenter(UserActionPresenter.UserActionView view) {
        super(view);

    }

    @Override
    public ObservableList<String> getActionList() {
        return actionList;
    }
    public ObservableList<String> getActionList() {
        return actionList;
    }

    public void onAction(int index) {
        switch (index) {
            case 0:

                break;
            case 1:
                create
                break;
            case 2:
                OrganizerActionViewController.navigateToCreateAccounts();
            case 3:
                getView().navigateToLogin();
            default:
                break;
        }
    }
}
