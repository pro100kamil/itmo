package UI.controllers;

import client.managers.ClientManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class Visualizer extends BaseController {
    private Stage currentStage;
    private static ClientManager clientManager = new ClientManager();
    private static Canvas canvas = new Canvas();
    @FXML
    private Button backToTableButton;

    @FXML
    private Pane canvasPane;

    @FXML
    private Label canvasXLabel;

    @FXML
    private Label canvasYLabel;

    @FXML
    private Label coordinateXLabel;

    @FXML
    private TextField coordinateXTextField;

    @FXML
    private Label coordinateYLabel;

    @FXML
    private TextField coordinateYTextField;

    public Stage getCurrentStage() {
        return currentStage;
    }

    public void setCurrentStage(Stage currentStage) {
        this.currentStage = currentStage;
    }

    public void initialize() {

        backToTableButton.setOnAction((event -> handleBackButton()));

//        canvas.setOnMouseMoved(this::onCanvasMouseMoved);
    }

    public void handleBackButton() {
        new SceneSwitcher().switchScene(currentStage, "/resources/Main.fxml", "Главное окно");
    }
}
