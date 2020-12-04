package ui.user;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import ui.BaseViewController;
import ui.event.EventPresenter;
import ui.event.EventViewController;
import ui.login.LoginViewController;
import ui.message.MessageViewController;
import ui.rate.RateViewController;
import ui.navigation.FXMLFile;
import ui.user.create_account.CreateAccountViewController;
import ui.util.NoSelectionModel;

@FXMLFile("user_action.fxml")
public class UserActionViewController extends BaseViewController<Void> implements UserActionPresenter.UserActionView {
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
    public void navigateToEvents(EventPresenter.EventFilter filter) {
        getNavigationController().navigate(EventViewController.class, filter);
    }

    @Override
    public void navigateToMessages() {
        getNavigationController().navigate(MessageViewController.class);
    }

    @Override
    public void navigateToLogin() {
        getNavigationController().navigate(LoginViewController.class);
    }

    @Override
    public void navigateToRate() {
        getNavigationController().navigate(RateViewController.class);
    }

    @Override
    public void navigateToCreateAccounts() {
        getNavigationController().navigate(CreateAccountViewController.class);
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
