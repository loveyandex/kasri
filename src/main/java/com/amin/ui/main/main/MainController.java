package com.amin.ui.main.main;

import com.amin.analysis.wind.WindMining;
import com.amin.data.Starter;
import com.amin.ui.SceneJson;
import com.amin.ui.dialogs.Dialog;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextArea;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.ResourceBundle;

/**
 * is created by aMIN on 5/30/2018 at 21:54
 */
public class MainController implements Initializable {
    @FXML
    public VBox rootme;

    public Label outputLbl;

    public MenuBar menuBar;

    @FXML
    private TextArea textArea;

    @FXML
    private void onactionHandeler() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/chart.fxml"));
            Stage stage = new Stage();

            stage.setTitle("Title");
            stage.setScene(new Scene(root, 450, 450));
            stage.show();
            // Hide this current window (if this is what you want)

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void exit() {
        System.exit(0);
    }

    @FXML
    private void fetch() {
        try {
            ArrayList<ArrayList<String>> windSpeedCol = WindMining.getWindSpeedCol("assets/data/00Z_08 _Jan _2017.csv", "00Z_08 _Jan _2017");
            ;
            for (int j = 0; j < windSpeedCol.size(); j++)
                textArea.appendText(windSpeedCol.get(j).get(0) + ";" + windSpeedCol.get(j).get(1) + "\r\n");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void chart(ActionEvent actionEvent) {
        charting();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (resources!=null){
            Enumeration<String> keys = resources.getKeys();

        while (keys.hasMoreElements())
            System.out.println(keys.nextElement());
    } }


    public void charting() {
        final NumberAxis xAxis = new NumberAxis(1000, 30000, 1000);
        final NumberAxis yAxis = new NumberAxis(0, 100, 10);
//        final ScatterChart<Number, Number> sc = new ScatterChart<Number, Number>(xAxis, yAxis);
        final LineChart<Number, Number> sc = new LineChart<Number, Number>(xAxis, yAxis);
        xAxis.setLabel("height");
        yAxis.setLabel("knot");
        sc.setTitle("windyear-knot-m");

        XYChart.Series series1 = new XYChart.Series();
        XYChart.Series series11 = new XYChart.Series();
        series1.setName("windspeed");
        series11.setName("d");

        try {

            ArrayList<ArrayList<String>> windSpeedCol = WindMining.getWindSpeedCol(
                    "G:\\lastdir\\afghanistan\\year_1976\\month_4\\40948",
                    "00Z_03_Apr_1976.csv");
            for (int j = 2; j < windSpeedCol.size() - 1; j++) {
                if (!windSpeedCol.get(j).get(0).equals("NULL") && !windSpeedCol.get(j).get(1).equals("NULL")) {
                    double v0 = Double.parseDouble(windSpeedCol.get(j).get(0));
                    double v00 = Double.parseDouble(windSpeedCol.get(j).get(0));
                    double v1 = Double.parseDouble(windSpeedCol.get(j).get(1));
                    double v11 = Double.parseDouble(windSpeedCol.get(j).get(1)) + 20.0;
                    series1.getData().add(new XYChart.Data(v0, v1));
                    ;
                    series11.getData().add(new XYChart.Data(v00, v11));

                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

//        sc.setPrefSize(500, 400);
        sc.getData().addAll(series1,series11);
        final VBox vbox = new VBox();
        final HBox hbox = new HBox();
        vbox.setLayoutY(300);
        vbox.setLayoutX(400);
        vbox.setStyle("-fx-background-color: #fff");

        final Button add = new Button("Add Series");



        final Button remove = new Button("Remove Series");
        remove.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if (!sc.getData().isEmpty())
                    sc.getData().remove((int) (Math.random() * (sc.getData().size() - 1)));
            }
        });

        final DropShadow shadow = new DropShadow();
        shadow.setOffsetX(2);
        shadow.setColor(Color.GREY);
        sc.setEffect(shadow);
        sc.setStyle("-fx-background-color: #fff;");
        hbox.setSpacing(10);
        hbox.getChildren().addAll(add, remove);
        vbox.getChildren().addAll(sc, hbox);
        hbox.setPadding(new Insets(10, 10, 10, 50));

        try {
            Parent root = FXMLLoader.load(getClass().getResource("/chart.fxml"));
            ((VBox) root).getChildren().add(vbox);


            Stage stage = new Stage();
            stage.setTitle("Title");
            root.setStyle("-fx-background-color: #ffb087");
            stage.setScene(new Scene(root, 450, 450));
            stage.show();

            add.setOnAction(event -> {
                stage.hide();
            });


            // Hide this current window (if this is what you want)


        } catch (IOException e) {
            e.printStackTrace();
        }



    }

    public void windyear(ActionEvent actionEvent) throws IOException, URISyntaxException {
        Stage stage = new Stage();
        stage.getIcons().add(new Image(getClass().getResource("/fav.jpg").toURI().toString()));

        stage.setResizable(true);
        Parent root = FXMLLoader.load(getClass().getResource("/com/amin/ui/main/wind/wind_year.fxml"));
        Scene scene = new Scene(root, 550, 550);
        String image = MainController.class.getResource("/loginWind.jpg").toURI().toString();
        root.setStyle("-fx-background-image: url('" + image + "'); " +
                "-fx-background-position: center center; " +
                "-fx-background-repeat: stretch;");
        root.setStyle("-fx-background-color: #e6fcff");

        stage.setScene(scene);
        stage.initOwner(rootme.getScene().getWindow());
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }



    public void windmonth(ActionEvent actionEvent) throws IOException, URISyntaxException {
        Stage stage = new Stage();
        stage.getIcons().add(new Image(getClass().getResource("/fav.jpg").toURI().toString()));

        stage.setResizable(true);
        Parent root = FXMLLoader.load(getClass().getResource("/com/amin/ui/main/wind/day.fxml"));
        Scene scene = new Scene(root, 750, 600);
//        String image = MainController.class.getResource("/logo.png").toURI().toString();
//        root.setStyle("-fx-background-image: url('" + image + "'); " +
//                "-fx-background-position: center center; " +
//                "-fx-background-repeat: stretch;");
//        root.setStyle("-fx-background-color: #e6fcff");

        stage.setScene(scene);
        stage.initOwner(rootme.getScene().getWindow());
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

























    public void getDataFromInternet(ActionEvent actionEvent) {
        Starter starter=new Starter();
        Stage stage=new Stage();
        starter.start(stage);
    }

//    public void newChart(ActionEvent actionEvent) throws IOException {
//        new NumberAxis(1000, 30000, 1000);
//        final XYChart<Number, Number> sc;
//        Charting charting = new Charting(1000, 30000, 1000,
//                0, 100, 10, "height", "height", Charting.LINE_CHART);
//        sc = charting.getSc();
//        charting.addSeriesToChart(
//                        "dd"
//                        , "dd",
//                        "G:\\lastdir\\afghanistan\\year_1976\\month_4\\40948\\00Z_03_Apr_1976.csv");
//
//        final VBox vbox = new VBox();
//        final HBox hbox = new HBox();
//        vbox.setLayoutY(300);
//        vbox.setLayoutX(400);
//        vbox.setStyle("-fx-background-color: #fff");
//        final Button add = new Button("Add Series");
//        final Button remove = new Button("Remove Series");
//
//
//        remove.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent e) {
//                if (!sc.getData().isEmpty())
//                    sc.getData().remove((int) (Math.random() * (sc.getData().size() - 1)));
//            }
//        });
//
//        hbox.setSpacing(10);
//        hbox.getChildren().addAll(add, remove);
//        vbox.getChildren().addAll(sc, hbox);
//        hbox.setPadding(new Insets(10, 10,
//                03.10, 10));
//        try {
//
//            Parent root = FXMLLoader.load(Charting.class.getResource("/chart.fxml"));
//            ((VBox) root).getChildren().add(vbox);
//            Stage stage = new Stage();
//            stage.setTitle("Title");
//            stage.setScene(new Scene(root, 450, 450));
//            stage.show();
//            add.setOnAction(event -> {
//                stage.hide();
//            });
//            // Hide this current window (if this is what you want)
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }





    public void loadAddMember(ActionEvent actionEvent) {
        try {
            windmonth(actionEvent);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }


    public void loadAddBook(ActionEvent actionEvent) {
        Dialog.SnackBar.showSnack(rootme,"comming sooon...");

    }

    public void loadMemberTable(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/amin/ui/main/wind/statistic.fxml"));
        root.setStyle("-fx-padding: 30 30 30 30 ");
        SceneJson scene = new SceneJson<>(root);
        ArrayList<ArrayList> json = new ArrayList<>();
        ArrayList<Object> kaArrayList=new ArrayList();
        for (int i = 0; i < 5; i++) {
            ArrayList e = new ArrayList();
            e.add(23.32+i);
            e.add(2016+i);
            json.add(e);
        }
        scene.setJson(json);
        Stage primaryStage = new Stage();
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void loadBookTable(ActionEvent actionEvent) {


    }

    public void loadIssuedBookList(ActionEvent actionEvent) {
    }

    public void loadSettings(ActionEvent actionEvent) {
    }


}
