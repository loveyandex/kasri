package com.amin.runonce;

import com.amin.config.C;
import com.amin.io.MyReader;
import com.amin.io.MyWriter;

import java.io.File;
import java.io.IOException;

public class AddCountries {
    public static void main(String[] args) throws IOException {
        File file = new File(C.STATIONS_PATH);
        MyWriter myWriter = new MyWriter(C.COUNTRIES_CONFIG_PATH, true);
        for (File country : file.listFiles()) {
            if (country.isFile()) {
                String cn = country.getName().split("-stations.conf")[0];
                myWriter.appendStringInNewLine(cn);

            }

        }
    }

}
