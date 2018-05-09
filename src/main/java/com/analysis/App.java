package com.analysis;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * is created by aMIN on 5/9/2018 at 20:08
 */
public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("weather");
        primaryStage.initStyle(StageStyle.UNDECORATED);

        primaryStage.setOnCloseRequest(event -> {
            System.out.println("king amin ");
        });


        GridPane rootNode = new GridPane();
        rootNode.setPadding(new Insets(15));
        rootNode.setHgap(5);
        rootNode.setVgap(5);
        rootNode.setAlignment(Pos.CENTER);

        Image image = new Image("File:assets\\2x\\baseline_close_white_36dp.png");
        rootNode.getChildren().add(new ImageView(image));


        Scene myScene = new Scene(rootNode, 300, 200);
        Label label = new Label("absolute root path for saving :");
        label.setAlignment(Pos.CENTER);
        rootNode.add(label, 0, 0, 2, 1);

        TextField firstValue = new TextField("G:/Program Files/AMinAbvall/kasridata");
        firstValue.setAlignment(Pos.CENTER);
        rootNode.add(firstValue, 1, 1, 2, 1);


        Label country = new Label("country:");
        country.setAlignment(Pos.CENTER);
        rootNode.add(country, 0, 2, 2, 1);

//        TextField countryvalue = new TextField( "israel;turkey;u_arab_emirates;saudi_arabia;qatar;oman;yemen;pakistan;bahrain;azerbaijan;afghanistan;armenia");
        TextField countryvalue = new TextField( "israel;turkey");
        countryvalue.setAlignment(Pos.CENTER);
        rootNode.add(countryvalue, 1, 3, 2, 1);




        Button aButton = new Button("start getting Data");
        rootNode.add(aButton, 1, 4);
        GridPane.setHalignment(aButton, HPos.CENTER);

        ProgressIndicator pbar = new ProgressIndicator(ProgressIndicator.INDETERMINATE_PROGRESS);
        pbar.setVisible(false);
        rootNode.add(pbar, 1, 5);

        primaryStage.setScene(myScene);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

}
