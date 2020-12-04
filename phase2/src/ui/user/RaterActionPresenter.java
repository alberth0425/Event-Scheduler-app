package ui.user;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ui.event.EventPresenter;
import ui.message.MessageViewController;
import use_cases.AuthService;

public class RaterActionPresenter extends UserActionPresenter {
    private final ObservableList<String> actionList = FXCollections.observableArrayList(
            "Rate Speaker", "Log out"
    );

    RaterActionPresenter(UserActionView view) {
        super(view);

        getView().setUserInfoLabelText("You have logged in as: " + AuthService.shared.getCurrentUser().getUsername()
                + " (Rater)");
        getView().setActionLabelText("Select an action:");
    }


    @Override
    public ObservableList<String> getActionList() {
        return actionList;
    }

    public void onAction(int index) {
        switch (index) {
            case 0:
                getView().navigateToRate();
                break;
            case 1:
                getView().navigateToLogin();
                break;
            default:
                break;
        }
    }
}
