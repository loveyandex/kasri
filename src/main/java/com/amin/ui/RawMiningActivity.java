package com.amin.ui;

import com.amin.analysis.RawMining;
import com.amin.analysis.SecondMining;
import com.amin.analysis.oldmapping.OldMapping;
import com.amin.config.C;
import com.amin.getdata.Methods;
import com.jfoenix.controls.JFXTextArea;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 * is created by aMIN on 5/31/2018 at 06:50
 */

public class RawMiningActivity extends Application {
    private static Stage primaryStage;
    public Button okBtn;
    public Button chooseDir;
    public ProgressBar progress;
    public Button chooseDir2;
    public Button allinon;
    public JFXTextArea showtext;
    private Desktop desktop = Desktop.getDesktop();

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource(C.RAW_FMXL_PATH));

        System.out.println("amin");
        System.out.println(C.DATA_PATH + " DATA_PATH");
        System.out.println(C.SOCANDARY_DATA_PATH + " Secondary");
        System.out.println(C.THIRDY_PATH + " Third");

        Scene scene = new Scene(root, 1000, 500);

        scene.getStylesheets().add("/dark-theme.css");

        primaryStage.setTitle("");
        primaryStage.setScene(scene);

        primaryStage.show();
        System.out.println("myth " + Thread.activeCount());

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
                    OldMapping.getWindSpeedCol(file.getParent(), file.getName());
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

    public void rawMining() {
        System.err.println(C.SOCANDARY_DATA_PATH);
        final DirectoryChooser directoryChooser =
                new DirectoryChooser();
        final File kasriDate =
                directoryChooser.showDialog(primaryStage);
        if (kasriDate != null) {
            new Thread(() -> {
                progress.setVisible(true);
                File[] countries = kasriDate.listFiles();
                for (File country : countries) {
                    System.out.println(country.getAbsolutePath());
                    File[] yerarFiles = country.listFiles();
                    for (File yearFile : yerarFiles) {
                        if (yearFile.isDirectory()) {
                            File[] months = yearFile.listFiles();
                            for (File month : months) {

                                String rootpathDirToSave = C.SOCANDARY_DATA_PATH + File.separator + country.getName() + File.separator + yearFile.getName() + File.separator + month.getName();
//                         ;
                                if (month.isDirectory()) {
                                    File[] stations = month.listFiles();
                                    for (File station :
                                            stations) {
                                        if (station.isFile()) {

                                            try {
                                                new RawMining(station.getParent(), station.getName())
                                                        .readAndWriteFile(rootpathDirToSave);
                                            } catch (FileNotFoundException e) {
                                                System.err.println(station.getPath());
                                                Methods.writeFallenUrls(station.getPath(), "config/crashStationsPatternsPathFiles.conf");
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
                    System.out.println("threads " + Thread.getAllStackTraces().keySet().size());
                    System.out.println("threads2 " + Thread.activeCount());

                    Platform.runLater(() -> showtext.setText("\n" + country.getName()));

                }


            }).start();
            System.err.println(kasriDate.getAbsolutePath());
            System.out.println("main thread come over from here");
        }


    }


    public void secondMining(ActionEvent actionEvent) {
        final DirectoryChooser directoryChooser =
                new DirectoryChooser();
        final File kasriDate = new File(C.SOCANDARY_DATA_PATH);
        kasriDate.mkdir();
        if (kasriDate != null) {
            new Thread(() -> {
                progress.setVisible(true);
                File[] countries = kasriDate.listFiles();
                for (File country : countries) {
                    System.out.println(country.getAbsolutePath());
                    int a = Character.getNumericValue(country.getName().charAt(0));

                    File[] yerarFiles = country.listFiles();
                    for (File yearFile : yerarFiles) {
                        if (yearFile.isDirectory()) {
                            File[] months = yearFile.listFiles();
                            for (File month : months) {
                                if (month.isDirectory()) {
                                    System.out.println(month.getAbsolutePath());
                                    File[] stations = month.listFiles();
                                    for (File station : stations) {
                                        if (station.isDirectory() && !station.getName().contains("item2")) {
                                            String rootpathDir = C.THIRDY_PATH + File.separator + country.getName() + File.separator + yearFile.getName() + File.separator + month.getName() + File.separator + station.getName();

                                            File[] days = station.listFiles();
                                            for (File day : days) {
                                                if (day.isFile()) {
                                                    try {
                                                        new SecondMining(day.getParent(), day.getName()).createCSVInPath(rootpathDir);

                                                    } catch (IOException e) {
                                                        System.err.println(day.getPath());
                                                        Methods.writeFallenUrls(day.getPath(), "config/crashDayCSVCreatorPatternOr.conf");

                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    Platform.runLater(() -> showtext.setText("\n" + country.getName()));

                }
                progress.setVisible(false);

            }).start();
            System.err.println(kasriDate.getAbsolutePath());
        }
    }


    public void chooseForRaw(ActionEvent actionEvent) {
        rawMining();
    }

    /**
     * deprecated methodology beause search files in one floder is not efficient
     * but we can think more
     */
    public void allinone(ActionEvent actionEvent) {
        final DirectoryChooser directoryChooser =
                new DirectoryChooser();
        final File kasriDate =
                directoryChooser.showDialog(primaryStage);
        if (kasriDate != null) {
            File[] countries = kasriDate.listFiles();
            for (File country : countries) {
                File[] yerarFiles = country.listFiles();
                if (yerarFiles != null)
                    for (File yearFile : yerarFiles) {
                        if (yearFile.isDirectory()) {
                            File[] months = yearFile.listFiles();
                            if (months != null)
                                for (File month : months) {
                                    if (month.isDirectory()) {
                                        File[] stations = month.listFiles();
                                        if (stations != null)
                                            for (File station : stations) {
                                                if (station.isDirectory() && !station.getName().contains("item2")) {
                                                    String rootpathDir = C.SOCANDARY_DATA_PATH + File.separator + country.getName() + File.separator + yearFile.getName() + File.separator + month.getName() + File.separator + station.getName();
                                                    File[] days = station.listFiles();
                                                    if (days != null)
                                                        for (File day : days) {
                                                            if (day.isFile()) {
                                                                try {
                                                                    new SecondMining(day.getParent() + File.separator + day.getName(), day.getName(), true)
                                                                            .allinOneFolder(day.getPath(),
                                                                                    country.getName() + "_" + yearFile.getName() + "_" + month.getName() + "_" + station.getName() + ".csv");

                                                                } catch (IOException e) {
                                                                    System.err.println(day.getPath());
                                                                    e.printStackTrace();
                                                                    Methods.writeFallenUrls(day.getPath(), "config/crashDayCSVCreatorPatternOr222222.conf");
                                                                }
                                                            }
                                                        }
                                                }
                                            }
                                    }
                                }
                        }
                    }
            }
            progress.setProgress(100);
            System.err.println(kasriDate.getAbsolutePath());
        }

    }


}
