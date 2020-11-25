package ui.navigation;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import ui.BaseViewController;
import ui.event.EventViewController;
import ui.login.LoginViewController;
import ui.user.UserActionViewController;

import java.io.IOException;

public class NavigationController {
    private final Scene scene;

    private NavigationController(Scene scene) {
        this.scene = scene;
    }

    public static <T extends BaseViewController> Scene initialize(
            Class<T> controllerClass,
            String filename
    ) throws IOException {
        RootAndController<T> pair = load(controllerClass, filename);

        Scene scene = new Scene(pair.root);

        NavigationController navigationController = new NavigationController(scene);
        pair.controller.setNavigationController(navigationController);

        return scene;
    }

    public void navigateTo(Destination destination) {
        try {
            switch (destination) {
                case LOGIN:
                    navigate(LoginViewController.class, "user_action.fxml");
                    break;
                case USER_ACTIONS:
                    navigate(UserActionViewController.class, "user_action.fxml");
                    break;
                case EVENTS:
                    navigate(EventViewController.class, "event.fxml");
                    break;
            }
        } catch (IOException e) {
            System.out.println("Unhandled IOException: " + e.getMessage());
        }
    }

    private <T extends BaseViewController> void navigate(
            Class<T> controllerClass,
            String filename
    ) throws IOException {
        RootAndController<T> pair = load(controllerClass, filename);
        pair.controller.setNavigationController(this);
        scene.setRoot(pair.root);
    }

    private static <T extends BaseViewController> RootAndController<T> load(
            Class<T> controllerClass,
            String filename
    ) throws IOException {
        FXMLLoader loader = new FXMLLoader(controllerClass.getResource(filename));
        Parent root = loader.load();
        T controller = loader.getController();
        return new RootAndController<>(root, controller);
    }

    private static class RootAndController<T extends BaseViewController> {
        Parent root;
        T controller;

        public RootAndController(Parent root, T controller) {
            this.root = root;
            this.controller = controller;
        }
    }
}
