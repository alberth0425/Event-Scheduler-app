package ui.navigation;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import ui.BaseViewController;

import java.io.IOException;
import java.net.URL;

public class NavigationController {
    private final Scene scene;

    private NavigationController(Scene scene) {
        this.scene = scene;
    }

    public static <P, T extends BaseViewController<P>> Scene initialize(Class<T> controllerClass) throws IOException {
        RootAndController<P, T> pair = load(getFXMLPath(controllerClass));

        Scene scene = new Scene(pair.root);

        NavigationController navigationController = new NavigationController(scene);
        pair.controller.setNavigationController(navigationController);

        return scene;
    }

    public <P, T extends BaseViewController<P>> void navigate(Class<T> controllerClass) {
        navigate(controllerClass, null);
    }

    public <P, T extends BaseViewController<P>> void navigate(Class<T> controllerClass, P params) {
        try {
            RootAndController<P, T> pair = load(getFXMLPath(controllerClass));
            pair.controller.setNavigationController(this);
            pair.controller.initializeWithParameters(params);
            scene.setRoot(pair.root);
        } catch (IOException e) {
            System.out.println("Unhandled IOException: " + e.getMessage());
        }
    }

    private static URL getFXMLPath(Class<?> controllerClass) {
        String filename = controllerClass.getAnnotation(FXMLFile.class).value();
        return controllerClass.getResource(filename);
    }

    private static <P, T extends BaseViewController<P>> RootAndController<P, T> load(URL path) throws IOException {
        FXMLLoader loader = new FXMLLoader(path);
        Parent root = loader.load();
        T controller = loader.getController();
        return new RootAndController<>(root, controller);
    }

    private static class RootAndController<P, T extends BaseViewController<P>> {
        Parent root;
        T controller;

        public RootAndController(Parent root, T controller) {
            this.root = root;
            this.controller = controller;
        }
    }
}
