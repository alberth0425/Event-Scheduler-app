package ui.navigation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Annotate the name of the FXML file associated with a BaseViewController class.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface FXMLFile {
    String value();
}
