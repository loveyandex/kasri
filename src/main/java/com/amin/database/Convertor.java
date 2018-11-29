package com.amin.database;

import com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException;

import java.io.File;
import java.sql.SQLException;

/**
 * is created by aMIN on 6/12/2018 at 02:59
 */
public class Convertor {
    private static Convertor ourInstance = new Convertor();

    private Convertor() {

    }

    public static Convertor getInstance() {
        return ourInstance;
    }

    private String getJustNameFile(String fileName) {
        String[] split = fileName.split("\\.");
        String tot = "";
        for (int i = 0; i < split.length - 1; i++)
            tot += split[i] + ".";
        return tot.substring(0, tot.length() - 1);

    }


}
