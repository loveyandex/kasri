package test.xx;

import javafx.application.Application;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class SliderWithLabeledThumb extends Application {
    public void start(Stage ps) {
        Slider yearsSlider = new Slider();

        StackPane root = new StackPane(yearsSlider);
        root.setPadding(new Insets(5));
        yearsSlider.setOrientation(Orientation.VERTICAL);
        yearsSlider.setMin(49);
        yearsSlider.setMax(99);
        yearsSlider.setValue(51);
        yearsSlider.setMinorTickCount(0);
        yearsSlider.setMajorTickUnit(1);

        Scene scene = new Scene(root);

        yearsSlider.applyCss();
        yearsSlider.layout();
        Pane p = (Pane) yearsSlider.lookup(".thumb");
        Label l = new Label();
        l.textProperty().bind(yearsSlider.valueProperty().asString("%.0f").concat(" °"));

        p.getChildren().add(l);

        ps.setScene(scene);
        ps.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}