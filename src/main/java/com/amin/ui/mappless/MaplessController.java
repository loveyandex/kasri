package com.amin.ui.mappless;

import com.amin.knn.ANN;
import com.amin.knn.KNN;
import com.amin.neuralNetwork.regression.load.AminLevenberg;
import com.amin.pojos.LatLon;
import com.amin.ui.SceneJson;
import com.amin.ui.StageOverride;
import com.amin.ui.dialogs.Dialog;
import com.amin.ui.map.LatLongFXMLController;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.encog.ml.data.basic.BasicMLDataSet;
import org.encog.neural.networks.BasicNetwork;
import org.encog.util.Format;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static com.amin.knn.ANN.*;
import static com.amin.ui.StageOverride.shiftToEnterEvent;

public class MaplessController implements Initializable {

    public AnchorPane root;
    public JFXButton cancelbtn;
    public JFXTextField distanceradius;
    public Hyperlink hyperlink;
    public JFXButton KNNbtn;
    public JFXButton ANNbtn;

    @FXML
    private JFXTextField longitude;
    @FXML
    private JFXTextField latitude;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        shiftToEnterEvent(hyperlink, cancelbtn, KNNbtn);

    }


    @FXML
    private void annSolve(ActionEvent actionEvent) throws IOException {
        LatLon latLon = new LatLon(Double.parseDouble(latitude.getText()), Double.parseDouble(longitude.getText()));
        Stage stage = new StageOverride();
        stage.setResizable(true);
        Parent root = FXMLLoader.load(getClass().getResource("/com/amin/ui/mappless/ann.fxml"));
        Scene scene = new SceneJson<>(root, 650, 480);
        ((SceneJson) scene).setJson(latLon);
        stage.setTitle("ANN ...");
        stage.setScene(scene);
//                stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }


    @FXML
    private void knnSolve(ActionEvent event) throws SQLException {
        try {
            LatLon latLon = new LatLon(Double.parseDouble(latitude.getText()), Double.parseDouble(longitude.getText()));
            final double nearst = KNN.nearst(300, new LatLon(latLon.getLat(), latLon.getLogn()));
            System.out.println(nearst);
            if (nearst == -3.0E15d) {
                LatLongFXMLController.SnackBar.showSnack(root, "Data not found for this pos", 2333);

            } else {

                Stage stage = new StageOverride();
                stage.setResizable(true);
                Parent root = FXMLLoader.load(getClass().getResource("/com/amin/ui/mappless/otherfield.fxml"));
                Scene scene = new SceneJson<>(root, 650, 480);
                ((SceneJson) scene).setJson(latLon);
                stage.setTitle("KNN ...");


                stage.setScene(scene);
//                stage.initModality(Modality.APPLICATION_MODAL);
                stage.showAndWait();

            }
        } catch (NumberFormatException v) {
            Dialog.SnackBar.showSnack(root, v.getLocalizedMessage(), 3000);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @FXML
    private void handleCancelButtonAction(ActionEvent event) {
        root.getScene().getWindow().hide();


    }

    public void hypringCityname(ActionEvent actionEvent) throws Exception {

        new SearchingView((latLon) -> {
            final double nearst = KNN.nearst(300, latLon);
            System.out.println(nearst);
            LatLongFXMLController.SnackBar.showSnack(root, String.valueOf(nearst), 2333);
            longitude.setText(String.valueOf(latLon.getLogn()));
            latitude.setText(String.valueOf(latLon.getLat()));
        }).start(new Stage());

    }
    public void hypringCuntryname(ActionEvent actionEvent) throws Exception {

        new SearchingView((latLon) -> {
            final double nearst = KNN.nearst(300, latLon);
            System.out.println(nearst);
            LatLongFXMLController.SnackBar.showSnack(root, String.valueOf(nearst), 2333);
            longitude.setText(String.valueOf(latLon.getLogn()));
            latitude.setText(String.valueOf(latLon.getLat()));
        }).start(new Stage());

    }

    public void annSolve2() {
        LatLon latLon = new LatLon(Double.parseDouble(latitude.getText()), Double.parseDouble(longitude.getText()));
        try {
            ANN.IranAnn((temps, latLons) -> {
                double[] outi = new double[temps.size()];
                double[] inp1 = new double[temps.size()];
                double[] inp2 = new double[temps.size()];
                for (int i = 0; i < temps.size(); i++) {
                    outi[i] = temps.get(i);
                    inp1[i] = latLons.get(i).getLat();
                    inp2[i] = latLons.get(i).getLogn();
                }
                final Stage primaryStage = new Stage();
                final BasicMLDataSet dataset = ANN.dataset(inp1, inp2, outi);
                final BasicNetwork network = AminLevenberg.netAndTrain(dataset, train -> {
                    try {
                        new WhenTrainingView(console -> {
                            System.out.println("Beginning training...");
                            double error = 1e-6d;
                            int epoch = 1;
                            do {
                                train.iteration();
                                console.appendText("Iteration #" + Format.formatInteger(epoch)
                                        + " Error:" + Format.formatPercent(train.getError())
                                        + " Target Error: " + Format.formatPercent(error) + "\n");
                                epoch++;
                            } while ((train.getError() > error) && !train.isTrainingDone());

                            train.finishTraining();
//                                for (MLDataPair pair : dataset) {
//                                    final BasicNetwork network1 = (BasicNetwork) train.getMethod();
//                                    final MLData output = network1.compute(pair.getInput());
//                                    final String x = pair.getInput().getData(0) * MAX_LAT + "," + pair.getInput().getData(1) * MAX_LONG
//                                            + ", actual=" + output.getData(0) * MAX_FITTNESS + ",ideal=" + pair.getIdeal().getData(0) * MAX_FITTNESS;
//                                    console.appendText(x + "\n");
//                                    System.out.println(x);
//                                }

                        }).start(primaryStage);


                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                });
                primaryStage.setOnHidden(event -> {

                    double[] inps = new double[]{latLon.getLat() / MAX_LAT, latLon.getLogn() / MAX_LONG};
                    double[] ops = new double[1];

                    network.compute(inps, ops);
                    System.err.println(ops[0] * MAX_FITTNESS);
                    Dialog.SnackBar.showSnack(root, "" + ops[0] * MAX_FITTNESS, 4001);
                });




            });
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}
