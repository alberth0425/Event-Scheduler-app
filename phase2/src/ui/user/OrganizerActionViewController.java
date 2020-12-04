package ui.user;

import ui.BaseViewController;
import ui.event.EventViewController;
import ui.message.send_message.SendMessagePresenter;
import ui.user.create_account.CreateAccountViewController;

public class OrganizerActionViewController extends UserActionViewController {



    public static void navigateToCreateAccounts() {
        getNavigationController().navigate(CreateAccountViewController.class);
    }

}
