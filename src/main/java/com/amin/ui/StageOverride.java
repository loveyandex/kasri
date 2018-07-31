package com.amin.ui;

import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URISyntaxException;

/**
 * is created by aMIN on 19/07/2018 at 07:25 AM
 */
public class StageOverride extends Stage {
    public StageOverride() {
        try {
            this.getIcons().add(new Image(getClass().getResource("/logo.png").toURI().toString()));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

    }

    public StageOverride(StageStyle style) {
        super(style);
        try {
            this.getIcons().add(new Image(getClass().getResource("/logo.png").toURI().toString()));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
