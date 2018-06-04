package test.calan;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.GroupBuilder;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.ImageViewBuilder;
import javafx.stage.Stage;
 
public class Main extends Application {
 
    public static void main(String[] args) {
        launch(args);
    }
     
    @Override
    public void start(Stage primaryStage) {
        Group root = new Group();
                 
        String imageSource = "https://www.google.com/images/branding/googlelogo/2x/googlelogo_color_272x92dp.png";

        Image x = new Image(imageSource);

        ImageView imageView = ImageViewBuilder.create()
                .image(x)
                .build();

        imageView.setFitWidth(0.15*x.getWidth());
        imageView.setFitHeight(0.15*x.getHeight());
        Group myGroup = GroupBuilder.create()
                .children(imageView)
                .build();

        root.getChildren().add(myGroup);
   
        primaryStage.setScene(new Scene(root, 500, 400));
        primaryStage.show();
    }
}
