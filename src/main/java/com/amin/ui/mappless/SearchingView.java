package com.amin.ui.mappless;

import com.amin.jsons.City;
import com.amin.jsons.Country;
import com.amin.pojos.LatLon;
import com.amin.ui.dialogs.SnackBar;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.SQLException;


interface Do {
    void aDo(LatLon latLon) throws SQLException;
}

public class SearchingView extends Application {
    final ListView listView = new ListView();
    final JFXTextField searchbox = new JFXTextField();
    final Gson gson = new Gson();
    City[] cities;
    Country[] countries;
    JsonReader reader;
    ;
    JsonReader reader2;
    private Do aDo;

    {
        try {
            reader = new JsonReader(new FileReader(new File("config/jsons/", "cities.json")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    {
        try {
            reader2 = new JsonReader(new FileReader("config/jsons/country-by-abbreviation.json"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    public SearchingView(Do aDo) {
        this.aDo = aDo;
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.initStyle(StageStyle.UNDECORATED);



        cities = gson.fromJson(reader, City[].class); // contains the whole reviews list
        countries = gson.fromJson(reader2, Country[].class); // contains the whole reviews list


        listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);


//        searchbox.setLabelFloat(true);
        searchbox.paddingProperty().setValue(new Insets(18, 18, 18, 18));
        searchbox.promptTextProperty().setValue("Search cities");


        searchbox.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.DOWN) {
                listView.requestFocus();
                if (listView.getItems().size() > 0)
                    listView.getSelectionModel().selectFirst();
            } else if (event.getCode() == KeyCode.ENTER) {
                listView.getItems().clear();
                for (int i = 0; i < cities.length; i++) {
                    final City city = cities[i];
                    if (city.getName().toLowerCase().contains(searchbox.getText().toLowerCase())) {
                        final String s = gson.toJson(city);
                        listView.getItems().add(s);
                        listView.refresh();
                    }

                }
                if (listView.getItems().size() == 0)
                    SnackBar.showSnack(((Pane) primaryStage.getScene().getRoot()), "No such city found", event1 -> {
                        nlp(searchbox.getText().toLowerCase());
                    }, "did you mean?", 5500);
            }
        });


        searchbox.textProperty().addListener((observable, oldValue, newValue) -> {

//            if (newValue.charAt(newValue.length() - 1) == ' ' && !newValue.replaceAll(" ", "").equalsIgnoreCase(""))
//            if (newValue.charAt(newValue.length() - 1) == '\n') {
//                listView.getItems().clear();
//                for (int i = 0; i < cities.length; i++) {
//                    final City city = cities[i];
//                    if (city.getName().toLowerCase().contains(newValue.toLowerCase().substring(0, newValue.length() - 1))) {
//
//                        final String s = gson.toJson(city);
//                        System.out.println(s);
//                        listView.getItems().add(s);
//                        listView.refresh();
//                    }
//
//                }
//                if (listView.getItems().size() == 0)
//                    SnackBar.showSnack(((Pane) primaryStage.getScene().getRoot()), "No such city found", 1500);
//
//            } else {
            listView.getItems().clear();
//            }
        });


        MenuItem copy = new MenuItem("Copy");
        final ContextMenu contextMenu = new ContextMenu();
        contextMenu.getItems().addAll(copy);
        copy.setOnAction(event -> {
            final Clipboard clipboard = Clipboard.getSystemClipboard();
            final ClipboardContent content = new ClipboardContent();

            final ObservableList selectedItems = listView.getSelectionModel().getSelectedItems();
            String o = "";
            for (int i = 0; i < selectedItems.size(); i++) {
                o = o + (selectedItems.get(i) + "\n");
            }
            content.putString(o);
            clipboard.setContent(content);
        });


        listView.setContextMenu(contextMenu);


        listView.setMinHeight(600);

        VBox vBox = new VBox(searchbox, listView);

        Scene scene = new Scene(vBox, 550, 662);
        primaryStage.setScene(scene);
        primaryStage.show();

        primaryStage.getScene().setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE)
                primaryStage.hide();
        });

        vBox.getStylesheets().add("/dark-theme.css");
        searchbox.getStyleClass().add("searchbox");



        //You would need from here
        primaryStage.focusedProperty().addListener((ov, onHidden, onShown) -> {
            primaryStage.hide();
        });


        listView.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE)
                primaryStage.hide();
            else if (event.getCode() == KeyCode.UP && listView.getSelectionModel().getSelectedIndices().get(0).equals(0)) {
                searchbox.requestFocus();

            } else if ((event.getCode() == KeyCode.ENTER && listView.getItems().size() > 0)) {
                final String json = (String) listView.getSelectionModel().getSelectedItems().get(0);
                final City city = gson.fromJson(json, City.class);
                try {
                    primaryStage.hide();
                    aDo.aDo(new LatLon(city.getLat(), city.getLng()));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
//                SnackBar.showSnack(vBox, String.valueOf(json));


            }
        });
        listView.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY && listView.getItems().size() > 0) {
                if (event.getClickCount() == 2) {
                    final String json = (String) listView.getSelectionModel().getSelectedItems().get(0);
                    final City city = gson.fromJson(json, City.class);
                    try {
                        primaryStage.hide();
                        aDo.aDo(new LatLon(city.getLat(), city.getLng()));
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        });



    }

    private String[] nlp(String text) {
        if (text.contains("oo"))
            text = text.replaceAll("oo", "u");
        else if (text.contains("ou"))
            text = text.replaceAll("ou", "oo");
        else if (text.contains("ei"))
            text = text.replaceAll("ei", "ai");
        System.err.println(text);
        lookup(text);
        return null;
    }

    private void lookup(String probablyname) {

        for (int i = 0; i < cities.length; i++) {
            final City city = cities[i];
            if (city.getName().toLowerCase().contains(probablyname.toLowerCase())) {

                final String s = gson.toJson(city);
                listView.getItems().add(s);
                listView.refresh();
            }

        }
        if (listView.getItems().size() == 0)
            SnackBar.showSnack(((Pane) listView.getScene().getRoot()), "No such city found", event1 -> {
            }, "Sorry", 1500);


    }
}