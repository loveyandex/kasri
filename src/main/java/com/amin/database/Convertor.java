package com.amin.database;

import com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException;

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

        follan("iran__islamic_rep");
    }


    public static void follan(String country) {

        try {

            File folder = new File("G:/lastdir/"+ country);

            final File[] yearsfolder = folder.listFiles();
            for (File yearfolder : yearsfolder) {

                final File[] monthesfolder = yearfolder.listFiles();

                for (File monthfolder : monthesfolder) {

                    final File[] stationsfolder = monthfolder.listFiles();

                    for (File stationFolder : stationsfolder) {

                        final File[] datas = stationFolder.listFiles();

                        for (File dataFile : datas) {

                            if (dataFile.isFile()) {
                                if (dataFile.getName().contains(".csv.csv"))
                                    continue;


                                final String namefile = dataFile.getName().replaceAll(".csv", "");

                                final String tablename = country+"_" + stationFolder.getName() + "_" + namefile;


                                String sql = Queries.load_dataInto.replaceAll("aminTable",
                                        tablename)
                                        .replaceAll("aminFile", dataFile.getAbsolutePath().replaceAll("\\\\", "/"));
//
                                String CRT_TBL_CSV_NUMERIC = Queries.CRT_TBL_CSV_NUMERIC
                                        .replaceAll("aminTable", tablename);


                                try {


                                    Driver.getDriver().getConnection().createStatement().execute(CRT_TBL_CSV_NUMERIC);

                                    Driver.getDriver().getConnection().createStatement().executeQuery(sql);
                                    System.out.println(namefile);
                                } catch (MySQLSyntaxErrorException e) {
                                    System.err.println(e);

                                }

                            }

                        }
                    }

                }


            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


}
