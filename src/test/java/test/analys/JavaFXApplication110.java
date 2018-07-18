package test.analys;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author blj0011
 */
public class JavaFXApplication110 extends Application
{

    @Override
    public void start(Stage primaryStage)
    {
        VBox root = new VBox();

        StackPane stackpane = new StackPane();        

        Label label = new Label("Hello world Hello world Hello world Hello world Hello world Hello world Hello world Hello world Hello world");
        VBox.setVgrow(label, Priority.ALWAYS);
        label.wrapTextProperty().set(true);

        label.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
                    if(mouseEvent.getClickCount() == 2){
                        label.setVisible(false);
                        TextArea textarea = new TextArea(label.getText());
                        textarea.setPrefHeight(label.getHeight() + 10);
                        stackpane.getChildren().add(textarea);

                        textarea.setOnKeyPressed(event ->{
                            System.out.println(event.getCode());
                            if(event.getCode().toString().equals("ENTER"))
                            {
                                stackpane.getChildren().remove(textarea);
                                label.setVisible(true);                               
                            }
                        });
                    }
                }
            }
        });

        stackpane.getChildren().add(label);   

        root.getChildren().add(stackpane);

        Scene scene = new Scene(root, 300, 250);

        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        launch(args);
    }

}