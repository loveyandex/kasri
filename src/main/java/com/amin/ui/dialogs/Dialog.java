package com.amin.ui.dialogs;

import com.amin.config.C;
import com.jfoenix.controls.JFXSnackbar;
import eu.hansolo.enzo.notification.Notification;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.stage.DirectoryChooser;
import javafx.stage.Window;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Optional;

import static com.amin.config.C.*;

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

    public static boolean createIOExceptionDialog(ArrayList<IOException> exceptions) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Exception Occurred");
        alert.setHeaderText(exceptions.get(0).getLocalizedMessage());
        alert.setContentText(exceptions.get(0).getMessage() + " more other like this exception");


// Create expandable Exception.
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        String exceptionText = "";
        exceptions.get(0).printStackTrace(pw);
        exceptionText += sw.toString();
        ;

        Label label = new Label("The all exceptions stacktrace was:");

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

    public static void createDataDirChooser(String path) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Requires...");
        alert.setHeaderText(path.replaceAll("_", " ") + "  is not specified");
        alert.setContentText("please choose the Data Folder");
        alert.getButtonTypes().clear();
        ButtonType buttonTypeOne = new ButtonType("choose");
        alert.getButtonTypes().add(buttonTypeOne);


        Optional<ButtonType> showAndWait = alert.showAndWait();
        if (showAndWait.isPresent()) {
            chDir(alert.getOwner(), path);
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
                String path = selectedDirectory.getCanonicalPath();
                writePropertie(data_path, path);
                if (data_path.equals(SOCANDARY_DATA_PATH_NAME))
                    C.SOCANDARY_DATA_PATH = path;
                else if (data_path.equals(C.DATA_PATH_NAME))
                    C.DATA_PATH = path;
                else if (data_path.equals(THIRDY_PATH_NAME))
                    C.THIRDY_PATH = path;

                Notification.Notifier instance = Notification.Notifier.INSTANCE;
                instance.setPopupLifetime(Duration.INDEFINITE);
                instance.setPopupLifetime(Duration.seconds(2));
                instance.notifySuccess("success", "the path is assigned successfully.");

            } catch (IOException e) {
                e.printStackTrace();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        } else {
            createDataDirChooser(data_path);
        }
    }

    public static class SnackBar {
        public static void showSnack(Pane snackbarContainer, String msg) {
            JFXSnackbar jfxSnackbar = new JFXSnackbar(snackbarContainer);
            EventHandler eh = new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    jfxSnackbar.unregisterSnackbarContainer(snackbarContainer);

                }
            };
            jfxSnackbar.setPrefWidth(200);
            jfxSnackbar.show(msg, "got it", 3000, eh);
        }


        public static void showSnack(Pane snackbarContainer, String msg, long timeout) {
            JFXSnackbar jfxSnackbar = new JFXSnackbar(snackbarContainer);

            jfxSnackbar.getStyleClass().add("jfx-snackbar-content");
            jfxSnackbar.show(msg, timeout);

        }
    }
}
