package com.amin.database;

import java.io.File;
import java.sql.SQLException;

/**
 * is created by aMIN on 6/12/2018 at 02:59
 */
public class Convertor {
    private static Convertor ourInstance = new Convertor();

    public static Convertor getInstance() {
        return ourInstance;
    }

    private Convertor() {

    }


    public void convertCSVTotable(String csvFile) {
        String TableName=getJustNameFile(csvFile);
        try {
            Driver.getDriver().createCSVTable("t_"+TableName);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void convertCSVFileTotable(File csvFile) {
        String TableName=getJustNameFile(csvFile.getName());
        try {
            Driver.getDriver().createCSVTable("t"+TableName);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private String getJustNameFile(String fileName) {
        String[] split = fileName.split("\\.");
        String tot = "";
        for (int i = 0; i < split.length - 1; i++)
            tot +=split[i] +".";
        return tot.substring(0,tot.length()-1);

    }

    public static void main(String[] args){

        try {
//            Convertor.getInstance().convertCSVPathTotable("assets/data/00Z_01_Jan_2017.csv");



            String fil="C:/Users/AminAbvaal/Desktop/javas/kasri/assets/data/00Z_01_Jan_2017.csv";
            String sql = Queries.load_dataInto.replaceAll("aminTable", "table100z_01_jan_2017").replaceAll("aminFile",fil);
            System.out.println(sql);
            Driver.getDriver().getConnection().createStatement().executeQuery(sql);

//            throw new SQLException();

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }


}
