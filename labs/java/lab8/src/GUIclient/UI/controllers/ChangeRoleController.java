package UI.controllers;

import common.models.UserRole;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import localizations.CurrentLanguage;

import java.util.ResourceBundle;

public class ChangeRoleController extends BaseController{
    private UserRole userRole;
    @FXML
    private Button button;

    @FXML
    private ComboBox<UserRole> comboBox;

    public void initialize() {
        ObservableList<UserRole> variants = FXCollections.observableArrayList(
                UserRole.USER_MIN,
                UserRole.USER_MIDDLE,
                UserRole.USER_SENIOR
        );
        comboBox.setItems(variants);

        button.setOnAction((event -> handleButton()));

        changeLocale();
    }

    public void changeLocale() {
        ResourceBundle resourceBundle = CurrentLanguage.getCurrentResourceBundle();

        button.setText(resourceBundle.getString("changeRole"));
    }

    public UserRole getRole() {
        return userRole;
    }

    public void setValue(UserRole role) {
        comboBox.setValue(role);
    }

    public void handleButton() {
        userRole = comboBox.getValue();

        currentStage.close();
    }

}
