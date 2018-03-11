package com.analysis;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * is created by aMIN on 3/3/2018 at 06:53 PM
 */
public class Mining {
    Scanner scanner;
    StringBuilder item1 = new StringBuilder("");
    StringBuilder item2 = new StringBuilder("");


    public Mining(String path) {
        FileReader reader = null;
        try {
            reader = new FileReader(path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
  scanner   = new Scanner(reader);
    }

    public void readFile() throws FileNotFoundException {


        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String getFileName = "";
            if (line.contains("<h2>") && line.contains("</h2>")) {
                String shallode = line.replace("<h2>", "").replace("</h2>", "");
                System.out.println(shallode);
              getFileName = analysisHeaderGetFileName(shallode);
            }

            if (isItem(line,"<item1>")) {
                String subitem1 = "";
                while (!( subitem1 = scanner.nextLine()).contains("</item1>")) {
                    item1.append(subitem1 + "\n");
                }
            } else
                continue;
            writeInFile(getFileName,item1);

            System.out.println(item1);

            line = scanner.nextLine();
            if (isItem(line,"<item2>")) {
                String subitem2 = "";
                while (!( subitem2 = scanner.nextLine()).contains("</item2>"))
                    item2.append(subitem2 + "\n");
            } else
                continue;

            System.out.println(item2);
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

           if (scanner.hasNextLine())
               readFile();

        }
    }

    private boolean writeInFile(String childFilrName,StringBuilder item1) {



        return false;
    }

    private boolean isItem(String line,String item) {
        return line.contains(item);
    }

    private  String  analysisHeaderGetFileName(String title){
        String regexe;
//        regexe = "[at]?[0-9]{1}[0-9]?Z";
        regexe = "[0-9]{1}[0-9]? [a-z|A-Z]* [0-9]{4}";
        // Step 1: Allocate a Pattern object to compile a regexe
        Pattern pattern = Pattern.compile(regexe, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(title);
        String date="";
        while (matcher.find())
            date=matcher.group();
        return date.replace(" ","_");

    }
    private  void analysisItem1(String item1Content){
    }




    public static void main(String[] args) {
        try {
            new Mining("assets/43.data").readFile();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


}
