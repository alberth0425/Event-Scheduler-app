package ui;

import ui.navigation.NavigationController;

public class BaseViewController {
    private NavigationController navigationController;

    public NavigationController getNavigationController() {
        return navigationController;
    }

    public void setNavigationController(NavigationController navigationController) {
        this.navigationController = navigationController;
    }
}
