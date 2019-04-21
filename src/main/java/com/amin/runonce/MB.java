package com.amin.runonce;

import com.amin.analysis.CONNECTIONTYPE;
import com.amin.analysis.Mapping;
import com.amin.config.C;

import java.io.File;


/**
 * is created by aMIN on 11/25/2018 at 5:35 AM
 */
public class MB {
    public static void main(String[] args) {


        final String rootparent = C.STATES_PATH;
        File file = new File(rootparent);
        king(file);

    }

    private static void king(File file) {
        if (file.isDirectory()) {
            final File[] files = file.listFiles();
            for (File file1 : files) {
                System.err.println(file1.getName());
                if (file1.getName().contains(".conf.csv"))
                    Mapping.map(CONNECTIONTYPE.DERBY, C.STATES_PATH, file1.getName());

            }
        }
    }



}
