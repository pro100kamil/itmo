package UI.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class ExecuteScriptResultController extends BaseController {
    @FXML
    private TextArea textArea;

    public void initialize() {

    }

    public void setText(String text) {
        textArea.setText(text);
    }
}
