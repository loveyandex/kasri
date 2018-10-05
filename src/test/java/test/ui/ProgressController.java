package test.ui;

import javafx.application.Platform;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * is created by aMIN on 7/30/2018 at 10:15 PM
 */
public class ProgressController implements Initializable, Runnable {
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(this);
    }


    @Override
    public void run() {

    }
}
