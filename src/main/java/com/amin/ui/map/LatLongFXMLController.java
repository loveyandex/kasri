package com.amin.ui.map;

import com.amin.database.database.DatabaseHandler;
import com.amin.knnann.KNN;
import com.amin.pojos.LatLon;
import com.amin.ui.dialogs.Dialog;
import com.amin.ui.scripts.ScriptAPP;
import com.google.gson.Gson;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXTextField;
import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.javascript.event.GMapMouseEvent;
import com.lynden.gmapsfx.javascript.event.UIEventType;
import com.lynden.gmapsfx.javascript.object.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static java.lang.Math.abs;

public class LatLongFXMLController implements Initializable {
    final Connection connectionDerby = DatabaseHandler.getInstance().getConnection();


    public AnchorPane anchorroot;
    public StackPane stackpane;
    public JFXDialog dialog;
    public JFXButton acceptButton;
    public Label descrip;
    public JFXTextField dayOfMonth;
    public JFXTextField monthOfYear;
    public Label headerDialoglable;


    @FXML
    private GoogleMapView googleMapView;

    private GoogleMap map;

    private DecimalFormat formatter = new DecimalFormat("###.00000");

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        googleMapView.setKey("AIzaSyAjh0Sl5vljWmzKnR5n_7xwI1L-1zAtARc");
        Platform.runLater(() -> {
            dialog.setTransitionType(JFXDialog.DialogTransition.BOTTOM
            );
            dialog.show(stackpane);
            dialog.close();

        });
        googleMapView.addMapInializedListener(() -> {
            try {
                configureMap();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        acceptButton.setOnAction(action -> dialog.close());


        anchorroot.widthProperty().addListener((observable, oldValue, newValue) -> {
            googleMapView.setPrefWidth(newValue.doubleValue());
            AnchorPane.setLeftAnchor(stackpane, anchorroot.getWidth() / 2.0 - dialog.getWidth() / 2.0);

        });
        anchorroot.heightProperty().addListener((observable, oldValue, newValue) -> googleMapView.setPrefHeight(newValue.doubleValue()));

    }

    protected void configureMap() throws SQLException {
        MapOptions mapOptions = new MapOptions();

        mapOptions.center(new LatLong(35.6892, 51.3890))
                .mapType(MapTypeIdEnum.ROADMAP)
                .zoom(3);
        map = googleMapView.createMap(mapOptions, false);

        final MarkerOptions markerOptions = new MarkerOptions();
        final Marker marker = new Marker(markerOptions);
        marker.setPosition(new LatLong(35.6892, 51.3890));
        marker.setTitle("first");
        map.addMarker(marker);

//        final Statement statement = connection.createStatement();
//        final ResultSet resultSet = statement.executeQuery("select * from stations");
//        final ArrayList<ArrayList<String>> stations = new ArrayList<>();
//
//        while (resultSet.next()) {
//            final String stnm = resultSet.getString(1);
//            if (!stnm.equals("NULL")) {
//                final MarkerOptions markerOptions2 = new MarkerOptions();
//                final Marker marker2 = new Marker(markerOptions);
//                final String latitude = resultSet.getString(4);
//                final String longitude = resultSet.getString(5);
//                final String country = resultSet.getString(2);
//
//                stations.add(new ArrayList<String>() {{
//                    add(stnm);
//                    add(country);
//                    add(latitude);
//                    add(longitude);
//                }});
//
//
//                marker2.setPosition(new LatLong(Double.parseDouble(latitude), Double.parseDouble(longitude)));
//                marker2.setTitle(stnm);
//                map.addMarker(marker2);
//
//            }
//        }


        map.addMouseEventHandler(UIEventType.click, (GMapMouseEvent event) -> {
            final com.lynden.gmapsfx.javascript.object.LatLong latLong = event.getLatLong();
            marker.setPosition(latLong);
            marker.setTitle("first");
            marker.setVisible(true);
            map.addMarker(marker);
//
//            SnackBar.showSnackwithAction(anchorroot,
//                    actionEvent -> calcFeaute(stations,latLong),latLong.toString(), 5000);
            SnackBar.showSnackwithActionKNN(anchorroot,
                    actionEvent -> {
                        try {
                            SnackBar.jfxSnackbar.unregisterSnackbarContainer(anchorroot);
                            LatLon latLong1 = new LatLon(latLong.getLatitude()
                                    , latLong.getLongitude());

                            String dayOfMonthText = dayOfMonth.getText();
                            String month = monthOfYear.getText();

                            int day = Integer.parseInt(dayOfMonthText);
                            int mon = Integer.parseInt(month);


                            final double nearst = KNN.nearst(500
                                    , latLong1, dayOfMonthText, month);
                            System.err.println(new Gson().toJson(latLong1));
                            System.out.println(nearst);
                            final String msg = String.valueOf(nearst);
                            SnackBar.showSnack(anchorroot, msg, 2333);

                            headerDialoglable.setText("KNN Data Modeling");
                            descrip.setText(msg);
                            AnchorPane.setLeftAnchor(stackpane, anchorroot.getWidth() / 2.0 - dialog.getWidth() / 2.0);
                            dialog.setTransitionType(JFXDialog.DialogTransition.BOTTOM);
                            dialog.show(stackpane);

                        } catch (java.lang.NumberFormatException e) {
                            headerDialoglable.setText("Oops!! error inputting day and month value");

                            descrip.setText(e.getLocalizedMessage());
                            AnchorPane.setLeftAnchor(stackpane, anchorroot.getWidth() / 2.0 - dialog.getWidth() / 2.0);
                            dialog.setTransitionType(JFXDialog.DialogTransition.BOTTOM);
                            dialog.show(stackpane);
                        } catch (Exception e) {
                            Dialog.createExceptionDialog(e);
                        }
                    },


                    latLong.toString(), 5000);


        });

    }


    private void calcFeaute(ArrayList<ArrayList<String>> stations, LatLong latLong) {
        ArrayList<Double> distnaces = new ArrayList<>();
        stations.forEach(strings -> {
            final double lat = Double.parseDouble(strings.get(2));
            final double logn = Double.parseDouble(strings.get(3));
            final double latitude = latLong.getLatitude();
            final double longitude = latLong.getLongitude();
            double distance = Math.hypot(abs(latitude - lat), abs(longitude - logn));
            distnaces.add(distance);

        });

        final double[] minloc = minloc(distnaces);

        final double mindist = minloc[0];
        final int mimndistloc = (int) minloc[1];
        final ArrayList<String> best = stations.get(mimndistloc);
        final String stnumbr = best.get(0);
        final String country = best.get(1);
        System.out.println(stnumbr);
        System.out.println(country);
        ScriptAPP.scripting(String.format("onday %s 10 26 WIND_SPEED m/s 5000 1973 2017 %s", stnumbr, country));


    }


    private double[] minloc(List<Double> doubles) {
        double m = +1.0D / 0.0;
        int index = 0;
        int var4 = doubles.size();
        for (int var5 = 0; var5 < var4; ++var5) {
            double v = doubles.get(var5);
            if (Math.min(v, m) == v)
                index = var5;
            m = Math.min(v, m);

        }
        return new double[]{m, index};
    }


    public static class SnackBar {
        static JFXSnackbar jfxSnackbar;

        public static void showSnack(Pane snackbarContainer, String msg) {
            JFXSnackbar jfxSnackbar = new JFXSnackbar(snackbarContainer);
            EventHandler eh = new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    jfxSnackbar.unregisterSnackbarContainer(snackbarContainer);

                }
            };
            jfxSnackbar.getStyleClass().add("jfx-snackbar-content");
            jfxSnackbar.setPrefWidth(200);
            jfxSnackbar.show(msg, "got it", 3000, eh);
        }

        public static void showSnackwithAction(Pane snackbarContainer, String msg, long timeout) {
            JFXSnackbar jfxSnackbar = new JFXSnackbar(snackbarContainer);
            EventHandler eh = new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    jfxSnackbar.unregisterSnackbarContainer(snackbarContainer);

                }
            };

            jfxSnackbar.getStyleClass().add("jfx-snackbar-content");

            jfxSnackbar.show(msg, "Get it", timeout, eh);
        }

        public static void showSnackwithAction(Pane snackbarContainer, EventHandler<ActionEvent> eventHandler, String msg, long timeout) {
            JFXSnackbar jfxSnackbar = new JFXSnackbar(snackbarContainer);

            jfxSnackbar.getStyleClass().add("jfx-snackbar-content");

            jfxSnackbar.show(msg, "Got it", timeout, eventHandler);
        }

        public static void showSnackwithActionKNN(Pane snackbarContainer, EventHandler<ActionEvent> eventHandler, String msg, long timeout) {
            jfxSnackbar = new JFXSnackbar(snackbarContainer);
//            jfxSnackbar.getStyleClass().add("jfx-snackbar-content");

            jfxSnackbar.show(msg, "KNN", timeout, eventHandler);
        }


        public static void showSnack(Pane snackbarContainer, String msg, long timeout) {
            JFXSnackbar jfxSnackbar = new JFXSnackbar(snackbarContainer);

            jfxSnackbar.getStyleClass().add("jfx-snackbar-content");
            jfxSnackbar.show(msg, timeout);

        }
    }

}
