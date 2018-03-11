package com.analysis;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

/**
 * is created by aMIN on 3/3/2018 at 06:53 PM
 */
public class Mining {

    public void readFile(String path) throws FileNotFoundException {
        FileReader reader = new FileReader(path);
        Scanner scanner = new Scanner(reader);

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.contains("<h2>") && line.contains("</h2>")){
                String shallode=line.replace("<h2>","").replace("</h2>","");
                shallode.contains("");
            }
        }
    }
}
