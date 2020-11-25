package ui.user;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class SpeakerActionPresenter extends UserActionPresenter {
    private final ObservableList<String> actionList = FXCollections.observableArrayList();

    public SpeakerActionPresenter(UserActionView view) {
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