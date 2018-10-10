/*
 * Copyright 2017 Esri.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.amin.arcgis.open_mobile_map_package;

import com.esri.arcgisruntime.geometry.CoordinateFormatter;
import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.loadable.LoadStatus;
import com.esri.arcgisruntime.mapping.MobileMapPackage;
import com.esri.arcgisruntime.mapping.view.Callout;
import com.esri.arcgisruntime.mapping.view.MapView;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;

public class OpenMobileMapPackageSample extends Application {

  private MapView mapView;

  @Override
  public void start(Stage stage) {

    try {
      // create stack pane and application scene
      StackPane stackPane = new StackPane();
      Scene scene = new Scene(stackPane);

      // set title, size, and add scene to stage
      stage.setTitle("Open Mobile Map Package Sample");
      stage.setWidth(800);
      stage.setHeight(700);
      stage.setScene(scene);
      stage.show();

      // create a map view
      mapView = new MapView();

      //load a mobile map package
      final String mmpkPath = new File("./samples-data/mmpk/SanFrancisco.mmpk").getAbsolutePath();
      MobileMapPackage mobileMapPackage = new MobileMapPackage(mmpkPath);

      mobileMapPackage.loadAsync();
      mobileMapPackage.addDoneLoadingListener(() -> {
        if (mobileMapPackage.getLoadStatus() == LoadStatus.LOADED && mobileMapPackage.getMaps().size() > 0) {
          //add the map from the mobile map package to the map view
          mapView.setMap(mobileMapPackage.getMaps().get(0));
        } else {
          Alert alert = new Alert(Alert.AlertType.ERROR, "Failed to load the mobile map package");
          alert.show();
        }
      });


        // click event to display the callout with the formatted coordinates of the clicked location
        mapView.setOnMouseClicked(e -> {
            // check that the primary mouse button was clicked and user is not panning
            if (e.isStillSincePress() && e.getButton() == MouseButton.PRIMARY) {
                // get the map point where the user clicked
                Point2D point = new Point2D(e.getX(), e.getY());
                Point mapPoint = mapView.screenToLocation(point);
                // show the callout at the point with the different coordinate format strings
                showCalloutWithLocationCoordinates(mapPoint);
            }
        });






        // add the map view to stack pane
      stackPane.getChildren().add(mapView);
    } catch (Exception e) {
      // on any error, display the stack trace.
      e.printStackTrace();
    }
  }







    /**
     * Shows a callout at the specified location with different coordinate formats in the callout.
     *
     * @param location coordinate to show coordinate formats for
     */
    private void showCalloutWithLocationCoordinates(Point location) {
        Callout callout = mapView.getCallout();
        callout.setTitle("Location:");
        String latLonDecimalDegrees = CoordinateFormatter.toLatitudeLongitude(location, CoordinateFormatter
                .LatitudeLongitudeFormat.DECIMAL_DEGREES, 4);
        String latLonDegMinSec = CoordinateFormatter.toLatitudeLongitude(location, CoordinateFormatter
                .LatitudeLongitudeFormat.DEGREES_MINUTES_SECONDS, 1);
        String utm = CoordinateFormatter.toUtm(location, CoordinateFormatter.UtmConversionMode.LATITUDE_BAND_INDICATORS,
                true);
        String usng = CoordinateFormatter.toUsng(location, 4, true);
        callout.setDetail(
                "Decimal Degrees: " + latLonDecimalDegrees + "\n" +
                        "Degrees, Minutes, Seconds: " + latLonDegMinSec + "\n" +
                        "UTM: " + utm + "\n" +
                        "USNG: " + usng + "\n"
        );
        mapView.getCallout().showCalloutAt(location, new Duration(500));
    }

    /**
   * Stops and releases all resources used in application.
   */
  @Override
  public void stop() {

    if (mapView != null) {
      mapView.dispose();
    }
  }

  /**
   * Opens and runs application.
   *
   * @param args arguments passed to this application
   */
  public static void main(String[] args) {

    Application.launch(args);
  }

}
