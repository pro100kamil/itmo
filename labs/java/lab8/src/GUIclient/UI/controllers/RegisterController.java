package UI.controllers;

import common.commands.CommandDescription;
import common.models.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import utils.SceneSwitcher;

import java.io.IOException;

public class RegisterController extends BaseController {
    @FXML
    private Button authButton;
    @FXML
    private Button registerButton;

    @FXML
    private Label errorLabel;

    @FXML
    private Label loginLabel;

    @FXML
    private Label mainLabel;

    @FXML
    private TextField loginField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label passwordLabel;

    public Stage getCurrentStage() {
        return currentStage;
    }

    public void setCurrentStage(Stage currentStage) {
        this.currentStage = currentStage;
    }

    public User getUser() {
        String login = loginField.getText();
        String password = passwordField.getText();
        return new User(login, password);
    }

    public void initialize() {
        errorLabel.setTextFill(Color.RED);

        registerButton.setOnAction(this::handleButton);
        authButton.setOnAction(this::handleButton);

    }

    private void handleButton(ActionEvent actionEvent) {
        Button button = (Button) actionEvent.getSource();
        String commandName;
        if (button == registerButton) commandName = "register";
        else commandName = "auth";

        if (loginField.getText().equals("") || passwordField.getText().equals("")) {
            errorLabel.setText("Логин и пароль не должны быть пустыми");
            return;
        }

        User user = getUser();
        System.out.println(user);

        CommandDescription commandDescription = new CommandDescription(commandName);
        commandDescription.setArgs(new String[]{user.getName(), user.getPassword()});

        try {
            String errorMessage = clientManager.getErrorMessage(commandDescription);
            if (!errorMessage.equals("")) {
                errorLabel.setText(errorMessage);
                return;
            }
            clientManager.commandHandler(commandDescription, null);
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        errorLabel.setText("");

        new SceneSwitcher().switchScene(currentStage, "/resources/Main.fxml", "Главное окно");
    }
}
