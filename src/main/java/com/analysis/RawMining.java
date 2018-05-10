package com.analysis;

import java.io.*;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * is created by aMIN on 3/3/2018 at 06:53 PM
 */
public class RawMining {
    Scanner scanner;
    StringBuilder item1 = new StringBuilder("");
    StringBuilder item2 = new StringBuilder("");


    public RawMining(String path) {
        FileReader reader = null;
        try {
            reader = new FileReader(path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        scanner = new Scanner(reader);
    }

    public void readFile() throws IOException {
        item1.setLength(0);
        item2.setLength(0);
        String getFileName = "";

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.contains("<h2>") && line.contains("</h2>")) {
                String shallode = line.replace("<h2>", "").replace("</h2>", "");
                System.out.println(shallode);
                getFileName = analysisHeaderGetFileName(shallode);
            }

            if (isItem(line, "<item1>")) {
                String subitem1 = "";
                while (!(subitem1 = scanner.nextLine()).contains("</item1>")) {
                    item1.append(subitem1 + "\r\n");
                }
            } else
                continue;
            writeInFileInOnce(System.getProperty("user.dir") + "/assets", getFileName, item1, true);

            System.out.println(item1);

            line = scanner.nextLine();
            if (isItem(line, "<item2>")) {
                String subitem2 = "";
                while (!(subitem2 = scanner.nextLine()).contains("</item2>"))
                    item2.append(subitem2 + "\n\r");
            } else
                continue;

            writeInFileInOnce(System.getProperty("user.dir") + "/assets", getFileName + "Item2", item2, true);
            System.out.println(item2);
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

            if (scanner.hasNextLine())
                readFile();

        }
    }

    public static boolean writeInFileInOnce(String pathDirToSave, String childFileName, StringBuilder stringBuilder, boolean closIt) throws IOException {
        File dir = new File(pathDirToSave);
        dir.mkdir();
        File fileTosave = new File(dir, childFileName);
        fileTosave.createNewFile();
        OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(fileTosave, false));
        writer.write(stringBuilder.toString());
        writer.flush();
        if (closIt)
            writer.close();
        return true;
    }


    private boolean isItem(String line, String item) {
        return line.contains(item);
    }

    private String analysisHeaderGetFileName(String title) {
        String regexe;
//        regexe = "[at]?[0-9]{1}[0-9]?Z";
        regexe = "[0-9]{1}[0-9]? [a-z|A-Z]* [0-9]{4}";
        // Step 1: Allocate a Pattern object to compile a regexe
        Pattern pattern = Pattern.compile(regexe, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(title);
        String date = "";
        while (matcher.find())
            date = matcher.group();
        String regexe1;
        regexe1 = "[at]?[0-9]{1}[0-9]?Z";
        // Step 1: Allocate a Pattern object to compile a regexe
        Pattern pattern1 = Pattern.compile(regexe1, Pattern.CASE_INSENSITIVE);
        Matcher matcher1 = pattern1.matcher(title);
        String zone = "";
        while (matcher1.find())
            zone = matcher1.group();

        return zone + "_" + date.replace(" ", " _");
    }


    private void analysisItem1(String item1Content) {

    }

    public static void main(String[] args) {
        try {
            new RawMining("assets/43.data").readFile();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
//
//        File file = new File(System.getProperty("user.dir") + "/assets/f.dat");
//        try {
//            file.createNewFile();
//            System.out.println(file.getParentFile().getParentFile().isFile());
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


    }

}