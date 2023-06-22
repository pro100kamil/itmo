package UI.controllers;

import common.commands.CommandDescription;
import common.models.User;
import common.models.UserRole;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import utils.MyAlerts;
import utils.SceneSwitcher;

import java.io.IOException;
import java.util.List;

public class UsersController extends BaseController {

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

        changeUserRoleButton.setOnAction((event -> handleChangeRoleButton()));


        initializeColumns();


        try {
            setUsers(clientManager.getUsers());
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void handleChangeRoleButton() {
        User selectedUser = tableView.getSelectionModel().getSelectedItem();
        if (selectedUser == null) {
            MyAlerts.showWarningAlert("Выберите пользователя",
                    "Надо выбрать пользователя, у которого нужно изменить роль");
            return;
        }

        Stage stage = new Stage();

        ChangeRoleController controller = (ChangeRoleController) new SceneSwitcher().switchSceneAndGetController(stage,
                "/resources/ChangeUserRole.fxml", "Введите роль");

        controller.setValue(selectedUser.getRole());

        stage.showAndWait();

        UserRole newRole = controller.getRole();
        if (newRole == null) return;

        CommandDescription commandDescription = new CommandDescription("change_role");
        commandDescription.setArgs(new String[]{String.valueOf(selectedUser.getId()), newRole.toString()});
        try {
            clientManager.commandHandler(commandDescription, null);
            setUsers(clientManager.getUsers());
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
