package UI.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import localizations.CurrentLanguage;
import utils.FilterCondition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

public class FilterController extends BaseController {
    @FXML
    private Button button;
    @FXML
    private ComboBox<String> birthdayComboBox;

    @FXML
    private TextField birthdayField;

    @FXML
    private Label birthdayLabel;

    @FXML
    private ComboBox<String> creationDateComboBox;

    @FXML
    private TextField creationDateField;

    @FXML
    private Label creationDateLabel;

    @FXML
    private ComboBox<String> creatorIdComboBox;

    @FXML
    private TextField creatorIdField;

    @FXML
    private Label creatorIdLabel;

    @FXML
    private ComboBox<String> heightComboBox;

    @FXML
    private TextField heightField;

    @FXML
    private Label heightLabel;

    @FXML
    private ComboBox<String> idComboBox;

    @FXML
    private TextField idField;

    @FXML
    private Label idLabel;

    @FXML
    private ComboBox<String> nameComboBox;

    @FXML
    private TextField nameField;

    @FXML
    private Label nameLabel;

    @FXML
    private ComboBox<String> passportIdComboBox;

    @FXML
    private TextField passportIdField;

    @FXML
    private Label passportIdLabel;

    @FXML
    private ComboBox<String> positionComboBox;

    @FXML
    private TextField positionField;

    @FXML
    private Label positionLabel;

    @FXML
    private ComboBox<String> salaryComboBox;

    @FXML
    private TextField salaryField;

    @FXML
    private Label salaryLabel;

    @FXML
    private ComboBox<String> statusComboBox;

    @FXML
    private TextField statusField;

    @FXML
    private Label statusLabel;

    @FXML
    private ComboBox<String> xComboBox;

    @FXML
    private TextField xField;

    @FXML
    private Label xLabel;

    @FXML
    private ComboBox<String> yComboBox;

    @FXML
    private TextField yField;

    @FXML
    private Label yLabel;

    private final ArrayList<ComboBox<String>> comboBoxes = new ArrayList<>();
    private ArrayList<TextField> textFields;

    public void initializeComboBoxes() {
        comboBoxes.add(birthdayComboBox);
        comboBoxes.add(creationDateComboBox);
        comboBoxes.add(creatorIdComboBox);
        comboBoxes.add(heightComboBox);
        comboBoxes.add(idComboBox);
        comboBoxes.add(nameComboBox);
        comboBoxes.add(passportIdComboBox);
        comboBoxes.add(positionComboBox);
        comboBoxes.add(salaryComboBox);
        comboBoxes.add(statusComboBox);
        comboBoxes.add(xComboBox);
        comboBoxes.add(yComboBox);

        ObservableList<String> variants = FXCollections.observableArrayList("", "<", "=", ">");
        for (ComboBox<String> comboBox : comboBoxes) {
            comboBox.setItems(variants);
            comboBox.setValue("");
        }
    }

    public void initialize() {
        TextField[] array = {birthdayField, creationDateField, creatorIdField, heightField, idField,
                nameField, passportIdField, positionField, salaryField, statusField, xField, yField};
        textFields = new ArrayList<>(Arrays.asList(array));
        initializeComboBoxes();

        button.setOnAction((event -> handleButton()));
        changeLocale();
    }

    public void changeLocale() {
        ResourceBundle resourceBundle = CurrentLanguage.getCurrentResourceBundle();
//        idLabel.setText(resourceBundle.getString("inputWorker"));

        idLabel.setText(resourceBundle.getString("id"));
        nameLabel.setText(resourceBundle.getString("name"));
        xLabel.setText(resourceBundle.getString("coordinateX"));
        yLabel.setText(resourceBundle.getString("coordinateY"));
        salaryLabel.setText(resourceBundle.getString("salary"));
        positionLabel.setText(resourceBundle.getString("position"));
        statusLabel.setText(resourceBundle.getString("status"));
        birthdayLabel.setText(resourceBundle.getString("birthday"));
        heightLabel.setText(resourceBundle.getString("height"));
        passportIdLabel.setText(resourceBundle.getString("passportId"));
        creatorIdLabel.setText(resourceBundle.getString("creatorId"));

        button.setText(resourceBundle.getString("submit"));
    }
    private void handleButton() {
        for (int i = 0; i < comboBoxes.size(); i++) {
            ComboBox<String> comboBox = comboBoxes.get(i);
            TextField textField = textFields.get(i);
            if (!comboBox.getValue().equals("")) {
                System.out.println(comboBox.getValue() + textField.getText());
            }
        }
    }

    public FilterCondition getFilterCondition() {
        return new FilterCondition(birthdayComboBox.getValue(), birthdayField.getText(),
                creationDateComboBox.getValue(), creationDateField.getText(),
                creatorIdComboBox.getValue(), creatorIdField.getText(),
                heightComboBox.getValue(), heightField.getText(),
                idComboBox.getValue(), idField.getText(),
                nameComboBox.getValue(), nameField.getText(),
                passportIdComboBox.getValue(), passportIdField.getText(),
                positionComboBox.getValue(), positionField.getText(),
                salaryComboBox.getValue(), salaryField.getText(),
                statusComboBox.getValue(), statusField.getText(),
                xComboBox.getValue(), xField.getText(),
                yComboBox.getValue(), yField.getText());
    }
}
