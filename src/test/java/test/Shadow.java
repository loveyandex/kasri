package test;

/**
 * is created by aMIN on 5/9/2018 at 22:39
 */

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.scene.CacheHint;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.effect.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Shadow extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Image image = new Image(
                "http://icons.iconarchive.com/icons/designbolts/smurfs-movie/128/smurfette-icon.png"
        );

        ImageView imageView = new ImageView(image);
        imageView.setClip(new ImageView(image));

        ColorAdjust monochrome = new ColorAdjust();
        monochrome.setSaturation(-1.0);

        Blend blush = new Blend(
                BlendMode.MULTIPLY,
                monochrome,
                new ColorInput(
                        0,
                        0,
                        imageView.getImage().getWidth(),
                        imageView.getImage().getHeight(),
                        Color.GRAY
                )
        );

        imageView.effectProperty().bind(
                Bindings
                        .when(imageView.hoverProperty())
                        .then((Effect) blush)
                        .otherwise((Effect) null)
        );

        imageView.setCache(true);
        imageView.setCacheHint(CacheHint.SPEED);

        stage.setScene(new Scene(new Group(imageView), Color.AQUA));
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch();
    }
}