package com.amin.runonce;

import java.io.*;
import java.util.Scanner;

public class XCopyJubin {
    public static void main(String[] args) throws IOException {
        Process exec = Runtime.getRuntime().exec("cmd.exe /k mkdir %appdata%\\\"Jubin's Glob\"");
        exec = Runtime.getRuntime().exec("cmd.exe /k  echo All|xcopy /e /s /h config %appdata%\\\"Jubin's Glob\"");
        BufferedReader is = new BufferedReader(new InputStreamReader(exec.getInputStream()));
        String line;
        while ((line = is.readLine()) != null)
            System.out.println(line);
    }
}
