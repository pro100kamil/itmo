package UI.controllers;

import common.models.UserRole;
import common.models.Worker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

public class ChangeRoleController extends BaseController{

    @FXML
    private Button button;

    @FXML
    private ComboBox<UserRole> comboBox;

    public void initialize() {
        ObservableList<UserRole> variants = FXCollections.observableArrayList(
                UserRole.USER_MIN,
                UserRole.USER_MIDDLE,
                UserRole.USER_SENIOR,
                UserRole.ADMIN);
        comboBox.setItems(variants);

        button.setOnAction((event -> handleButton()));

    }

    public UserRole getRole() {
        return comboBox.getValue();
    }

    public void setValue(UserRole role) {
        comboBox.setValue(role);
    }

    public void handleButton() {
        UserRole role = comboBox.getValue();

        currentStage.close();
    }

}
