package ui.user;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ui.event.EventPresenter;
import use_cases.AuthService;

public class AttendeeActionPresenter extends UserActionPresenter {
    private final ObservableList<String> actionList = FXCollections.observableArrayList(
            "Browse events", "View my events", "Messages", "Log out"
    );

    public AttendeeActionPresenter(UserActionView view) {
        super(view);

        getView().setUserInfoLabelText("You have logged in as: " + AuthService.shared.getCurrentUser().getUsername()
                + " (Attendee)");
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
                browseEvents(EventPresenter.EventFilter.SIGNED_UP);
                break;
            case 2:
                getView().navigateToMessages();
                break;
            case 3:
                getView().navigateToLogin();
                break;
            default:
                break;
        }
    }
}
