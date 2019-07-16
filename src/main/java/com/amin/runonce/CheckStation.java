package com.amin.runonce;

import com.amin.database.Driver;
import com.amin.database.database.DatabaseHandler;
import com.amin.io.MyReader;
import com.google.gson.Gson;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;


/**
 * is created by aMIN on 11/25/2018 at 5:35 AM
 */
public class CheckStation {
    public static void main(String[] args) throws SQLException {
        String var = "config/regins";
        File rgs = new File(var);

        Connection connection = DatabaseHandler.getInstance().getConnection();

        ResultSet resultSet = connection.createStatement().executeQuery("select * from STATIONS");
        ArrayList<String> stns = new ArrayList<>();
        ArrayList<String> otherStns = new ArrayList<>();
        ArrayList<String> newStns = new ArrayList<>();
        while (resultSet.next()) {
            String stn = resultSet.getString(1);
            stns.add(stn);
        }


        for (File rffile : rgs.listFiles()) {
            if (rffile.getName().contains("withname"))
                continue;

            MyReader myReader = new MyReader(rffile.getAbsolutePath());
            Scanner scanner = myReader.getScanner();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                otherStns.add(line);
            }
        }

        ff:
        for (String otherStn : otherStns) {
            for (String stn : stns) {
                if (otherStn.equals(stn)) {
                    continue ff;
                }
            }
            newStns.add(otherStn);
        }
        System.out.println(newStns.size());
        System.err.println(new Gson().toJson(newStns));
    }


}