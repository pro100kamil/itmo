package UI.controllers;

import client.managers.ClientManager;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Affine;
import javafx.stage.Stage;
import utils.SceneSwitcher;

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

        canvas.setOnMouseMoved(this::onCanvasMouseMoved);
    }

    private void onCanvasMouseMoved(MouseEvent mouseEvent) {
        System.out.println("mouse moved");
        Affine affine = canvas.getGraphicsContext2D().getTransform();
        Integer newX = (int) Math.round(mouseEvent.getX() / affine.getMxx());
        Integer newY = (int) Math.round(mouseEvent.getY() / affine.getMyy());
        canvasXLabel.setText(newX.toString());
        canvasYLabel.setText(newY.toString());
    }

    public void handleBackButton() {
        new SceneSwitcher().switchScene(currentStage, "/resources/Main.fxml", "Главное окно");
    }
}
