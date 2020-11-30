package ui.util;

import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class TextFieldPrompt {
    /**
     * Create a text field prompt with a confirm button and cancel button.
     *
     * @param promptText the prompt text
     * @param buttonText the text to display on the button
     * @param validator a validator that takes a string and returns the error message, or null if valid. The text field
     *                  will dismiss whenever this returns null.
     * @param dismissAction the action to run when the text field dismisses
     * @return the node containing the text field
     */
    public static Node create(String promptText, String buttonText, Validator validator, ActionHandler dismissAction) {
        TextField textField = new TextField();
        textField.setPromptText(promptText);

        Button confirmButton = new Button(buttonText);
        confirmButton.setOnAction(event -> {
            String errorMessage = validator.getError(textField.getText());
            if (errorMessage == null) {
                dismissAction.run();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, errorMessage);
                alert.show();
            }
        });

        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(event -> dismissAction.run());

        HBox hBox = new HBox();
        hBox.setSpacing(10);
        hBox.getChildren().setAll(textField, confirmButton, cancelButton);
        HBox.setHgrow(textField, Priority.ALWAYS);

        return hBox;
    }

    public interface Validator {
        /**
         * Get the error message for given text, or null if there is no error.
         *
         * @param text the text to validate
         * @return the error message if there is an error, otherwise null
         */
        String getError(String text);
    }

    public interface ActionHandler {
        void run();
    }
}
