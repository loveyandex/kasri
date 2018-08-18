package test.sounds;

import javafx.application.Application;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.File;
import java.net.URISyntaxException;

/**
 * is created by aMIN on 8/7/2018 at 4:05 AM
 */
public class Tes extends Application {
    public static void main(String[] args) throws URISyntaxException {
        launch(args);
          }

    @Override
    public void start(Stage primaryStage) throws Exception {
        String musicFile = "St.mp3";     // For example

        try {
            final String source = Tes.class.getResource(musicFile).toURI().toString();
            AudioClip s = new AudioClip(source);

            s.rateProperty().addListener((observable, oldValue, newValue) -> {
                System.out.println(newValue);
            });

            s.cycleCountProperty().addListener((observable, oldValue, newValue) -> {
                System.out.println(newValue);

            });
            s.play(0.1);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

    }
}
