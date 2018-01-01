package com.excel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * Created by ABOLFAZL on 11/18/2017.
 */
public class Excell {
    static int jj=22;
    public static void main(String[] args) throws IOException {
        String fs = System.getProperty("user.dir") + "\\assets\\Data"+ jj +".dat";
        String fs1 = System.getProperty("user.dir") + "\\assets\\Data"+ jj +".csv";
        File file = new File(fs);
        File file1 = new File(fs1);
        file1.createNewFile();
        Scanner sc = new Scanner(file);
//                    List<String> lines = new ArrayList<String>();
        String dataline = "";
        FileWriter fw = new FileWriter(file1);
        while (sc.hasNextLine()) {

            dataline= sc.nextLine().replaceAll(" ",",").replaceAll(",,,,",",").replaceAll(",,,",",").replaceAll(",,",",");
            fw.write(dataline);
            fw.write("\n");
            fw.flush();
        }
        fw.close();

    }
}
