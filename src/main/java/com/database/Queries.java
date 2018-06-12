package com.database;

/**
 * is created by aMIN on 6/11/2018 at 23:02
 */
public class Queries {
    private final static String CSV_FILE = "aminFile";
    public static String TABLE_NAME_CSV = "aminTable";

    public static String load_dataInto = "LOAD DATA LOCAL INFILE \'" + CSV_FILE + "\'\n" +
            "INTO TABLE " + TABLE_NAME_CSV + " \n" +
            "FIELDS TERMINATED BY ';'\n" +
            "ENCLOSED BY '\"'\n" +
            "LINES TERMINATED BY '\\r\\n'\n" +
            "IGNORE 2 LINES\n" +
            "(PRES,HGHT,TEMP,DWPT,RELH,MIXR,DRCT,SKNT,THTA,THTE,THTV);\n" +
            "\n";

    public static String CRT_TBL_X = "create table Aminwrr(id int);";


    public static String CRT_TBL_CSV = "create table aminTable" +
            "(\n" +
            "  PRES varchar(20) null primary key,\n" +
            "  HGHT varchar(20) null,\n" +
            "  TEMP varchar(20) null,\n" +
            "  DWPT varchar(20) null,\n" +
            "  RELH varchar(20) null,\n" +
            "  MIXR varchar(20) null,\n" +
            "  DRCT varchar(20) null,\n" +
            "  SKNT varchar(20) null,\n" +
            "  THTA varchar(20) null,\n" +
            "  THTE varchar(20) null,\n" +
            "  THTV varchar(20) null\n" +
            ");";
    public static String CRT_TBL_CSV_NUMERIC = "create table aminTable" +
            "(\n" +
            "  PRES float null primary key,\n" +
            "  HGHT float null,\n" +
            "  TEMP float null,\n" +
            "  DWPT float null,\n" +
            "  RELH float null,\n" +
            "  MIXR float null,\n" +
            "  DRCT float null,\n" +
            "  SKNT float null,\n" +
            "  THTA float null,\n" +
            "  THTE float null,\n" +
            "  THTV float null\n" +
            ");";

}