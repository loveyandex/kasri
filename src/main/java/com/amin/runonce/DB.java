package com.amin.runonce;

import com.amin.analysis.Mapping;

import java.io.File;


/**
 * is created by aMIN on 11/25/2018 at 5:35 AM
 */
public class DB {
    public static void main(String[] args) {

        final String rootparent = "config/old-stations";
//        final String rootparent = "config/states";
        File file = new File(rootparent);
        if (file.isDirectory()) {
            final File[] files = file.listFiles();
            for (File file1 : files) {
                System.err.println(file1.getName());
                if (file1.getName().contains(".conf.csv"))
                    Mapping.map(rootparent, file1.getName());

            }
        }

    }
}
