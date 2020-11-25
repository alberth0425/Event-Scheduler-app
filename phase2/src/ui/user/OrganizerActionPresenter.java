package ui.user;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class OrganizerActionPresenter extends UserActionPresenter {
    private final ObservableList<String> actionList = FXCollections.observableArrayList();

    public OrganizerActionPresenter(UserActionView view) {
        super(view);
    }

    @Override
    public ObservableList<String> getActionList() {
        return actionList;
    }

    @Override
    public void onAction(int index) {

    }
}
