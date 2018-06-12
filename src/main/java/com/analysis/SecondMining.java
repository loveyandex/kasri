package com.analysis;

import java.io.*;
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
                total += s + "\r\n";
            }

        }

        RawMining.writeInFileInOnce(this.pathDir, this.fileName + ".csv", new StringBuilder(total), true);

    }


    public static void NtoNULLInCSV(File file) {
        try {
            FileReader readFile = new FileReader(file);
            File file1 = new File(file.getParent(), file.getName() + "L");
            boolean newFile = file1.createNewFile();
            file1.renameTo(file);
            FileWriter writer=new FileWriter(file1);
            Scanner scanne = new Scanner(readFile);
            int count = 0;
            while (scanne.hasNextLine()) {
                if (count == 0 || count == 1)
                    continue;
                count++;
                String line = scanne.nextLine();
                line = line.replaceAll("N", "NULL");
            }
            writer.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public static void main(String[] args) {
        //            new SecondMining("assets/data", "00Z_01 _Jan _2017").createCSV();
        SecondMining.NtoNULLInCSV(new File("assets/data/00Z_01 _Jan _2017.csv"));


    }
}

