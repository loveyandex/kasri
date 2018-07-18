package com.amin.ui;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.paint.Paint;

/**
 * is created by aMIN on 6/8/2018 at 08:49
 */
public class SceneJson<T> extends Scene {

    private T json;




    /*
    Constructores
     */

    public SceneJson(Parent root) {
        super(root);
    }

    public SceneJson(Parent root, double width, double height) {
        super(root, width, height);
    }

    public SceneJson(Parent root, Paint fill) {
        super(root, fill);
    }

    public SceneJson(Parent root, double width, double height, Paint fill) {
        super(root, width, height, fill);
    }

    public SceneJson(Parent root, double width, double height, boolean depthBuffer) {
        super(root, width, height, depthBuffer);
    }

    public SceneJson(Parent root, double width, double height, boolean depthBuffer, SceneAntialiasing antiAliasing) {
        super(root, width, height, depthBuffer, antiAliasing);
    }



    public T getJson() {
        return json;
    }

    public void setJson(T json) {
        this.json = json;
    }
}
