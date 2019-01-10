//package com.amin.test;
//
//import afester.javafx.svg.SvgLoader;
//import javafx.application.Application;
//import javafx.geometry.Insets;
//import javafx.scene.Group;
//import javafx.scene.Scene;
//import javafx.scene.control.Button;
//import javafx.scene.layout.HBox;
//import javafx.stage.Stage;
//
//import java.io.InputStream;
//
///**
// * is created by aMIN on 1/11/2019 at 12:44 AM
// */
//public class JD extends Application {
//    @Override
//    public void start(Stage primaryStage) throws Exception {
//        // load the svg file
//        InputStream svgFile =
//                getClass().getResourceAsStream("/drawable/Spinner-1.2s-200px.svg");
//        SvgLoader loader = new SvgLoader();
//        Group svgImage = loader.loadSvg(svgFile);
//
//
//
//        // Scale the image and wrap it in a Group to make the button
//        // properly scale to the size of the image
////        svgImage.setScaleX(0.1);
////        svgImage.setScaleY(0.1);
//        Group graphic = new Group(svgImage);
//
//        // create a button and set the graphics node
//        Button button = new Button();
//        button.setGraphic(graphic);
//
//        // add the button to the scene and show the scene
//        HBox layout = new HBox(button);
//        HBox.setMargin(button, new Insets(10));
//        Scene scene = new Scene(layout);
//        primaryStage.setScene(scene);
//        primaryStage.show();
//    }
//}
