package ui.user;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import use_cases.AuthService;

public class OrganizerActionPresenter extends UserActionPresenter {
    private final ObservableList<String> actionList = FXCollections.observableArrayList("Browse events");

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
                browseEvents();
                break;
            default:
                break;
        }
    }
}
