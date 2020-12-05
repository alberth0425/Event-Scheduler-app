package ui.user.create_account;

import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.TextField;
import ui.BaseViewController;
import ui.message.send_message.SendMessagePresenter;
import ui.navigation.FXMLFile;
import ui.user.OrganizerActionPresenter;
import ui.user.UserActionViewController;

import javax.swing.*;
import java.net.URL;
import java.util.ResourceBundle;

@FXMLFile("create_account.fxml")
public class CreateAccountViewController extends BaseViewController<Void> implements CreateAccountPresenter.CreateAccountView {

    private CreateAccountPresenter presenter = new CreateAccountPresenter(this);

    public ComboBox<String> userComboBox;
    public TextField usernameTextField;
    public TextField passwordTextField;
    public TextField firstNameTextField;
    public TextField lastNameTextField;
    public Label errorLabel;



    @Override
    public void initializeWithParameters(Void parameters) {

        // TODO: maybe replace the receiver text field with a combo box

        userComboBox.setItems(presenter.getUserTypes());
        userComboBox.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            int selectedIndex = (int) newValue;
            userComboBox.getSelectionModel().select(selectedIndex);
            presenter.onSelectUserType(selectedIndex);
        });


    }

    @Override
    public String getUsername() {
        return usernameTextField.getText();
    }

    @Override
    public String getPassword() {
        return passwordTextField.getText();
    }

    @Override
    public String getFirstName() {
        return firstNameTextField.getText();
    }

    @Override
    public String getLastName() {
        return lastNameTextField.getText();
    }

    @Override
    public void setError(String error) {
        errorLabel.setText(error);
    }

    public void OkButtonAction(ActionEvent actionEvent) {
        presenter.onOkButtonPressed();
    }

    public void backButtonAction(ActionEvent actionEvent) {
        getNavigationController().navigate(UserActionViewController.class);
    }

}

