package UI.controllers;

import client.managers.ClientManager;
import common.commands.CommandDescription;
import common.models.*;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

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

        if (clientManager.getUser().getRole() == UserRole.ADMIN) {
            usersButton.setVisible(true);
        }

        userMenu.setText(clientManager.getUser().getName());
        try {
            collection = (List<Worker>) clientManager.getCollection();
            setCollection(collection);
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
//        чекать здесь текущего юзера
        logOutMenuItem.setOnAction((event -> logout()));

        addButton.setOnAction((event -> handleAddButton()));
        clearButton.setOnAction((event -> handleClearButton()));
        removeGreaterButton.setOnAction((event -> handleRemoveGreaterButton()));

        removeByIdButton.setOnAction((event -> handleRemoveByIdButton()));
        updateButton.setOnAction((event -> handleUpdateButton()));
        executeScriptButton.setOnAction((event -> handleExecuteScriptButton()));
        
        visualizeButton.setOnAction((event -> visualize()));
        createFilterButton.setOnAction((event -> filter()));

        usersButton.setOnAction((event -> handleUsersButton()));
        usersButton.setVisible(false);

        initializeColumns();
    }

    private void handleUsersButton() {
        List<User> users;
        try {
            users = clientManager.getUsers();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        new SceneSwitcher().switchScene(currentStage, "/resources/users.fxml", "Пользователи");
//        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/resources/users.fxml"));
//        Parent root;
//        try {
//            root = fxmlLoader.load();
//        } catch (IOException e) {
//            System.out.println("errrorrr");
//            throw new RuntimeException(e);
//        }
//        UsersController controller = fxmlLoader.getController();
//
//        controller.setCurrentStage(currentStage);
//
//        currentStage.setScene(new Scene(root));
//        currentStage.setTitle("Пользователи");
//        System.out.println(users);
    }

    private void filter() {
        new SceneSwitcher().switchScene(currentStage, "/resources/users.fxml", "Фильтр");
//        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/resources/Filter.fxml"));
//        Parent root;
//        try {
//            root = fxmlLoader.load();
//        } catch (IOException e) {
//            System.out.println(e.toString());
//            System.out.println("errrorrr");
//            throw new RuntimeException(e);
//        }
////        Visualizer controller = fxmlLoader.getController();
////        controller.setClientManager(clientManager);
////
////        controller.setCurrentStage(currentStage);
//
//        currentStage.setScene(new Scene(root));
//        currentStage.setTitle("Фильтр");
    }
    private void visualize() {
        new SceneSwitcher().switchScene(currentStage, "/resources/VisualizerForm.fxml", "Визуализация");
//        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/resources/VisualizerForm.fxml"));
//        Parent root;
//        try {
//            root = fxmlLoader.load();
//        } catch (IOException e) {
//            System.out.println("errrorrr");
//            throw new RuntimeException(e);
//        }
//        Visualizer controller = fxmlLoader.getController();
//
//        controller.setCurrentStage(currentStage);
//
//        currentStage.setScene(new Scene(root));
//        currentStage.setTitle("Визуализация");
    }

    private void logout() {
        new SceneSwitcher().switchScene(currentStage, "/resources/Register.fxml", "Вход и регистрация");
//        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/resources/Register.fxml"));
//        Parent root;
//        try {
//            root = fxmlLoader.load();
//        } catch (IOException e) {
//            System.out.println("errrorrr");
//            throw new RuntimeException(e);
//        }
//
//        RegisterController controller = fxmlLoader.getController();
//        controller.setCurrentStage(currentStage);
//
//        currentStage.setScene(new Scene(root));
//        currentStage.setTitle("Вход и регистрация");
    }

    public Worker getWorker() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/resources/getWorker.fxml"));
        Parent root = null;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            System.out.println("errrorrr");
            throw new RuntimeException(e);
        }
        GetWorkerController controller = fxmlLoader.getController();

        Scene scene = new Scene(root);
        Stage stage = new Stage();

        controller.setCurrentStage(stage);

        stage.setScene(scene);
        stage.showAndWait();

        return controller.getWorker();
    }

    public Worker getWorker(Worker oldWorker) {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/resources/getWorker.fxml"));
        Parent root = null;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            System.out.println("errrorrr");
            throw new RuntimeException(e);
        }
        GetWorkerController controller = fxmlLoader.getController();

        controller.setFields(oldWorker);

        Scene scene = new Scene(root);
        Stage stage = new Stage();

        controller.setCurrentStage(stage);

        stage.setScene(scene);
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
        System.out.println(selectedFile);
    }
}
