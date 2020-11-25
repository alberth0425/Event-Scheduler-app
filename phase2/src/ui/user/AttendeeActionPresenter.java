package ui.user;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import use_cases.AuthService;

public class AttendeeActionPresenter extends UserActionPresenter {
    private final ObservableList<String> actionList = FXCollections.observableArrayList(
            "Browse events", "View my events", "Send message"
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
                browseEvents();
                break;
            case 1:
                // TODO: hmm, figure out how to display events "generically"
                // allow sign up, cancel, if needed.
                break;
            case 2:
                break;
            default:
                break;
        }
    }
}
