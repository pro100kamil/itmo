package UI.controllers;

import client.managers.ClientManager;
import javafx.stage.Stage;

public class BaseController {
    protected Stage currentStage;
    protected static final ClientManager clientManager = new ClientManager();

    public static ClientManager getClientManager() {
        return clientManager;
    }

    public Stage getCurrentStage() {
        return currentStage;
    }

    public void setCurrentStage(Stage currentStage) {
        this.currentStage = currentStage;
    }
}
