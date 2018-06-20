package com.amin.ui.dialogs;

import eu.hansolo.enzo.notification.Notification;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.DirectoryChooser;
import javafx.stage.Window;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URISyntaxException;
import java.util.Optional;

import static com.amin.config.C.writePropertie;

public class Dialog {


    public static boolean createExceptionDialog(Exception ex) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Exception Occurred");
        alert.setHeaderText(ex.getLocalizedMessage());
        alert.setContentText(ex.getMessage());


// Create expandable Exception.
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        String exceptionText = sw.toString();

        Label label = new Label("The exception stacktrace was:");

        TextArea textArea = new TextArea(exceptionText);
        textArea.setEditable(false);
        textArea.setWrapText(true);

        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);

        GridPane expContent = new GridPane();
        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.add(label, 0, 0);
        expContent.add(textArea, 0, 1);

// Set expandable Exception into the dialog pane.
        alert.getDialogPane().setExpandableContent(expContent);

        alert.showAndWait();
        return true;
    }


    public static String examp() {
        TextInputDialog dialog = new TextInputDialog("walter");
        dialog.setTitle("Text Input Dialog");
        dialog.setHeaderText("Look, a Text Input Dialog");
        dialog.setContentText("Please enter your name:");

// Traditional way to get the response value.
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            System.out.println("Your name: " + result.get());
        }

// The Java 8 way to get the response value (with lambda expression).
        result.ifPresent(name -> System.out.println("Your name: " + name));
        return null;
    }




    public static void createDataDirChooser(String path) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Requires...");
        alert.setHeaderText("data directory path is not specified");
        alert.setContentText("please choose the Data Folder");
        alert.getButtonTypes().clear();
        ButtonType buttonTypeOne = new ButtonType("choose");
        alert.getButtonTypes().add(buttonTypeOne);

        alert.getButtonTypes().forEach(buttonType -> {
            System.out.println(buttonType.getText());
        });

        Optional<ButtonType> showAndWait = alert.showAndWait();
        if (showAndWait.isPresent()) {
            chDir(alert.getOwner(),path);
        } else {
            createDataDirChooser(path);
        }
    }



    public static void chDir(Window primaryStage, String data_path) {
        final DirectoryChooser directoryChooser =
                new DirectoryChooser();
        final File selectedDirectory =
                directoryChooser.showDialog(primaryStage);
        if (selectedDirectory != null) {
            try {
                writePropertie(data_path, selectedDirectory.getCanonicalPath());

                // Create a custom Notification without icon
//                Notification info = new Notification("", "God is great");
// Show the custom notification
//            Notification.Notifier.INSTANCE.notify(info);
                Notification.Notifier instance = Notification.Notifier.INSTANCE;
                instance.setPopupLifetime(Duration.INDEFINITE);
                instance.notifySuccess("success", "the data path is assigned successfully.");


            } catch (IOException e) {
                e.printStackTrace();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        } else {
            createDataDirChooser(data_path);
        }
    }




}
