package com.amin.test;

import com.amin.jsons.Date;
import com.amin.jsons.SomeDays;
import com.amin.scripting.Functions;
import javafx.application.Application;
import javafx.stage.Stage;

import java.lang.reflect.InvocationTargetException;

/**
 * is created by aMIN on 12/18/2018 at 4:09 AM
 */
public class App  extends Application{
    public static void main(String[] args) throws Exception {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            Functions.getInstance().someDaysOneHeightOneStations(new SomeDays()
                    .setCountry("iran__islamic_rep")
                    .setFeatureUnit("m/s")
                    .setFeaureName("WIND_SPEED")
                    .setHeight("12000")
                    .setFromDate(new Date(10, 26, 1993))
                    .setToDate(new Date(10, 26, 1993))
                    .setStationNumber("40800")
                    .setHighYear(2017)
                    .setLowerYear(1992)
            );
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
