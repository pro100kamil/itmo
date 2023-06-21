package UI.controllers;

import UI.controllers.MainController;
import client.managers.ClientManager;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Задаёт stage новую scene, которая в файле с именем fileName
 */
public class SceneSwitcher {
    public BaseController getController(String fileName) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fileName));
        return fxmlLoader.getController();
    }
    public void switchScene(Stage currentStage, String fileName, String title) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fileName));
        Parent root;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            System.out.println("errrorrr");
            throw new RuntimeException(e);
        }
        BaseController controller = fxmlLoader.getController();

        controller.setCurrentStage(currentStage);

        currentStage.setScene(new Scene(root));
        currentStage.setTitle(title);
    }

    public BaseController switchSceneAndGetController(Stage currentStage, String fileName, String title) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fileName));
        Parent root;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            System.out.println("errrorrr");
            throw new RuntimeException(e);
        }
        BaseController controller = fxmlLoader.getController();

        controller.setCurrentStage(currentStage);

        currentStage.setScene(new Scene(root));
        currentStage.setTitle(title);
        return controller;
    }
}
