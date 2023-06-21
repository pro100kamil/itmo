package UI.controllers;

import client.managers.ClientManager;
import common.models.User;
import common.models.UserRole;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class UsersController extends BaseController {

    @FXML
    private ComboBox<UserRole> roleComboBox;
    @FXML
    private Label loginLabel;

    @FXML
    private Button backToTableButton;

    @FXML
    private Button changeUserRoleButton;

    @FXML
    private TableView<User> tableView;
    @FXML
    private TableColumn<User, Integer> idColumn;

    @FXML
    private TableColumn<User, String> nameColumn;

    @FXML
    private TableColumn<User, UserRole> roleColumn;

    public void initializeColumns() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));
    }

    public void setUsers(List<User> users) {
        tableView.setItems(FXCollections.observableArrayList(
                users
        ));
//        tableView.refresh();
    }


    public void initialize() {
        backToTableButton.setOnAction((event ->
                new SceneSwitcher().switchScene(currentStage,
                        "/resources/Main.fxml", "Главное окно")));
        initializeColumns();

        // TODO сделать смену роли

//        tableView.setOnMouseClicked((event -> System.out.println(event.getTarget())));

        ObservableList<UserRole> variants = FXCollections.observableArrayList(
                UserRole.USER_MIN,
                UserRole.USER_MIDDLE,
                UserRole.USER_SENIOR,
                UserRole.ADMIN);
        roleComboBox.setItems(variants);
//        comboBox.setValue("");

        try {
            setUsers(clientManager.getUsers());
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
