package com.amin.ui.scripts;

import com.amin.ui.SceneJson;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * is created by aMIN on 8/7/2018 at 3:17 AM
 */
public class ScriptAppFxml extends Application implements Initializable {
    @Override
    public void start(Stage stage) throws Exception {
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle("all stations of a country");
        stage.setResizable(true);
        Parent root = FXMLLoader.load(getClass().getResource("/com/amin/ui/scripts/scriptapp.fxml"));
        Scene scene = new SceneJson<>(root, 750, 600);
        stage.setScene(scene);
        stage.show();
        ResizeHelper.addResizeListener(stage);

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
