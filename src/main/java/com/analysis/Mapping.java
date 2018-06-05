package com.analysis;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/**
 * is created by aMIN on 6/5/2018 at 22:11
 */
public class Mapping {

    static public void mapStationNumTOCities(String Dirpath, String fileName) throws IOException {

        FileReader reader = new FileReader(Dirpath + File.separator + fileName);
        Scanner scanner = new Scanner(reader);
        String total = "";
        int[] exps = new int[]{23, 28, 36, 40, 44, 49, 53, 58};
        int counter = 0;
        while (scanner.hasNextLine()) {
            counter++;
            String line = scanner.nextLine();
            if (counter==1)
                continue;
            else {
                String x = line.substring(3, 19).replaceAll(" ", "^");
                line = line.substring(19, line.length());
                line = "   " + x + line;
                for (int i = 0; i < exps.length; i++)
                    try {
                        if (line.charAt(exps[i]) == ' ') {
                            StringBuilder stringBuilder = new StringBuilder(line);
                            stringBuilder.setCharAt(exps[i], '&');
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
                            ;
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
        System.out.println(total);
        RawMining.writeInFileInOnce("config", "turkey.conf.csv", new StringBuilder(total), true);
    }

    public static void main(String[] args) {
        try {
            Mapping.mapStationNumTOCities("config", "turkey.conf");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
