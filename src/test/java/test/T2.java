package test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

/**
 * is created by aMIN on 3/11/2018 at 04:34 PM
 */
public class T2 {
    public static void main(String[] args) {
        try {
            readFile("assets/43.data");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    static public void readFile(String path) throws FileNotFoundException {
        FileReader reader = new FileReader(path);
        Scanner scanner = new Scanner(reader);
        StringBuilder item1= new StringBuilder("");
        String s="";

        while (true) {
          item1.append(scanner.nextLine()+"\n");
          if (!scanner.hasNextLine())
              break;
        }
        System.out.println(item1);

    }




}
