package ui.event.create_event;

import javafx.event.ActionEvent;
import ui.BaseViewController;
import ui.event.EventViewController;
import ui.navigation.FXMLFile;

@FXMLFile("create_event.fxml")
public class CreateEventViewController extends BaseViewController<Void> implements CreateEventPresenter.CreateEventView {
    private CreateEventPresenter presenter;

    @Override
    public void initializeWithParameters(Void parameters) {
        presenter = new CreateEventPresenter(this);
    }

    public void backButtonAction(ActionEvent actionEvent) {
        getNavigationController().navigate(EventViewController.class);
    }
}
