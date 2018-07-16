package com.amin.ui;

import com.amin.jsons.FormInfo;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.paint.Paint;

/**
 * is created by aMIN on 6/8/2018 at 08:49
 */
public class SceneJsonWindInfo extends Scene {

    private String json;
    private FormInfo formInfo;




    /*
    Constructores
     */

    public SceneJsonWindInfo(Parent root) {
        super(root);
    }

    public SceneJsonWindInfo(Parent root, double width, double height) {
        super(root, width, height);
    }

    public SceneJsonWindInfo(Parent root, Paint fill) {
        super(root, fill);
    }

    public SceneJsonWindInfo(Parent root, double width, double height, Paint fill) {
        super(root, width, height, fill);
    }

    public SceneJsonWindInfo(Parent root, double width, double height, boolean depthBuffer) {
        super(root, width, height, depthBuffer);
    }

    public SceneJsonWindInfo(Parent root, double width, double height, boolean depthBuffer, SceneAntialiasing antiAliasing) {
        super(root, width, height, depthBuffer, antiAliasing);
    }


    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public FormInfo getFormInfo() {
        return formInfo;
    }

    public void setFormInfo(FormInfo formInfo) {
        this.formInfo = formInfo;
    }
}
