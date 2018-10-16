package test.analys;

import com.jfoenix.controls.JFXTextField;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class ListViewExperiments extends Application  {


    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("ListView Experiment 1");
        primaryStage.initStyle(StageStyle.UNDECORATED);

        ListView listView = new ListView();

        listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        listView.getItems().add(new ImageView(new Image("/drawable/baseline_file_copy_black_18dp.png")));
        listView.getItems().add("Item 1");
        listView.getItems().add("Item 2");
        listView.getItems().add("Item 3");


        Button button = new Button("Read Selected Value");

        button.setOnAction(event -> {
            ObservableList selectedIndices = listView.getSelectionModel().getSelectedIndices();
            listView.getItems().add(System.nanoTime());


            for(Object o : selectedIndices){
                System.out.println("o = " + o + " (" + o.getClass() + ")");
            }
        });


        final JFXTextField searchbox = new JFXTextField();
//        searchbox.setLabelFloat(true);
        searchbox.paddingProperty().setValue(new Insets(22,22,22,22));
        searchbox.promptTextProperty().setValue("Search cities");

        searchbox.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.charAt(newValue.length() - 1) == ' ' &&
                    !newValue.replaceAll(" ","").equalsIgnoreCase("")) {
                listView.getItems().add(newValue);
            }
        });

        listView.setMinHeight(700);

        VBox vBox = new VBox(searchbox,listView);

        Scene scene = new Scene(vBox, 600, 720);
        primaryStage.setScene(scene);
        primaryStage.show();

        primaryStage.getScene().setOnKeyPressed(event -> {
            if (event.getCode()== KeyCode.ESCAPE)
                primaryStage.hide();
        });


    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
