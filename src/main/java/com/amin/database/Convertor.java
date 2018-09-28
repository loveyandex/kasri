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

        follan();
    }


    public static void follan() {

        try {
            File folder = new File("G:/lastdir/armenia");

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

                                final String tablename = "armenia_" + stationFolder.getName() + "_" + namefile;


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


    public static void loadinOneTable() {

        try {
            File folder = new File("G:/lastdir/armenia");

            String CRT_TBL_CSV_NUMERIC = Queries.CRT_ONE_TBL_FOR_ALL_CSV_NUMERIC
                    .replaceAll("aminTable", "armenia");


//            Driver.getDriver().getConnection().createStatement().execute(CRT_TBL_CSV_NUMERIC);
            final File[] yearsfolder = folder.listFiles();
            for (File yearfolder : yearsfolder) {

                final File[] monthesfolder = yearfolder.listFiles();

                for (File monthfolder : monthesfolder) {

                    final File[] stationsfolder = monthfolder.listFiles();

                    for (File stationFolder : stationsfolder) {

                        final File[] datas = stationFolder.listFiles();
                        ;

                        for (File dataFile : datas) {

                            if (dataFile.isFile()) {
                                final String namefile = dataFile.getName().replaceAll(".csv", "");
                                System.out.println(namefile);
                                final String tablename = "armenia_" + stationFolder.getName() + "_" + namefile;


                                String month_ = monthfolder.getName().replaceAll("month_", "");
                                month_ = String.format("%02d", Integer.parseInt(month_));
                                String sql = String.format(Queries.load_dataInto.replaceAll("aminTable",
                                        "armenia")
                                                .replaceAll("aminFile", dataFile.getAbsolutePath().replaceAll("\\\\", "/"))
                                                + " SET station=%s; SET col_data=%s-%s-%s"
                                        , stationFolder, yearfolder.getName().replaceAll("year_", ""),
                                        month_
                                        , namefile.substring(4, 6));

                                ;
//
//                                String CRT_TBL_CSV_NUMERIC = Queries.CRT_TBL_CSV_NUMERIC
//                                        .replaceAll("aminTable", tablename);

                                try {

                                    Driver.getDriver().getConnection().createStatement().executeQuery(sql);

                                } catch (MySQLSyntaxErrorException e) {
                                    System.out.println(e);

                                }

                            }

                        }
                    }

                }


            }

//            String fil = "G:/lastdir/armenia/year_1973/month_1/37789/00Z_05_Jan_1973.csv";
//            String sql = Queries.load_dataInto.replaceAll("aminTable", "armenia_37789_00Z_05_Jan_1973")
//                    .replaceAll("aminFile", fil);
//
//
//            String CRT_TBL_CSV_NUMERIC = Queries.CRT_TBL_CSV_NUMERIC
//                    .replaceAll("aminTable", "armenia_37789_00Z_05_Jan_1973");
//
//
//            Driver.getDriver().getConnection().createStatement().execute(CRT_TBL_CSV_NUMERIC);
//
//            Driver.getDriver().getConnection().createStatement().executeQuery(sql);

//            throw new SQLException();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


}
