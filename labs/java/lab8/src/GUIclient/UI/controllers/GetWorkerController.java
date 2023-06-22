package UI.controllers;

import common.managers.WorkerValidateManager;
import common.models.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.time.LocalDate;

public class GetWorkerController extends BaseController {
    private Worker worker;
    @FXML
    private Label birthdayLabel;

    @FXML
    private Button button;

    @FXML
    private Label coordinateXLabel;

    @FXML
    private Label coordinateYLabel;

    @FXML
    private Label errorLabel;

    @FXML
    private Label heightLabel;

    @FXML
    private Label mainLabel;
    @FXML
    private Label nameLabel;

    @FXML
    private Label passportLabel;

    @FXML
    private Label positionLabel;

    @FXML
    private Label salaryLabel;

    @FXML
    private Label statusLabel;

    @FXML
    private TextField nameField;
    @FXML
    private TextField coordinateXField;
    @FXML
    private TextField coordinateYField;
    @FXML
    private TextField salaryField;
    @FXML
    private TextField positionField;
    @FXML
    private TextField statusField;
    @FXML
    private TextField birthdayField;
    @FXML
    private TextField heightField;
    @FXML
    private TextField passportField;

    public Stage getCurrentStage() {
        return currentStage;
    }

    public void setCurrentStage(Stage currentStage) {
        this.currentStage = currentStage;
    }

    public void initialize() {
        errorLabel.setTextFill(Color.RED);
        button.setOnAction((event -> handleButton()));

    }

    public void handleButton() {
        if (!WorkerValidateManager.isName(nameField.getText())) {
            errorLabel.setText("Имя не должно быть пустым");
            return;
        }
        String name = WorkerValidateManager.getName(nameField.getText());

        if (!WorkerValidateManager.isCoordinateX(coordinateXField.getText())) {
            errorLabel.setText("Координата X должна быть целым числом");
            return;
        }
        Integer x = WorkerValidateManager.getCoordinateX(coordinateXField.getText());
        if (!WorkerValidateManager.isCoordinateY(coordinateYField.getText())) {
            errorLabel.setText("Координата Y должна быть целым числом");
            return;
        }
        Integer y = WorkerValidateManager.getCoordinateY(coordinateYField.getText());
        Coordinates coordinates = new Coordinates(x, y);

        if (!WorkerValidateManager.isSalary(salaryField.getText())) {
            errorLabel.setText("Зарплата должна быть вещественным числом");
            return;
        }
        Float salary = WorkerValidateManager.getSalary(salaryField.getText());

        if (!WorkerValidateManager.isPosition(positionField.getText())) {
            errorLabel.setText("Несуществующая позиция");
            return;
        }
        Position position = WorkerValidateManager.getPosition(positionField.getText());

        if (!WorkerValidateManager.isStatus(statusField.getText())) {
            errorLabel.setText("Несуществующий статус");
            return;
        }
        Status status = WorkerValidateManager.getStatus(statusField.getText());

        if (!WorkerValidateManager.isBirthday(birthdayField.getText())) {
            errorLabel.setText("Некорректная дата");
            return;
        }
        LocalDate birthday = WorkerValidateManager.getBirthday(birthdayField.getText());

        if (!WorkerValidateManager.isHeight(heightField.getText())) {
            errorLabel.setText("Некорректный рост");
            return;
        }
        float height = WorkerValidateManager.getHeight(heightField.getText());

        if (!WorkerValidateManager.isPassportID(passportField.getText())) {
            errorLabel.setText("Некорректная дата");
            return;
        }
        errorLabel.setText("");

        String passportID = WorkerValidateManager.getPassportID(passportField.getText());

        Person person = new Person(birthday, height, passportID);

        worker = new Worker(name, coordinates, salary, position, status, person);
        currentStage.close();
    }

    public void setFields(Worker oldWorker) {
        if (oldWorker.getName() != null)
            nameField.setText(oldWorker.getName());
        if (oldWorker.getCoordinates().getX() != null)
            coordinateXField.setText(oldWorker.getCoordinates().getX().toString());
        if (oldWorker.getCoordinates().getY() != null)
            coordinateYField.setText(oldWorker.getCoordinates().getY().toString());
        if (oldWorker.getSalary() != null)
            salaryField.setText(String.valueOf(oldWorker.getSalary()));
        if (oldWorker.getPosition() != null)
            positionField.setText(String.valueOf(oldWorker.getPosition()));
        if (oldWorker.getStatus() != null)
            statusField.setText(String.valueOf(oldWorker.getStatus()));
        if (oldWorker.getPerson().getBirthday() != null)
            birthdayField.setText(String.valueOf(oldWorker.getPerson().getBirthday()));
        heightField.setText(String.valueOf(oldWorker.getPerson().getHeight()));
        if (oldWorker.getPerson().getPassportID() != null)
            passportField.setText(oldWorker.getPerson().getPassportID());
    }

    public Worker getWorker() {
        return worker;
//        new TextField()
//        if (!WorkerValidateManager.isName(nameField.getText())) {
//            errorLabel.setText("Имя не должно быть пустым");
//            return null;
//        }
//        String name = WorkerValidateManager.getName(nameField.getText());
//
//        if (!WorkerValidateManager.isCoordinateX(coordinateXField.getText())) {
//            errorLabel.setText("Координата X должна быть целым числом");
//            return null;
//        }
//        Integer x = WorkerValidateManager.getCoordinateX(coordinateXField.getText());
//        if (!WorkerValidateManager.isCoordinateY(coordinateYField.getText())) {
//            errorLabel.setText("Координата Y должна быть целым числом");
//            return null;
//        }
//        Integer y = WorkerValidateManager.getCoordinateY(coordinateYField.getText());
//        Coordinates coordinates = new Coordinates(x, y);
//
//        if (!WorkerValidateManager.isSalary(salaryField.getText())) {
//            errorLabel.setText("Зарплата должна быть вещественным числом");
//            return null;
//        }
//        Float salary = WorkerValidateManager.getSalary(salaryField.getText());
//
//        if (!WorkerValidateManager.isPosition(positionField.getText())) {
//            errorLabel.setText("Несуществующая позиция");
//            return null;
//        }
//        Position position = WorkerValidateManager.getPosition(positionField.getText());
//
//        if (!WorkerValidateManager.isStatus(statusField.getText())) {
//            errorLabel.setText("Несуществующий статус");
//            return null;
//        }
//        Status status = WorkerValidateManager.getStatus(statusField.getText());
//
//        if (!WorkerValidateManager.isBirthday(birthdayField.getText())) {
//            errorLabel.setText("Некорректная дата");
//            return null;
//        }
//        LocalDate birthday = WorkerValidateManager.getBirthday(birthdayField.getText());
//
//        if (!WorkerValidateManager.isHeight(heightField.getText())) {
//            errorLabel.setText("Некорректный рост");
//            return null;
//        }
//        float height = WorkerValidateManager.getHeight(heightField.getText());
//
//        if (!WorkerValidateManager.isPassportID(passportField.getText())) {
//            errorLabel.setText("Некорректная дата");
//            return null;
//        }
//        errorLabel.setText("");
//
//        String passportID = WorkerValidateManager.getPassportID(passportField.getText());
//
//        Person person = new Person(birthday, height, passportID);
//
//        return new Worker(name, coordinates, salary, position, status, person);
    }
}
