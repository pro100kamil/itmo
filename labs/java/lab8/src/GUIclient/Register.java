import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javafx.geometry.Orientation;

public class Register extends Application {

    public static void main(String[] args) {
        System.out.println("Не надо запускать");
        launch(args);
    }

    public void start(Stage stage) {
        TextField loginField = new TextField();
        loginField.setPromptText("Логин: ");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Пароль: ");

        Button button = new Button("Зарегистрироваться");
        button.setOnAction(actionEvent -> {
            System.out.println(loginField.getText());
            System.out.println(passwordField.getText());
        });

        Label errorLabel = new Label("Тут будет информация об ошибках");
        errorLabel.setTextFill(Color.RED);

        FlowPane root = new FlowPane(Orientation.VERTICAL, loginField, passwordField, errorLabel, button);
        root.setAlignment(Pos.CENTER);
        root.setVgap(10);  //отступ между строчками

        stage.setTitle("Регистрация");
        stage.setScene(new Scene(root, 450, 200));
        stage.setMinHeight(200);
        stage.setMinWidth(300);

        stage.show();
    }
}