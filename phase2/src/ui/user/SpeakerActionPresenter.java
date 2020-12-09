package ui.user;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ui.event.EventPresenter;
import use_cases.AuthService;

public class SpeakerActionPresenter extends UserActionPresenter {
    private final ObservableList<String> actionList = FXCollections.observableArrayList(
            "Browse events", "My events", "Messages", "View my rate", "Log out"
    );

    public SpeakerActionPresenter(UserActionView view) {
        super(view);

        getView().setUserInfoLabelText("You have logged in as: " + AuthService.shared.getCurrentUser().getUsername()
                + " (Speaker)");
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
                browseEvents(EventPresenter.EventFilter.GIVING_SPEECH);
                break;
            case 2:
                getView().navigateToMessages();
                break;
            case 3:
                getView().navigateToViewRate();
                break;
            case 4:
                getView().navigateToLogin();
                break;
            default:
                break;
        }
    }
}