package utils;

import javafx.scene.control.Alert;

public class MyAlerts {
    public static void showInformationAlert(String title, String text) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);

        alert.setHeaderText(null);
        alert.setContentText(text);

        alert.showAndWait();
    }

    public static void showWarningAlert(String title, String text) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);

        alert.setHeaderText(null);
        alert.setContentText(text);

        alert.showAndWait();
    }
}
