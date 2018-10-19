package com.amin.ui;

import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Paint;

/**
 * is created by aMIN on 6/8/2018 at 08:49
 */
public class SceneJson<T> extends Scene implements EventHandler<KeyEvent> {

    private T json;




    /*
    Constructores
     */

    public SceneJson(Parent root) {
        super(root);
        this.setOnKeyPressed(this);
    }

    public SceneJson(Parent root, double width, double height) {
        super(root, width, height);
        this.setOnKeyPressed(this);

    }

    public SceneJson(Parent root, Paint fill) {
        super(root, fill);
        this.setOnKeyPressed(this);

    }

    public SceneJson(Parent root, double width, double height, Paint fill) {
        super(root, width, height, fill);
        this.setOnKeyPressed(this);

    }

    public SceneJson(Parent root, double width, double height, boolean depthBuffer) {
        super(root, width, height, depthBuffer);
        this.setOnKeyPressed(this);

    }

    public SceneJson(Parent root, double width, double height, boolean depthBuffer, SceneAntialiasing antiAliasing) {
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
            this.getWindow().hide();
        }
    }
}
