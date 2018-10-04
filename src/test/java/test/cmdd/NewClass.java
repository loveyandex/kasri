package test.cmdd;// Java program to illustrate
// open cmd prompt

import java.io.InputStream;
import java.util.Scanner;

class NewClass
{
    public static void main(String[] args)
    {
        try
        {         Runtime.getRuntime().exec("cmd /c start cmd.exe /K \"dir && ping localhost\"");
//
//             Just one line and you are done !
//             We have given a command to start cmd
//             /K : Carries out command specified by string
            final Process exec = Runtime.getRuntime().exec(new String[]{"cmd", "/k","start"});
            final InputStream inputStream = exec.getInputStream();
            Scanner scanner = new Scanner(inputStream);
            while (scanner.hasNextLine()) {
                System.out.println(scanner.nextLine());

            }

        }
        catch (Exception e)
        {
            System.out.println("HEY Buddy ! U r Doing Something Wrong ");
            e.printStackTrace();
        }
    }
}
