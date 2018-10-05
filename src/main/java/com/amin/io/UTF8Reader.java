package com.amin.io;

import lombok.Getter;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * is created by aMIN on 8/6/2018 at 1:41 AM
 */

@Getter
public class UTF8Reader {
    private FileReader reader;
    private BufferedReader in;
    Charset windows1252 = Charset.forName("windows-1252");

    public UTF8Reader(String path) throws FileNotFoundException, UnsupportedEncodingException {
        File fileDir = new File(path);
        in = new BufferedReader(
                new InputStreamReader(new FileInputStream(fileDir), windows1252));

    }

    public String readline() throws IOException {
        return in.readLine();
    }


    public static String readFirstLine(String path) {
        FileReader reader = null;
        String s = null;
        try {
            reader = new FileReader(path);

            Scanner scanner = new Scanner(reader);
            s = scanner.nextLine();
            scanner.close();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return s;
    }


    public void close() {
        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {

        final UTF8Reader utf8Reader = new UTF8Reader("C:\\Users\\AminAbvaal\\Desktop\\data\\DRCT_10105_iran__islamic_rep_2.csv");
        final String readline = utf8Reader.readline();
        System.out.println(readline);
        new MyWriter("C:\\Users\\AminAbvaal\\Desktop\\data", "ii.csv", true).appendStringInNewLine(readline);
    }

}
