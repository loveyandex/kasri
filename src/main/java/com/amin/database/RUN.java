package com.amin.database;

import com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException;

import java.io.File;
import java.sql.SQLException;

/**
 * is created by aMIN on 6/12/2018 at 02:59
 */
public class RUN {

    public static void main(String[] args) {

        loadinOneTable();
    }


    public static void loadinOneTable() {

        try {
            File folder = new File("G:/lastdir/armenia");

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
                                if (dataFile.getName().contains(".csv.csv"))
                                    continue;
                                final String namefile = dataFile.getName().replaceAll(".csv", "");
                                System.out.println(namefile);
                                final String stationFolderName = stationFolder.getName();
                                final String tablename = "armenia_" + stationFolderName + "_" + namefile;

                                final String year_ = yearfolder.getName().replaceAll("year_", "");
                                final String day_ = namefile.substring(4, 6);
                                String month_ = monthfolder.getName().replaceAll("month_", "");
//                                month_ = String.format("%02d", Integer.parseInt(month_));

                                final String insertAllTableToOne = Queries.insertAllTableToOne;

                                final String zone = namefile.substring(0, 3);
                                final String armenia = "armenia";
                                final String query = String.format(insertAllTableToOne,
                                        armenia, stationFolderName, Integer.parseInt(year_),
                                        Integer.parseInt(month_), Integer.parseInt(day_)
                                        , zone, armenia + "_"+stationFolderName+"_" + namefile);
                                try {

                                    Driver.getDriver().getConnection().createStatement().execute(query);

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
