package com.amin.ui.backtopbtn;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.awt.*;

public class SimpleWindowApplication extends Application {
    private double xOffset = 0;
    private double yOffset = 0;
    Dimension dimension=Toolkit.getDefaultToolkit().getScreenSize();
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(final Stage primaryStage) {
        primaryStage.initStyle(StageStyle.UNDECORATED);
        BorderPane root = new BorderPane();

        Button value = new Button();
        value.setStyle("-fx-padding: 20 0 0 0");
        value.setMinWidth(800);
        root.setTop(value);

        value.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });
        value.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                primaryStage.setX(event.getScreenX() - xOffset);
                primaryStage.setY(event.getScreenY() - yOffset);
            }
        });
        value.setOnMouseClicked(event -> {
            if (event.getButton().name()==MouseButton.SECONDARY.name()) {
                final double width = dimension.getWidth();
                System.out.println(width);
                primaryStage.setWidth(width);
                primaryStage.setHeight(dimension.getHeight());
                System.out.println("king");
            }

        });

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
        ResizeHelper.addResizeListener(primaryStage);

    }
}