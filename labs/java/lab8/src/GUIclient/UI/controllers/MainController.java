package UI.controllers;

import client.commands.ExecuteScript;
import client.commands.Help;
import common.commands.CommandDescription;
import common.consoles.StringConsole;
import common.managers.FileManager;
import common.models.Position;
import common.models.Status;
import common.models.UserRole;
import common.models.Worker;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import localizations.CurrentLanguage;
import localizations.Languages;
import utils.FilterCondition;
import utils.MyAlerts;
import utils.SceneSwitcher;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

public class MainController extends BaseController {

    private List<Worker> collection = new LinkedList<>();

    @FXML
    private Button addButton;

    @FXML
    private Button clearButton;

    @FXML
    private Label commandsLabel;
    @FXML
    private Button createFilterButton;

    @FXML
    private Button headButton;

    @FXML
    private Button historyButton;

    @FXML
    private Button infoButton;
    @FXML
    private MenuItem logOutMenuItem;

    @FXML
    private Button removeByIdButton;

    @FXML
    private Button removeFilterButton;

    @FXML
    private Button removeGreaterButton;

    @FXML
    private Button helpButton;

    @FXML
    private TableView<Worker> tableView;
    @FXML
    private TableColumn<Worker, Integer> idColumn;
    @FXML
    private TableColumn<Worker, String> nameColumn;
    @FXML
    private TableColumn<Worker, Integer> coordinateXColumn;
    @FXML
    private TableColumn<Worker, Integer> coordinateYColumn;
    @FXML
    private TableColumn<Worker, LocalDate> creationDateColumn;
    @FXML
    private TableColumn<Worker, Float> salaryColumn;
    @FXML
    private TableColumn<Worker, Position> positionColumn;
    @FXML
    private TableColumn<Worker, Status> statusColumn;

    @FXML
    private TableColumn<Worker, LocalDate> birthdayColumn;

    @FXML
    private TableColumn<Worker, Float> heightColumn;
    @FXML
    private TableColumn<Worker, String> passportIdColumn;
    @FXML
    private TableColumn<Worker, Integer> creatorIdColumn;

    @FXML
    private Button updateButton;
    @FXML
    private Button executeScriptButton;

    @FXML
    private Menu userMenu;

    @FXML
    private Button visualizeButton;
    @FXML
    private Button usersButton;

    @FXML
    private Button changeLanguageButton;

    @FXML
    private ComboBox<String> languageComboBox;

    private final HashMap<String, Button> commandsToButtons = new HashMap<>();

    public Stage getCurrentStage() {
        return currentStage;
    }

    public void setCurrentStage(Stage currentStage) {
        this.currentStage = currentStage;
    }

    public void changeLocale() {
        ResourceBundle resourceBundle = CurrentLanguage.getCurrentResourceBundle();
        idColumn.setText(resourceBundle.getString("id"));
        nameColumn.setText(resourceBundle.getString("name"));
        creationDateColumn.setText(resourceBundle.getString("creationDate"));
        salaryColumn.setText(resourceBundle.getString("salary"));
        positionColumn.setText(resourceBundle.getString("position"));
        statusColumn.setText(resourceBundle.getString("status"));
        birthdayColumn.setText(resourceBundle.getString("birthday"));
        heightColumn.setText(resourceBundle.getString("height"));
        passportIdColumn.setText(resourceBundle.getString("passportId"));
        creatorIdColumn.setText(resourceBundle.getString("creatorId"));

        createFilterButton.setText(resourceBundle.getString("createFilter"));
        removeFilterButton.setText(resourceBundle.getString("removeFilter"));

        visualizeButton.setText(resourceBundle.getString("visualize"));

        usersButton.setText(resourceBundle.getString("users"));

        helpButton.setText(resourceBundle.getString("help"));

        commandsLabel.setText(resourceBundle.getString("commands"));
        for (String commandName : commandsToButtons.keySet()) {
            try {
                commandsToButtons.get(commandName).setText(resourceBundle.getString(commandName));
            } catch (Exception e) {
                System.out.println(e);
                System.out.println(commandName);
            }
        }
    }

    public void initializeColumns() {
//        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        coordinateXColumn.setCellValueFactory(cellValue -> new SimpleIntegerProperty(cellValue.getValue().getCoordinates().getX()).asObject());
        coordinateYColumn.setCellValueFactory(cellValue -> new SimpleIntegerProperty(cellValue.getValue().getCoordinates().getY()).asObject());
        creationDateColumn.setCellValueFactory(new PropertyValueFactory<>("creationDate"));
        salaryColumn.setCellValueFactory(new PropertyValueFactory<>("salary"));
        positionColumn.setCellValueFactory(new PropertyValueFactory<>("position"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        birthdayColumn.setCellValueFactory(cellValue -> new SimpleObjectProperty<LocalDate>(cellValue.getValue().getPerson().getBirthday()));
        heightColumn.setCellValueFactory(cellValue -> new SimpleFloatProperty(cellValue.getValue().getPerson().getHeight()).asObject());
        passportIdColumn.setCellValueFactory(cellValue -> new SimpleStringProperty(cellValue.getValue().getPerson().getPassportID()));
        creatorIdColumn.setCellValueFactory(new PropertyValueFactory<>("creatorId"));
    }

    public void initializeButtons() {
        changeLanguageButton.setOnAction((event -> {
            CurrentLanguage.setLanguage(languageComboBox.getValue());
            changeLocale();
        }));

        commandsToButtons.put("history", historyButton);
        commandsToButtons.put("info", infoButton);
        commandsToButtons.put("head", headButton);
        commandsToButtons.put("add", addButton);
        commandsToButtons.put("clear", clearButton);
        commandsToButtons.put("remove_by_id", removeByIdButton);
        commandsToButtons.put("remove_greater", removeGreaterButton);
        commandsToButtons.put("update", updateButton);
        commandsToButtons.put("executeScript", executeScriptButton);

        CommandDescription[] commandDescriptions;
        try {
            commandDescriptions = clientManager.getAllCommands();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        for (String commandName : commandsToButtons.keySet()) {
            boolean flag = true;
            for (CommandDescription commandDescription : commandDescriptions) {
                if (commandName.equals(commandDescription.getName())) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                commandsToButtons.get(commandName).setVisible(false);
            }
        }

        usersButton.setVisible(false);
        if (clientManager.getUser().getRole() == UserRole.ADMIN) {
            usersButton.setVisible(true);
            executeScriptButton.setVisible(true);
        }

        userMenu.setText(clientManager.getUser().getName());

        updateCollection();

        logOutMenuItem.setOnAction((event ->
                new SceneSwitcher().switchScene(currentStage, "/resources/Register.fxml", "Вход и регистрация")));

        addButton.setOnAction((event -> handleAddButton()));
        clearButton.setOnAction((event -> handleClearButton()));
        removeGreaterButton.setOnAction((event -> handleRemoveGreaterButton()));

        removeByIdButton.setOnAction((event -> handleRemoveByIdButton()));
        updateButton.setOnAction((event -> handleUpdateButton()));
        executeScriptButton.setOnAction((event -> handleExecuteScriptButton()));

        visualizeButton.setOnAction((event ->
                new SceneSwitcher().switchScene(currentStage, "/resources/VisualizerForm.fxml", "Визуализация")));

        createFilterButton.setOnAction((event -> filter()));
        removeFilterButton.setOnAction((event -> updateCollection()));

        usersButton.setOnAction((event ->
                new SceneSwitcher().switchScene(currentStage, "/resources/users.fxml", "Пользователи")));

        headButton.setOnAction((event -> showResultSimpleCommandInInformationAlert("head")));
        infoButton.setOnAction((event -> showResultSimpleCommandInInformationAlert("info")));
        historyButton.setOnAction((event -> showResultSimpleCommandInInformationAlert("history")));

        helpButton.setOnAction((event -> {
            StringConsole stringConsole = new StringConsole();
            Help help = new Help();
            try {
                CommandDescription[] serverDescriptions = clientManager.getAllCommands();
                CommandDescription[] clientDescriptions = {
                        new CommandDescription(new ExecuteScript()),
                        new CommandDescription(new Help())
                };
                CommandDescription[] allDescriptions = new CommandDescription[serverDescriptions.length + clientDescriptions.length];
                System.arraycopy(serverDescriptions, 0, allDescriptions, 0, serverDescriptions.length);
                System.arraycopy(clientDescriptions, 0, allDescriptions, serverDescriptions.length, clientDescriptions.length);
                help.setCommands(allDescriptions);
            } catch (IOException | ClassNotFoundException e) {
                return;
            }
            help.setConsole(stringConsole);
            help.execute(new String[0]);
            MyAlerts.showInformationAlert("help", stringConsole.getAllText());
        }));
    }

    public void setCollection(List<Worker> workers) {
        tableView.setItems(FXCollections.observableArrayList(
                workers
        ));
//        tableView.refresh();
    }

    public void showResultSimpleCommandInInformationAlert(String commandName) {
        CommandDescription commandDescription = new CommandDescription(commandName);
        commandDescription.setArgs(new String[]{});

        StringConsole stringConsole = new StringConsole();
        clientManager.setConsole(stringConsole);
        try {
            clientManager.commandHandler(commandDescription, null);
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        MyAlerts.showInformationAlert(commandName, stringConsole.getAllText());
    }

    public void initialize() {
        initializeButtons();
        initializeColumns();

        languageComboBox.setItems(FXCollections.observableArrayList(Languages.getStringLanguages()));
        languageComboBox.setValue(CurrentLanguage.getLanguage());


        changeLocale();

    }

    private void updateCollection() {
        setCollection((List<Worker>) clientManager.getCollection());
    }

    private void filter() {
        Stage stage = new Stage();

        FilterController controller = (FilterController) new SceneSwitcher().switchSceneAndGetController(stage,
                "/resources/Filter.fxml", "Фильтр");

        stage.showAndWait();

        FilterCondition filterCondition = controller.getFilterCondition();

        collection = (List<Worker>) clientManager.getCollection();
        setCollection(filterCondition.filter(collection));


    }

    public Worker getWorker() {
        Stage stage = new Stage();

        GetWorkerController controller = (GetWorkerController) new SceneSwitcher().switchSceneAndGetController(stage,
                "/resources/getWorker.fxml", "Введите данные о работнике");

        stage.showAndWait();

        return controller.getWorker();
    }

    public Worker getWorker(Worker oldWorker) {
        Stage stage = new Stage();

        GetWorkerController controller = (GetWorkerController) new SceneSwitcher().switchSceneAndGetController(stage,
                "/resources/getWorker.fxml", "Введите данные о работнике");

        controller.setFields(oldWorker);

        stage.showAndWait();

        return controller.getWorker();
    }


    public void handleAddButton() {
        addButton.setDisable(true);

        Worker worker = getWorker();

        if (worker != null) {
            try {
                CommandDescription commandDescription = new CommandDescription("add");
                commandDescription.setArgs(new String[]{});
                clientManager.commandHandler(commandDescription, worker);

                collection = (List<Worker>) clientManager.getCollection();
                setCollection(collection);

            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        addButton.setDisable(false);
    }

    public void handleClearButton() {
        clearButton.setDisable(true);

        try {
            CommandDescription commandDescription = new CommandDescription("clear");
            commandDescription.setArgs(new String[]{});
            clientManager.commandHandler(commandDescription, null);

            collection = (List<Worker>) clientManager.getCollection();
            setCollection(collection);

        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


        clearButton.setDisable(false);
    }

    public void handleRemoveGreaterButton() {
        removeGreaterButton.setDisable(true);

        Worker worker = getWorker();

        if (worker != null) {
            try {
                CommandDescription commandDescription = new CommandDescription("remove_greater");
                commandDescription.setArgs(new String[]{});
                clientManager.commandHandler(commandDescription, worker);

                collection = (List<Worker>) clientManager.getCollection();
                setCollection(collection);

            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        removeGreaterButton.setDisable(false);
    }


    private void handleRemoveByIdButton() {
        ResourceBundle resourceBundle = CurrentLanguage.getCurrentResourceBundle();
        Worker worker = tableView.getSelectionModel().getSelectedItem();
        if (worker == null) {
            MyAlerts.showWarningAlert("Выберите работника", resourceBundle.getString("notSelectedWorker"));
            return;
        }
        if (worker.getCreatorId() != clientManager.getUser().getId()) {
            MyAlerts.showWarningAlert("Чужой работник", resourceBundle.getString("foreignWorker"));
            return;
        }
        CommandDescription commandDescription = new CommandDescription("remove_by_id");
        commandDescription.setArgs(new String[]{String.valueOf(worker.getId())});
        try {
            clientManager.commandHandler(commandDescription, null);
            collection = (List<Worker>) clientManager.getCollection();
            setCollection(collection);
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    private void handleUpdateButton() {
        ResourceBundle resourceBundle = CurrentLanguage.getCurrentResourceBundle();
        Worker oldWorker = tableView.getSelectionModel().getSelectedItem();
        if (oldWorker == null) {
//            MyAlerts.showWarningAlert("Выберите работника", "Надо выбрать работника, которого нужно обновить");
            MyAlerts.showWarningAlert("Выберите работника", resourceBundle.getString("notSelectedWorker"));
            return;
        }
        if (oldWorker.getCreatorId() != clientManager.getUser().getId()) {
//            MyAlerts.showWarningAlert("Чужой работник", "Нельзя изменять чужого работника");
            MyAlerts.showWarningAlert("Чужой работник", resourceBundle.getString("foreignWorker"));
            return;
        }
        CommandDescription commandDescription = new CommandDescription("update");
        commandDescription.setArgs(new String[]{String.valueOf(oldWorker.getId())});

        Worker worker = getWorker(oldWorker);
        if (worker == null) return;
        try {
            clientManager.commandHandler(commandDescription, worker);
            collection = (List<Worker>) clientManager.getCollection();
            setCollection(collection);
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    private void handleExecuteScriptButton() {
        System.out.println("укажите файл");
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Выберете файл для скрипта");
        File selectedFile = fileChooser.showOpenDialog(currentStage);
        if (selectedFile == null) return;
        String text = FileManager.getTextFromFile(selectedFile.getName());

        ExecuteScript executeScript = new ExecuteScript();
        executeScript.setClientManager(clientManager);
        StringConsole stringConsole = new StringConsole();
        clientManager.setConsole(stringConsole);
        executeScript.setConsole(stringConsole);
        executeScript.execute(new String[]{selectedFile.getName()});

        Stage stage = new Stage();
        ExecuteScriptResultController controller = (ExecuteScriptResultController) new SceneSwitcher().switchSceneAndGetController(stage, "/resources/ExecuteScriptResult.fxml", "Результат скрипта");
        controller.setText(stringConsole.getAllText());
        stage.showAndWait();
    }
}
