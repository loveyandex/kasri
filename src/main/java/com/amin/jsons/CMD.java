package com.amin.jsons;


import java.io.IOException;

/**
 * is created by aMIN on 10/5/2018 at 2:02 AM
 */
public class CMD {
    public static Process run(String cmd) throws IOException {
        return Runtime.getRuntime().exec(cmd);

    }

    public static void main(String[] args) throws IOException {
        final String cmd = "cmd /c start cmd.exe /K \"cd target/classes && java com.amin.ui.main.main.Run  && ping localhost\"";
        run(cmd);
    }
}
