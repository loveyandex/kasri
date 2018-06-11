package com.database;

/**
 * is created by aMIN on 6/11/2018 at 23:02
 */
public class Queries {
    private static String CSV_FILE = "C:/Users/AminAbvaal/Desktop/javas/kasri/assets/data/00Z_01 _Jan _2017.csv";
    public static String load_dataInto = "LOAD DATA LOCAL INFILE " + CSV_FILE + "\n" +
            "INTO TABLE dataa\n" +
            "FIELDS TERMINATED BY ';'\n" +
            "ENCLOSED BY '\"'\n" +
            "LINES TERMINATED BY '\\r\\n'\n" +
            "IGNORE 2 LINES\n" +
            "(PRES,HGHT,TEMP,DWPT,RELH,MIXR,DRCT,SKNT,THTA,THTE,THTV);\n" +
            "\n";

    public static String CRT_TBL_X="create table Aminw(id int);";

}