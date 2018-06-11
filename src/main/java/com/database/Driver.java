package com.database;

import com.ui.dialogs.Dialog;
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
    private static Driver driver;
    private  Connection connection;
    public Driver() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
             connection=DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/weather","root","");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
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

    public static void main(String[] args) throws SQLException {
        launch(args);

    }

    public  Connection getConnection() {
        return connection;
    }

    public static Driver getDriver() {
        return new Driver();
    }
}
