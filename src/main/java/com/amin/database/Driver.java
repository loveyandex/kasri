package com.amin.database;

import com.amin.ui.dialogs.Dialog;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * is created by aMIN on 6/11/2018 at 22:14
 */
public class Driver extends Application {
    final private static Driver driver = new Driver();
    ;
    private Connection connection;

    private Driver() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/weather", "root", "");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws SQLException {
        launch(args);

    }

    public static Driver getDriver() {
        return driver;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        Connection connection = Driver.getDriver().getConnection();
        Statement statement = null;
        try {
            statement = connection.createStatement();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            statement.executeUpdate(Queries.CRT_TBL_X);
            Platform.exit();

        } catch (SQLException e) {
            Dialog.createExceptionDialog(e);
        }
//        while(rs.next())
//            System.out.println(rs.getString("HGHT")+"  "+rs.getString(7)+"  "+rs.getString(8));

    }

    public Connection getConnection() {
        return connection;
    }

    public int createCSVTable(String query) throws SQLException {
        return getConnection().createStatement().executeUpdate(query);
    }

}
