package test;

/**
 * is created by aMIN on 5/26/2018 at 05:00
 */

import com.analysis.WindMining;
import eu.hansolo.enzo.notification.Notification;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Pair;
import org.controlsfx.control.NotificationPane;
import org.controlsfx.control.RangeSlider;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

//from   ww w .  ja va 2  s  .c o  m
public class TestMainActivity extends Application implements Initializable {
    @FXML
    private RangeSlider hSlider;

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();
        ;
        Group group = new Group();
        group.getChildren().add(root);
        Scene scene = new Scene(group, new Dimension().getWidth() / 2, new Dimension().getHeight() / 2, Color.rgb(75, 75, 69));

        MenuBar menuBar = new MenuBar();
        menuBar.prefWidthProperty().bind(primaryStage.widthProperty());
        root.setTop(menuBar);


        // File menu - new, save, exit
        javafx.scene.control.Menu fileMenu = new javafx.scene.control.Menu("File");
        MenuItem newMenuItem = new MenuItem("New");
        MenuItem saveMenuItem = new MenuItem("Save");
        MenuItem exitMenuItem = new MenuItem("Exit");

        ProgressIndicator pbar = new ProgressIndicator(ProgressIndicator.INDETERMINATE_PROGRESS);
        pbar.setVisible(false);
        pbar.setTranslateX(232);

        Button button1 = new Button("select All");
        Button button2 = new Button("Button Number 2");
        button1.setTranslateX(-12);

        HBox hbox = new HBox(button1, button2);
        TextArea textArea = new TextArea();

//        textArea.textProperty().addListener((observable, oldValue, newValue) -> {
//        });
        try {
            ArrayList<ArrayList<String>> windSpeedCol = WindMining.getWindSpeedCol("assets/00Z_08 _Jan _2017.csv", "00Z_08 _Jan _2017");

            for (int j = 0; j < windSpeedCol.size(); j++)
                textArea.appendText(windSpeedCol.get(j).get(0) + ";" + windSpeedCol.get(j).get(1) + "\r\n");

        } catch (IOException e) {
            e.printStackTrace();
        }

        button1.setOnAction(event -> {
            // Create a custom Notification without icon
            Notification info = new Notification("You know That", "God is great");

// Show the custom notification
            Notification.Notifier.INSTANCE.notify(info);
            Notification.Notifier.INSTANCE.notifySuccess("downloaded", "dsd");

            Notification.Notifier.INSTANCE.notifyInfo("Information", "   ");


        });


        hSlider = new RangeSlider(0, 100, 10, 90);

        hSlider.setShowTickMarks(true);
        hSlider.setShowTickLabels(true);
        hSlider.setBlockIncrement(1);

        hSlider.lowValueProperty().addListener((observable, oldValue, newValue) -> {
            textArea.appendText(String.valueOf(hSlider.getLowValue()) + "\n");

        });


        hSlider.setLayoutX(521);
        hSlider.setLayoutY(531);
        group.getChildren().add(hSlider);


        VBox vbox = new VBox(textArea);

        hbox.setLayoutX(232);
        hbox.setLayoutY(333);
        vbox.setLayoutX(50);
        vbox.setLayoutY(100);
        group.getChildren().addAll(pbar, vbox, hbox);

        saveMenuItem.setOnAction(event -> {
            Stage dialog = new Stage();
            dialog.setAlwaysOnTop(true);
            dialog.setResizable(true);
            dialog.initOwner(primaryStage);
            dialog.initModality(Modality.NONE);
            dialog.show();
            dialog.onShowingProperty().addListener((observable, oldValue, newValue) -> {
                System.exit(0);
            });
        });

        newMenuItem.setOnAction(event -> {
            pbar.setVisible(true);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText("Look, an Information Dialog");
            alert.setContentText("I have a great message for you!");
            alert.showAndWait();

        });

        button2.setOnAction(event -> {
            textArea.setText(String.valueOf(hSlider.getLowValue()));
            showNotificationPane(group);

// Create the custom dialog.
            Dialog<Pair<String, String>> dialog = new Dialog<>();
            dialog.setTitle("Login Dialog");
            dialog.setHeaderText("Look, a Custom Login Dialog");

// Set the icon (must be included in the project).
//            dialog.setGraphic(new ImageView(this.getClass().getResource("login.png").toString()));

// Set the button types.
            ButtonType loginButtonType = new ButtonType("Login", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

// Create the username and password labels and fields.
            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new javafx.geometry.Insets(20, 150, 10, 10));

            TextField username = new TextField();
            username.setPromptText("Username");
            PasswordField password = new PasswordField();
            password.setPromptText("Password");

            grid.add(new Label("Username:"), 0, 0);
            grid.add(username, 1, 0);
            grid.add(new Label("Password:"), 0, 1);
            grid.add(password, 1, 1);

// Enable/Disable login button depending on whether a username was entered.
            Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
            loginButton.setDisable(true);

// Do some validation (using the Java 8 lambda syntax).
            username.textProperty().addListener((observable, oldValue, newValue) -> {
                loginButton.setDisable(newValue.trim().isEmpty());
            });

            dialog.getDialogPane().setContent(grid);

// Request focus on the username field by default.
            Platform.runLater(() -> username.requestFocus());

// Convert the result to a username-password-pair when the login button is clicked.
            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == loginButtonType) {
                    return new Pair<>(username.getText(), password.getText());
                }
                return null;
            });

            Optional<Pair<String, String>> result1 = dialog.showAndWait();

            result1.ifPresent(usernamePassword -> {
                System.out.println("Username=" + usernamePassword.getKey() + ", Password=" + usernamePassword.getValue());
            });

        });

        exitMenuItem.setOnAction(actionEvent -> Platform.exit());

        fileMenu.getItems().addAll(newMenuItem, saveMenuItem,
                new SeparatorMenuItem(), exitMenuItem);

        javafx.scene.control.Menu webMenu = new javafx.scene.control.Menu("view");
        CheckMenuItem htmlMenuItem = new CheckMenuItem("HTML");
        htmlMenuItem.setSelected(true);
        webMenu.getItems().add(htmlMenuItem);

        CheckMenuItem cssMenuItem = new CheckMenuItem("CSS");
        cssMenuItem.setSelected(true);
        webMenu.getItems().add(cssMenuItem);

        javafx.scene.control.Menu sqlMenu = new javafx.scene.control.Menu("SQL");
        ToggleGroup tGroup = new ToggleGroup();
        RadioMenuItem mysqlItem = new RadioMenuItem("MySQL");
        mysqlItem.setToggleGroup(tGroup);

        RadioMenuItem oracleItem = new RadioMenuItem("Oracle");
        oracleItem.setToggleGroup(tGroup);
        oracleItem.setSelected(true);

        sqlMenu.getItems().addAll(mysqlItem, oracleItem,
                new SeparatorMenuItem());

        javafx.scene.control.Menu tutorialManeu = new javafx.scene.control.Menu("Tutorial");
        tutorialManeu.getItems().addAll(
                new CheckMenuItem("File"),
                new CheckMenuItem("View"),
                new CheckMenuItem("Swing"));

        sqlMenu.getItems().add(tutorialManeu);

        menuBar.getMenus().addAll(fileMenu, webMenu, sqlMenu);



        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void showNotificationPane(Group group) {
        GridPane gridPane = new GridPane();
        NotificationPane notificationPane = new NotificationPane();
        Button button = new Button("click me");
        gridPane.add(button, 1, 1);
        gridPane.add(notificationPane, 2, 1);
        group.getChildren().add(gridPane);
        notificationPane.show("god is great");
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ChangeListener<Scene> initializer = new ChangeListener<Scene>() {
            @Override
            public void changed(ObservableValue<? extends Scene> obs, Scene oldScene, Scene newScene) {
                if (newScene != null) {
                    hSlider.applyCss();
                    hSlider.getParent().layout();
                    System.out.println(hSlider.getLowValue()); // <-- No longer null
                    hSlider.sceneProperty().removeListener(this);
                }
            }
        };
        hSlider.sceneProperty().addListener(initializer);
    }


}