package UI.controllers;

import common.commands.CommandDescription;
import common.models.User;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import localizations.CurrentLanguage;
import localizations.Languages;
import utils.SceneSwitcher;

import java.io.IOException;
import java.util.ResourceBundle;

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

    @FXML
    private Button changeLanguageButton;

    @FXML
    private ComboBox<String> languageComboBox;

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

        languageComboBox.setItems(FXCollections.observableArrayList(Languages.getStringLanguages()));
        languageComboBox.setValue(CurrentLanguage.getLanguage());

        registerButton.setOnAction(this::handleButton);
        authButton.setOnAction(this::handleButton);
        changeLanguageButton.setOnAction((event -> {
            CurrentLanguage.setLanguage(languageComboBox.getValue());
            changeLocale();
        }));


        changeLocale();
    }

    public void changeLocale() {
        ResourceBundle resourceBundle = CurrentLanguage.getCurrentResourceBundle();
        changeLanguageButton.setText(resourceBundle.getString("changeLanguage"));
        mainLabel.setText(resourceBundle.getString("authAndRegister"));
        loginLabel.setText(resourceBundle.getString("login"));
        passwordLabel.setText(resourceBundle.getString("password"));
        authButton.setText(resourceBundle.getString("auth"));
        registerButton.setText(resourceBundle.getString("register"));

        errorLabel.setText("");
    }

    private void handleButton(ActionEvent actionEvent) {
        Button button = (Button) actionEvent.getSource();
        String commandName;
        if (button == registerButton) commandName = "register";
        else commandName = "auth";

        ResourceBundle resourceBundle = CurrentLanguage.getCurrentResourceBundle();
        if (loginField.getText().equals("") || passwordField.getText().equals("")) {
//            errorLabel.setText("Логин и пароль не должны быть пустыми");
            errorLabel.setText(resourceBundle.getString("emptyCredentials"));
            return;
        }

        User user = getUser();

        CommandDescription commandDescription = new CommandDescription(commandName);
        commandDescription.setArgs(new String[]{user.getName(), user.getPassword()});

        try {
            String errorMessage = clientManager.getErrorMessage(commandDescription);
            if (!errorMessage.equals("")) {
                if (resourceBundle.containsKey(errorMessage)) {
                    errorLabel.setText(resourceBundle.getString(errorMessage));
                } else {
                    errorLabel.setText(errorMessage);
                }
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
