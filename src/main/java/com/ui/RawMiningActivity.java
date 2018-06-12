package com.ui;

import com.analysis.RawMining;
import com.analysis.SecondMining;
import com.analysis.wind.WindMining;
import com.config.C;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * is created by aMIN on 5/31/2018 at 06:50
 */
public class RawMiningActivity extends Application {
    public Button okBtn;
    public Button chooseDir;
    private static Stage primaryStage;
    private Desktop desktop = Desktop.getDesktop();

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource(C.RAW_FMXL_PATH));


        Scene scene = new Scene(root, 1000, 500);

        primaryStage.setTitle("");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void choosoe(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png"),
                new FileChooser.ExtensionFilter("All Files", "*.*")
        );
        fileChooser.setTitle("Open Resource File");
        fileChooser.setInitialDirectory(new File(C.PATH_TO_RAW_DIR_ROOT));


        List<File> list = fileChooser.showOpenMultipleDialog(primaryStage);
        if (list != null) {
            for (File file : list) {
                try {
                    WindMining.getWindSpeedCol(file.getParent(), file.getName());
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }


    }

    private void openFile(File file) throws IOException {

        Image image = new Image(file.toURI().toString());
        ImageView imageView = new ImageView(image);

        HBox hbox = new HBox(imageView);

        ((VBox) primaryStage.getScene().getRoot()).getChildren().add(hbox);

        desktop.open(file);
        desktop.print(file);

    }

    public void rawMining(ActionEvent actionEvent) {
        final DirectoryChooser directoryChooser =
                new DirectoryChooser();
        final File countryselectedDirectory =
                directoryChooser.showDialog(primaryStage);
        if (countryselectedDirectory != null) {
            File[] yerarFiles = countryselectedDirectory.listFiles();
            for (File yearFile : yerarFiles) {
                if (yearFile.isDirectory()) {
                    File[] months = yearFile.listFiles();
                    for (File month :
                            months) {
//                            String rootpathDirToSave = C.DATA_PATH + File.separator + month.getParentFile().getParentFile().getParentFile().getName()+ File.separator + yearFile.getParentFile().getParentFile().getName() + File.separator + yearFile.getParentFile().getName() ;
                        String rootpathDirToSave = C.DATA_PATH + File.separator + countryselectedDirectory.getName() + File.separator + yearFile.getName() + File.separator + month.getName();
                        System.out.println(rootpathDirToSave);
                        if (month.isDirectory()) {
                            File[] stations = month.listFiles();
                            for (File station :
                                    stations) {
                                if (station.isFile()) {

                                    try {
                                        new RawMining(station.getParent(), station.getName())
                                                .readAndWriteFile(rootpathDirToSave);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                    }


                } else {

                }
            }

            System.err.println(countryselectedDirectory.getAbsolutePath());
        }
    }


    public void secondMining(ActionEvent actionEvent) {

        final DirectoryChooser directoryChooser =
                new DirectoryChooser();
        final File selectedDirectory =
                directoryChooser.showDialog(primaryStage);
        if (selectedDirectory != null) {
            File[] rawsFiles = selectedDirectory.listFiles();
            for (File dataRaw : rawsFiles) {
                if (dataRaw.isFile()) {
                    try {
                        new SecondMining(dataRaw.getParent(), dataRaw.getName()).createCSV();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {

                }
            }

            System.err.println(selectedDirectory.getAbsolutePath());
        }

    }
}
