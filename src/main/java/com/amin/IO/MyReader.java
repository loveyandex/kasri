package com.amin.IO;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * is created by aMIN on 8/6/2018 at 1:41 AM
 */
public class MyReader {


    public static ArrayList<String> readFileLines(String path) throws FileNotFoundException {
        ArrayList<String> lines = new ArrayList<>();
        FileReader reader = new FileReader(path);
        Scanner scanner = new Scanner(reader);
        while (scanner.hasNextLine())
            lines.add(scanner.nextLine());
        return lines;

    }

}
