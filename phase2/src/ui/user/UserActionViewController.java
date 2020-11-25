package ui.user;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import ui.BaseViewController;
import ui.navigation.Destination;
import ui.util.NoSelectionModel;

public class UserActionViewController extends BaseViewController implements UserActionPresenter.UserActionView {
    public ListView<String> listView;
    public Label userInfoLabel;
    public Label actionLabel;

    private UserActionPresenter presenter;

    public void initialize() {
        presenter = UserActionPresenter.create(this);

        listView.setCellFactory(listView -> new ActionCell());
        listView.setItems(presenter.getActionList());
        listView.setSelectionModel(new NoSelectionModel<>());
    }

    @Override
    public void setUserInfoLabelText(String text) {
        userInfoLabel.setText(text);
    }

    @Override
    public void setActionLabelText(String text) {
        actionLabel.setText(text);
    }

    @Override
    public void navigateToEvents() {
        getNavigationController().navigateTo(Destination.EVENTS);
    }

    class ActionCell extends ListCell<String> {
        @Override
        public void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);

            if (item != null) {
                final Button btn = new Button(item);
                btn.setOnAction(event -> presenter.onAction(getIndex()));
                setGraphic(btn);
            } else {
                setGraphic(null);
            }
        }
    }
}