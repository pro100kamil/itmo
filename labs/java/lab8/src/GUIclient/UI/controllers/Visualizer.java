package UI.controllers;

import client.managers.ClientManager;
import common.commands.CommandDescription;
import common.models.Coordinates;
import common.models.Worker;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Affine;
import javafx.stage.Stage;
import javafx.util.Duration;
import localizations.CurrentLanguage;
import utils.MyAlerts;
import utils.SceneSwitcher;
import utils.WorkerSprite;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Visualizer extends BaseController {
    private Worker selectedworker;
    private static final int CANVAS_DEFAULT_WIDTH = 580;

    private static final int CANVAS_DEFAULT_HEIGHT = 580;
    private final double FRAMES_PER_SECOND = 31;

    private final int SECOND = 1000;
    private Stage currentStage;
    private static ClientManager clientManager = new ClientManager();
    private static Canvas canvas = new Canvas();
    private static final Affine defaultTransform = canvas.getGraphicsContext2D().getTransform();
    private static List<WorkerSprite> workerSprites = new ArrayList<>();
    private static Timeline frameTimer;
    @FXML
    private Button backToTableButton;
    
    @FXML
    private Button channgeCoordinatesButton;

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

    static {
        configureCanvas();
    }

    public Stage getCurrentStage() {
        return currentStage;
    }

    public void setCurrentStage(Stage currentStage) {
        this.currentStage = currentStage;
    }

    public void initialize() {
        canvasPane.getChildren().add(canvas);
        canvas.setOnMouseMoved(this::onCanvasMouseMoved);
        canvas.setOnMouseClicked(this::onCanvasMouseClicked);

        channgeCoordinatesButton.setOnAction(this::onOkButtonPressed);
        backToTableButton.setOnAction((event -> handleBackButton()));

        for (Worker worker : clientManager.getCollection()) {
            int x = worker.getCoordinates().getX();
            int y = worker.getCoordinates().getY();
            WorkerSprite sprite = new WorkerSprite(worker, x, y, canvas);
            workerSprites.add(sprite);
        }
        frameTimer = new Timeline(new KeyFrame(Duration.millis(0)), new KeyFrame(
                Duration.millis(SECOND / FRAMES_PER_SECOND),
                this::drawModels
        ));
        frameTimer.setCycleCount(Timeline.INDEFINITE);
        frameTimer.play();
        changeLocale();
    }

    public void changeLocale() {
        ResourceBundle resourceBundle = CurrentLanguage.getCurrentResourceBundle();
        backToTableButton.setText(resourceBundle.getString("backToTable"));
        coordinateXLabel.setText(resourceBundle.getString("coordinateX"));
        coordinateYLabel.setText(resourceBundle.getString("coordinateY"));
        channgeCoordinatesButton.setText(resourceBundle.getString("changeCoordinates"));
    }


    private void onCanvasMouseMoved(MouseEvent mouseEvent) {
        Affine affine = canvas.getGraphicsContext2D().getTransform();
        Integer newX = (int) Math.round(mouseEvent.getX() / affine.getMxx());
        Integer newY = (int) Math.round(mouseEvent.getY() / affine.getMyy());
        canvasXLabel.setText(newX.toString());
        canvasYLabel.setText(newY.toString());
    }

    private static void configureCanvas() {
        canvas.maxHeight(Double.POSITIVE_INFINITY);
        canvas.maxWidth(Double.POSITIVE_INFINITY);
        canvas.setHeight(CANVAS_DEFAULT_HEIGHT);
        canvas.setWidth(CANVAS_DEFAULT_WIDTH);
        canvas.setScaleY(1);
        canvas.setScaleX(1);
//        canvas.getGraphicsContext2D().setLineWidth(DEFAULT_STROKE_WIDTH);
    }

    private void drawModels(ActionEvent event) {
        canvas.getGraphicsContext2D().setTransform(defaultTransform);
        canvas.getGraphicsContext2D().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
//        initPolygons();
//        musicBandPolygons.sort((o1, o2) -> Float.compare(o1.getRadius(), o2.getRadius()));
        for (WorkerSprite sprite : workerSprites) {
            drawModel(sprite);
        }
//        frameTimer.playFromStart();
    }
    private void drawModel(WorkerSprite sprite) {
//        canvas.getGraphicsContext2D().setFill(Color.TRANSPARENT);
        sprite.draw();
        sprite.refresh();

//        var username = sprite.getworker().getOwner().getUsername();
//        canvas.getGraphicsContext2D().setStroke(username.equals(client.credentials().getUsername()) ? Color.GREEN : Color.RED);
        canvas.getGraphicsContext2D().strokeRect(sprite.getX(), sprite.getY(), WorkerSprite.getWidth(),
                WorkerSprite.getHeight());

    }
    protected void onCanvasMouseClicked(MouseEvent event) {
        Affine affine = canvas.getGraphicsContext2D().getTransform();
        double newX = event.getX() / affine.getMxx();
        double newY = event.getY() / affine.getMyy();
        WorkerSprite workerSprite = null;
        for (WorkerSprite sprite : workerSprites) {
            if (sprite.contains(newX, newY)) {
                if (sprite.getWorker().getCreatorId() != clientManager.getUser().getId()) {
                    MyAlerts.showWarningAlert("Чужой работник",
                            CurrentLanguage.getCurrentResourceBundle().getString("foreignWorker"));

//                    MyAlerts.showWarningAlert("Чужой работник", "Нельзя изменять чужого работника");
                    return;
                }
                workerSprite = sprite;
            }
        }
        if (workerSprite == null) {
            resetSelection();
            return;
        }
        selectPolygon(workerSprite);
    }
    @FXML
    protected void onOkButtonPressed(ActionEvent actionEvent) {
        channgeCoordinatesButton.setDisable(true);
        System.out.println(selectedworker);
        if (selectedworker == null) {
            return;
        }
        Coordinates coordinates = new Coordinates(Integer.parseInt(coordinateXTextField.getText()), Integer.parseInt(coordinateYTextField.getText()));
        selectedworker.setCoordinates(coordinates);
        CommandDescription commandDescription = new CommandDescription("update");
        commandDescription.setArgs(new String[]{String.valueOf(selectedworker.getId())});
        new Thread(() -> {
            updateSprite(selectedworker);
        }).start();
        try {
            clientManager.commandHandler(commandDescription, selectedworker);

        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        channgeCoordinatesButton.setDisable(false);
    }

    private void resetSelection() {
        coordinateXTextField.setText("");
        coordinateYTextField.setText("");
//        heightSlider.setValue(HEIGHT_SLIDER_DEFAULT_VALUE);
        selectedworker = null;
    }

    private void selectPolygon(WorkerSprite sprite) {
        coordinateXTextField.setText(String.valueOf(sprite.getWorker().getCoordinates().getX()));
        coordinateYTextField.setText(String.valueOf(sprite.getWorker().getCoordinates().getY()));
//        Float height = sprite.getworker().getFrontMan().getHeight();
//        heightSlider.setValue(height == null ? 100 : height);
        selectedworker = sprite.getWorker();
    }

    public static void removeSprite(long id) {
        if (workerSprites == null) {
            return;
        }
        workerSprites.removeIf(sprite -> sprite.getWorker().getId() == id);
    }

    public static void addSprite(Worker worker) {
        if (workerSprites == null) {
            return;
        }
        var x = (double) worker.getCoordinates().getX();
        var y = (double) worker.getCoordinates().getY();
        var sprite = new WorkerSprite(worker, x, y, canvas);
        workerSprites.add(sprite);
    }

    public static void updateSprite(Worker worker) {
        if (workerSprites == null) {
            return;
        }
        var x = (double) worker.getCoordinates().getX();
        var y = (double) worker.getCoordinates().getY();
        for (WorkerSprite sprite : workerSprites) {
            if (sprite.getWorker().getId() == worker.getId()) {
                sprite.setTarget(x, y);
            }
        }
    }

    public static List<WorkerSprite> getWorkerSprites() {
        return workerSprites;
    }
    public void handleBackButton() {
        new SceneSwitcher().switchScene(currentStage, "/resources/Main.fxml", "Главное окно");
    }
}
