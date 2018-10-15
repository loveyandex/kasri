package com.amin.ui.mappless;

import com.amin.jsons.City;
import com.amin.jsons.Country;
import com.amin.ui.dialogs.SnackBar;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.FileReader;


public class ListViewExperiments extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("ListView Experiment 1");
        primaryStage.initStyle(StageStyle.UNDECORATED);


        Gson gson = new Gson();
        JsonReader reader = new JsonReader(new FileReader(new File("../jsons/", "cities.json")));
        JsonReader reader2 = new JsonReader(new FileReader("../jsons/country-by-abbreviation.json"));
        City[] cities = gson.fromJson(reader, City[].class); // contains the whole reviews list
        Country[] countries = gson.fromJson(reader2, Country[].class); // contains the whole reviews list


        ListView listView = new ListView();
        listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);


        final JFXTextField searchbox = new JFXTextField();
//        searchbox.setLabelFloat(true);
        searchbox.paddingProperty().setValue(new Insets(22, 22, 22, 22));
        searchbox.promptTextProperty().setValue("Search cities");
        searchbox.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.DOWN) {
                listView.requestFocus();
            }
        });


        searchbox.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.charAt(newValue.length() - 1) == ' ' &&
                    !newValue.replaceAll(" ", "").equalsIgnoreCase("")) {
                listView.getItems().clear();
                for (int i = 0; i < cities.length; i++) {
                    final City city = cities[i];
                    if (city.getName().toLowerCase().contains(newValue.toLowerCase().substring(0, newValue.length() - 1))) {

                        final String s = gson.toJson(city);
                        System.out.println(s);
                        listView.getItems().add(s);
                        listView.refresh();
                    }

                }
                if (listView.getItems().size() == 0)
                    SnackBar.showSnack(((Pane) primaryStage.getScene().getRoot()), "No such city found", 2000);

            } else {
                listView.getItems().clear();
            }
        });

        listView.setMinHeight(600);

        VBox vBox = new VBox(searchbox, listView);

        Scene scene = new Scene(vBox, 550, 667);
        primaryStage.setScene(scene);
        primaryStage.show();

        primaryStage.getScene().setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE)
                primaryStage.hide();
        });
        listView.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE)
                primaryStage.hide();
            else if (event.getCode() == KeyCode.UP && listView.getSelectionModel().getSelectedIndices().get(0).equals(0)) {
                searchbox.requestFocus();
            }else if ((event.getCode()==KeyCode.ENTER && listView.getItems().size()>0)||
                    (event.getCode()==KeyCode.ENTER && listView.getItems().size()>0)){
                SnackBar.showSnack(vBox, String.valueOf(listView.getSelectionModel().getSelectedItems().get(0)));
            }
        });


        vBox.getStylesheets().add("/dark-theme.css");
        //You would need from here
        primaryStage.focusedProperty().addListener((ov, onHidden, onShown) -> {
            primaryStage.hide();
        });



    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
