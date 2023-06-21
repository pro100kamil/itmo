import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class MainForm extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/resources/Main.fxml")));
//        stage.setScene(new Scene(root, 700, 400));
        stage.setScene(new Scene(root));
        stage.setTitle("Главное окно");
        stage.show();
    }
}
