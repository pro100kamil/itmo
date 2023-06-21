import UI.controllers.GetWorkerController;
import UI.controllers.RegisterController;
import client.Configuration;
import common.consoles.Console;
import common.consoles.FileConsole;
import common.consoles.StandardConsole;
import common.models.Worker;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Main extends Application {

    public static void main(String[] args) {
        Console console = new StandardConsole();
        String[] lines = new FileConsole("client_host_port.txt").getLines();
        if (lines.length != 2) {
            console.write("Некорректный файл client_host_port.txt");
            return;
        }

        Configuration.setHost(lines[0].trim());
        Configuration.setPort(Integer.parseInt(lines[1].trim()));

        launch(args);
    }

    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/resources/Register.fxml"));
        Parent root = null;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            System.out.println("errrorrr");
            throw new RuntimeException(e);
        }
        RegisterController controller = fxmlLoader.getController();

        controller.setCurrentStage(stage);

        stage.setScene(new Scene(root));
        stage.setTitle("Вход и регистрация");
        stage.show();
    }
}