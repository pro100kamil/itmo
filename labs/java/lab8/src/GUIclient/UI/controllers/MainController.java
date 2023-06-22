package UI.controllers;

import client.commands.ExecuteScript;
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
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import utils.FilterCondition;
import utils.SceneSwitcher;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class MainController extends BaseController {

    private List<Worker> collection = new LinkedList<>();
    @FXML
    private ScrollPane scrollPane;

    @FXML
    private Button addButton;

    @FXML
    private Button clearButton;

    @FXML
    private Label controllersLabel;
    @FXML
    private Button createFilterButton;
    @FXML
    private Button filterBySalaryButton;

    @FXML
    private HBox filtersHBox;

    @FXML
    private Button headButton;

    @FXML
    private Button historyButton;

    @FXML
    private Button infoButton;

    @FXML
    private Menu infoMenu;

    @FXML
    private MenuItem infoMenuItem;

    @FXML
    private MenuItem languageMenuItem;

    @FXML
    private MenuItem logOutMenuItem;

    @FXML
    private Button printDescendingButton;

    @FXML
    private Button printFieldDescendingPositionButton;

    @FXML
    private Button removeByIdButton;

    @FXML
    private Button removeFilterButton;

    @FXML
    private Button removeGreaterButton;

    @FXML
    private Menu settingsMenu;

    @FXML
    private Button showButton;

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
    public Stage getCurrentStage() {
        return currentStage;
    }

    public void setCurrentStage(Stage currentStage) {
        this.currentStage = currentStage;
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

    public void setCollection(List<Worker> workers) {
        tableView.setItems(FXCollections.observableArrayList(
                workers
        ));
//        tableView.refresh();
    }


    public void initialize() {
//        TODO кнопки в зависимости от роли
        usersButton.setVisible(false);
//        System.out.println("user " + clientManager.getUser());
        if (clientManager.getUser().getRole() == UserRole.ADMIN) {
            usersButton.setVisible(true);
        }

        userMenu.setText(clientManager.getUser().getName());

        updateCollection();

        logOutMenuItem.setOnAction((event -> logout()));

        addButton.setOnAction((event -> handleAddButton()));
        clearButton.setOnAction((event -> handleClearButton()));
        removeGreaterButton.setOnAction((event -> handleRemoveGreaterButton()));

        removeByIdButton.setOnAction((event -> handleRemoveByIdButton()));
        updateButton.setOnAction((event -> handleUpdateButton()));
        executeScriptButton.setOnAction((event -> handleExecuteScriptButton()));
        
        visualizeButton.setOnAction((event -> visualize()));

        createFilterButton.setOnAction((event -> filter()));
        removeFilterButton.setOnAction((event -> updateCollection()));

        usersButton.setOnAction((event -> handleUsersButton()));

        initializeColumns();
    }

    private void updateCollection() {
        setCollection((List<Worker>) clientManager.getCollection());
    }

    private void handleUsersButton() {
        new SceneSwitcher().switchScene(currentStage, "/resources/users.fxml", "Пользователи");
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

    private void visualize() {
        new SceneSwitcher().switchScene(currentStage, "/resources/VisualizerForm.fxml", "Визуализация");
    }

    private void logout() {
        new SceneSwitcher().switchScene(currentStage, "/resources/Register.fxml", "Вход и регистрация");
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
        Worker worker = tableView.getSelectionModel().getSelectedItem();
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
        Worker oldWorker = tableView.getSelectionModel().getSelectedItem();
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
        executeScript.setConsole(stringConsole);
        executeScript.execute(new String[]{selectedFile.getName()});

        Stage stage = new Stage();
        ExecuteScriptResultController controller = (ExecuteScriptResultController) new SceneSwitcher().switchSceneAndGetController(stage, "/resources/ExecuteScriptResult.fxml", "Результат скрипта");
        controller.setText(stringConsole.getAllText());
        stage.showAndWait();
    }
}
