package com.amin.analysis;

import com.amin.config.C;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/**
 * is created by aMIN on 5/9/2018 at 23:38
 */
public class SecondMining {
    private final String pathDir;
    Scanner scanner;
    FileReader reader = null;
    private String fileName;

    public SecondMining(String pathDir, String fileName) {
        this.fileName = fileName;
        this.pathDir=pathDir;
        try {
            reader = new FileReader(pathDir+File.separator+fileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        scanner = new Scanner(reader);
    }

    public void createCSV() throws IOException {
        String total = "";
        int[] exps = new int[]{6, 13, 20, 27, 34, 41, 48, 55, 62, 69, 76};
        int counter = 0;
        while (scanner.hasNextLine()) {
            counter++;
            String line = scanner.nextLine();
            if (line.contains("--"))
                continue;
            else {
                if (counter!=3)
                for (int i = 0; i < exps.length; i++)
                    try {
                        if (line.charAt(exps[i]) == ' ') {
                            StringBuilder stringBuilder = new StringBuilder(line);
                            stringBuilder.setCharAt(exps[i], 'N');
                            line = stringBuilder.toString();
                        }
                    } catch (StringIndexOutOfBoundsException e) {

                        for (int j = i; j < exps.length; j++) {
                            StringBuilder ana = new StringBuilder(line);
                            int analength = ana.length();
                            int desire = exps[j];
                            for (int k = analength; k < desire; k++) {
                                ana.insert(k, " ");
                            }
                            ana.insert(ana.length(), "N");

                            line = ana.toString();
                        }
                        break;
                    }
                line = line.replace(" ", ";");
                String s = line;
                for (; ; )
                    if (s.contains(";;"))
                        s = s.replaceAll(";;", ";");
                    else
                        break;
                if (s.charAt(0) == ';')
                    s = s.replaceFirst(";", "");
                if (s.charAt(s.length() - 1) == ';')
                    s = s.substring(0, s.length() - 1);


                s = s.replaceAll("N", "NULL");

                total += s + "\r\n";
            }
        }

        RawMining.writeInFileInOnce(this.pathDir, this.fileName + ".csv", new StringBuilder(total), true);

    }

    public void createCSVInPath(String RootpathDir) throws IOException {
        String total = "";
        int[] exps = new int[]{6, 13, 20, 27, 34, 41, 48, 55, 62, 69, 76};
        int counter = 0;
        while (scanner.hasNextLine()) {
            counter++;
            String line = scanner.nextLine();
            if (line.contains("--"))
                continue;
            else {
                if (counter != 3)
                    for (int i = 0; i < exps.length; i++)
                        try {
                            if (line.charAt(exps[i]) == ' ') {
                                StringBuilder stringBuilder = new StringBuilder(line);
                                stringBuilder.setCharAt(exps[i], 'N');
                                line = stringBuilder.toString();
                            }
                        } catch (StringIndexOutOfBoundsException e) {

                            for (int j = i; j < exps.length; j++) {
                                StringBuilder ana = new StringBuilder(line);
                                int analength = ana.length();
                                int desire = exps[j];
                                for (int k = analength; k < desire; k++) {
                                    ana.insert(k, " ");
                                }
                                ana.insert(ana.length(), "N");

                                line = ana.toString();
                            }
                            break;
                        }
                line = line.replace(" ", ";");
                String s = line;
                for (; ; )
                    if (s.contains(";;"))
                        s = s.replaceAll(";;", ";");
                    else
                        break;
                if (s.charAt(0) == ';')
                    s = s.replaceFirst(";", "");
                if (s.charAt(s.length() - 1) == ';')
                    s = s.substring(0, s.length() - 1);


                s = s.replaceAll("N", "NULL");

                total += s + "\r\n";
            }
        }

        RawMining.writeInFileInOnce(RootpathDir, this.fileName + ".csv", new StringBuilder(total), true);

    }




    public static void main(String[] args) throws IOException {
        new SecondMining("assets/data", "00Z_01_Jan_2017").createCSV();


    }
    public SecondMining(String pathDir, String fileName,boolean haminjuri){
        this.fileName = fileName;
        this.pathDir=pathDir;
    }


    public void allinOneFolder(   String RootpathFile,String newName) throws IOException {
        FileUtils.copyFile(new File(RootpathFile),
                new File(C.THIRDY_PATH+File.separator+newName));
    }


}

