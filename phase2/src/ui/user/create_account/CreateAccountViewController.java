package ui.user.create_account;

import javafx.beans.property.ReadOnlyIntegerProperty;
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

import java.net.URL;
import java.util.ResourceBundle;

@FXMLFile("create_account.fxml")
public class CreateAccountViewController extends BaseViewController<Void> implements CreateAccountPresenter.CreateAccountView {
    public ComboBox<String> userTypeComboBox;

    private CreateAccountPresenter presenter = new CreateAccountPresenter(this);

    public ComboBox<String> userComboBox;
    public TextField usernameTextField;
    public TextField passwordTextField;
    public TextField firstNameTextField;
    public TextField lastNameTextField;
    public Label errorLabel;
    public TextField userTypeTextField;



    @Override
    public void initializeWithParameters(Void parameters) {

        // TODO: maybe replace the receiver text field with a combo box

        userTypeComboBox.setItems(presenter.getUserTypes());
        userTypeComboBox.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            presenter.onSelectUserType((Integer) newValue);

            userTypeTextField.setText("");

            String prompt = presenter.getReceiverTextPrompt((Integer) newValue);
            if (prompt == null) {
                userTypeTextField.setVisible(false);
            } else {
                userTypeTextField.setVisible(true);
                userTypeTextField.setPromptText(prompt);
            }
        });

        userTypeComboBox.getSelectionModel().select(0);

        userTypeTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            presenter.setUser(newValue);
        });

        usernameTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            presenter.setUsername(newValue);
        });
        passwordTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            presenter.setPassword(newValue);
        });
        firstNameTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            presenter.setFirstName(newValue);
        });
        lastNameTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            presenter.setLastName(newValue);
        });

    }


    @Override
    public void setError(String error) {
        errorLabel.setText(error);
    }

    public void OkButtonAction(ActionEvent actionEvent) {
        presenter.onOkButtonPressed();
    }

    public void backButtonAction(ActionEvent actionEvent) {
    }

}

