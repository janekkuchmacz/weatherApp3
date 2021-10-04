package pl.kuchmaczpogoda.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;

public class DialogUtils {
    public static final String ERROR_TITLE = "Apka Pogodowa by Janek Kuchmacz | Error";
    public static final String ERROR_HEADER = "Błąd";


    public static void errorDialog(String errorMessage) {
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setTitle(ERROR_TITLE);
        errorAlert.setHeaderText(ERROR_HEADER);
        TextArea textArea = new TextArea(errorMessage);
        errorAlert.getDialogPane().setContent(textArea);
        errorAlert.showAndWait();
    }
}
