package ui;

import ui.navigation.NavigationController;

public abstract class BaseViewController<P> {
    private NavigationController navigationController;

    /**
     * Initialize the view controller with given parameter, which is passed by the view controller from which we are
     * navigating. This method is executed after the FXML file is loaded and JavaFX initialize method is called.
     *
     * @param parameters the initial parameters
     */
    public void initializeWithParameters(P parameters) {}

    /**
     * Get the NavigationController instance that this view controller uses.
     *
     * @return the navigation controller
     */
    public NavigationController getNavigationController() {
        return navigationController;
    }

    /**
     * Set the navigation controller.
     *
     * @param navigationController the navigation controller that this view controller uses
     */
    public void setNavigationController(NavigationController navigationController) {
        this.navigationController = navigationController;
    }
}
