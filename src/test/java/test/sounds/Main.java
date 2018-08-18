package test.sounds;

import java.net.URL;

import javafx.application.Application;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

public class Main extends Application {

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) {
    final URL resource = getClass().getResource("St.mp3");
    final Media media = new Media(resource.toString());
    final MediaPlayer mediaPlayer = new MediaPlayer(media);
    mediaPlayer.play();

    primaryStage.setTitle("Audio Player 1");
    primaryStage.setWidth(200);
    primaryStage.setHeight(200);
    primaryStage.show();
  }
}
