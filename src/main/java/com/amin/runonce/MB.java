package com.amin.runonce;

import com.amin.analysis.CONNECTIONTYPE;
import com.amin.analysis.Mapping;

import java.io.File;


/**
 * is created by aMIN on 11/25/2018 at 5:35 AM
 */
public class MB {
    public static void main(String[] args) {

        final String rootparent = "config/states";
        File file = new File(rootparent);
        king(rootparent, file);

        final String rootparent2 = "config/old-stations";
        File file2 = new File(rootparent2);
        king(rootparent2, file2);

    }

    private static void king(String rootparent, File file) {
        if (file.isDirectory()) {
            final File[] files = file.listFiles();
            for (File file1 : files) {
                System.err.println(file1.getName());
                if (file1.getName().contains(".conf.csv"))
                    Mapping.map(CONNECTIONTYPE.DERBY, rootparent, file1.getName());

            }
        }
    }



}
