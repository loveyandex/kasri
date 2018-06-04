package com.ui;

import com.analysis.RawMining;
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

        File file1 = fileChooser.showSaveDialog(primaryStage);
        if (!file1.exists()) {
            try {
                file1.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.exit(0);
        }


        List<File> list = fileChooser.showOpenMultipleDialog(primaryStage);
        if (list != null) {
            for (File file : list) {
                try {
                    openFile(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

//
//            File file = fileChooser.showOpenDialog(primaryStage);
//            if (file != null) {
//                System.out.println(file.getAbsolutePath());
//                System.out.println(file.getName());
//                System.out.println(file.getParent());
//                try {
//                    new RawMining(file.getParent(), file.getName()).readFile();
//                    fileChooser.setInitialDirectory(file.getParentFile());
//                    fileChooser.showOpenDialog(primaryStage);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }


    }

    private void openFile(File file) throws IOException {

            Image image = new Image(file.toURI().toString());
            ImageView imageView = new ImageView(image);

            HBox hbox = new HBox(imageView);

            ((VBox) primaryStage.getScene().getRoot()).getChildren().add(hbox);

            desktop.open(file);
            desktop.print(file);

    }

    public void ChDir(ActionEvent actionEvent) {
        final DirectoryChooser directoryChooser =
                new DirectoryChooser();
        final File selectedDirectory =
                directoryChooser.showDialog(primaryStage);
        if (selectedDirectory != null) {
            File[] rawsFiles = selectedDirectory.listFiles();
            for (File dataRaw : rawsFiles) {
                if (dataRaw.isFile()) {
                    try {
                        new RawMining(dataRaw.getParent(), dataRaw.getName()).readFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } else {

                }
            }



            System.err.println(selectedDirectory.getAbsolutePath());
        }
    }


//    void ini(){
//
//        MenuItem cmItem2 = new MenuItem("Save Image");
//        cmItem2.setOnAction(new EventHandler<ActionEvent>() {
//                                public void handle(ActionEvent e) {
//                                    FileChooser fileChooser = new FileChooser();
//                                    fileChooser.setTitle("Save Image");
//                                    Object pic;
//                                    File file = fileChooser.showSaveDialog(primaryStage);
//                                    if (file != null) {
//                                        try {
//                                            ImageIO.write(SwingFXUtils.fromFXImage(pic.getImage(),
//                                                    null), "png", file);
//                                        } catch (IOException ex) {
//                                            System.out.println(ex.getMessage());
//                                        }
//                                    }
//                                }
//                            }
//        );
//
//
//    }


}
