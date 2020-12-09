package ui.user.create_event;

import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import ui.BaseViewController;
import ui.navigation.FXMLFile;
import ui.user.UserActionViewController;
import ui.user.create_event.create_event_success.createEventSuccessViewController;

import java.util.ArrayList;
import java.util.List;

@FXMLFile("createEvent.fxml")
public class createEventViewController extends BaseViewController<Void> implements createEventPresenter.createEventView{
    public TextField eventTitleTextField;
    public TextField startingTimeTextField;
    public TextField speakerUNTextField;
    public TextField roomNumberTextField;
    public TextField capacityTextField;
    public TextField durationTextField;
    public ComboBox<String> eventComboBox;
    public Label errorLabel;

    private final createEventPresenter presenter = new createEventPresenter(this);
    public Label speakerUNPromptLabel;

    @Override
    public void initializeWithParameters(Void parameters) {
        eventComboBox.setItems(presenter.getEventTypes());
        eventComboBox.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            int selectedIndex = (int) newValue;
            eventComboBox.getSelectionModel().select(selectedIndex);
            presenter.onSelectEventType(selectedIndex);
        });
        eventComboBox.getSelectionModel().select(0);
    }

    public void confirmButtonAction(ActionEvent actionEvent) {
        presenter.onConfirmButtonPressed();
    }

    public void backButtonAction(ActionEvent actionEvent) {
        getNavigationController().navigate(UserActionViewController.class);
    }

    @Override
    public String getEventTitle() {
        return eventTitleTextField.getText();
    }

    @Override
    public int getStartingTime() {
        return Integer.parseInt(startingTimeTextField.getText());
    }

    @Override
    public int getCapacity() {
        return Integer.parseInt(capacityTextField.getText());
    }

    @Override
    public String getSpeakerUN() {
        return speakerUNTextField.getText();
    }

    @Override
    public int getRoomNumber() {
        return Integer.parseInt(roomNumberTextField.getText());
    }

    @Override
    public int getDuration() {
        return Integer.parseInt(durationTextField.getText());
    }

    @Override
    public void setError(String error) {
        errorLabel.setText(error);
    }

    @Override
    public void setSpeakerUNPrompt(String prompt) {
        speakerUNPromptLabel.setText(prompt);
    }

    @Override
    public void setSpeakerUNVisible(boolean visible) {
        speakerUNPromptLabel.setVisible(visible);
        speakerUNTextField.setVisible(visible);

        // Set whether layout is managed by VBox
        speakerUNPromptLabel.setManaged(visible);
        speakerUNTextField.setManaged(visible);
    }

    @Override
    public void navigateToSuccessCreateEvent() {
        getNavigationController().navigate(createEventSuccessViewController.class);
    }

}
