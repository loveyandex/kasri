package com.amin.ui;

import javafx.scene.control.ButtonBase;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URISyntaxException;

/**
 * is created by aMIN on 19/07/2018 at 07:25 AM
 */
public class StageOverride extends Stage {
    public StageOverride() {


    }

    public StageOverride(final String imgurl) {
        try {
            this.getIcons().add(new Image(getClass().getResource(imgurl).toURI().toString()));
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


    public static void shiftToEnterEvent(ButtonBase... buttonBase) {
        for (int i = 0; i < buttonBase.length; i++) {
            int finalI = i;
            buttonBase[i].setOnKeyPressed(event -> {
                if (event.getCode() == KeyCode.ENTER)
                    buttonBase[finalI].getOnAction().handle(null);
            });
        }


    }

}
