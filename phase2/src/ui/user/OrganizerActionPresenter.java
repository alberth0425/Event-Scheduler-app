package ui.user;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ui.event.EventPresenter;
import use_cases.AuthService;

public class OrganizerActionPresenter extends UserActionPresenter {
    private final ObservableList<String> actionList = FXCollections.observableArrayList(
            "Browse events", "Messages", "Create Account", "Create Event", "Create Room", "Log out"
    );

    public OrganizerActionPresenter(UserActionView view) {
        super(view);

        getView().setUserInfoLabelText("You have logged in as: " + AuthService.shared.getCurrentUser().getUsername()
                + " (Organizer)");
        getView().setActionLabelText("Select an action:");
    }

    @Override
    public ObservableList<String> getActionList() {
        return actionList;
    }

    @Override
    public void onAction(int index) {
        switch (index) {
            case 0:
                browseEvents(EventPresenter.EventFilter.ALL);
                break;
            case 1:
                getView().navigateToMessages();
                break;
            case 2:
                OrganizerActionViewController.navigateToCreateAccounts();
                break;
            case 3:
                getView().navigateToCreateEvent();
                break;
            case 4:
                getView().navigateToCreateRoom();
                break;
            case 5:
                getView().navigateToLogin();
            default:
                break;
        }
    }
}
