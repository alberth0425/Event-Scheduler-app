package ui;

import ui.navigation.NavigationController;

public abstract class BaseViewController<P> {
    private NavigationController navigationController;

    public void initializeWithParameters(P parameters) {}

    public NavigationController getNavigationController() {
        return navigationController;
    }

    public void setNavigationController(NavigationController navigationController) {
        this.navigationController = navigationController;
    }
}
