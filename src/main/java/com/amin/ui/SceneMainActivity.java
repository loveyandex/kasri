package com.amin.ui;

import com.amin.ui.dialogs.Dialog;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

/**
 * is created by aMIN on 6/8/2018 at 08:49
 */
public class SceneMainActivity<T> extends Scene implements EventHandler<KeyEvent> {

    private T json;




    /*
    Constructores
     */

    public SceneMainActivity(Parent root) {
        super(root);
        this.setOnKeyPressed(this);
    }

    public SceneMainActivity(Parent root, double width, double height) {
        super(root, width, height);
        this.setOnKeyPressed(this);

    }

    public SceneMainActivity(Parent root, Paint fill) {
        super(root, fill);
        this.setOnKeyPressed(this);

    }

    public SceneMainActivity(Parent root, double width, double height, Paint fill) {
        super(root, width, height, fill);
        this.setOnKeyPressed(this);

    }

    public SceneMainActivity(Parent root, double width, double height, boolean depthBuffer) {
        super(root, width, height, depthBuffer);
        this.setOnKeyPressed(this);

    }

    public SceneMainActivity(Parent root, double width, double height, boolean depthBuffer, SceneAntialiasing antiAliasing) {
        super(root, width, height, depthBuffer, antiAliasing);
        this.setOnKeyPressed(this);

    }



    public T getJson() {
        return json;
    }

    public void setJson(T json) {
        this.json = json;
    }

    @Override
    public void handle(KeyEvent event) {
        if (event.getCode() == KeyCode.ESCAPE) {
            try {
                closeAlertDialog();
            } catch (IOException e) {
                Dialog.createExceptionDialog(e);
            }

        }else if (event.getCode()==KeyCode.D &&  event.isControlDown())
        {

        }
    }

    private void closeAlertDialog() throws IOException {
        Stage stage = new StageOverride();
        stage.initStyle(StageStyle.UTILITY);
        stage.setResizable(false);
        Parent root = FXMLLoader.load(getClass().getResource("/com/amin/ui/alert.fxml"));
        Scene scene = new SceneJson<>(root);
        stage.setScene(scene);
        stage.initOwner(this.getWindow());
        stage.initModality(Modality.WINDOW_MODAL);
        stage.show();
    }



}
