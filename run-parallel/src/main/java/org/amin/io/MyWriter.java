package org.amin.io;

import java.io.*;

/**
 * is created by aMIN on 7/30/2018 at 9:15 PM
 */
public class MyWriter {


    private OutputStreamWriter outputStreamWriter;
    private FileOutputStream fileOutputStream;

    public MyWriter(String pathDirToSave, String childFileName, boolean append) {
        try {
            File dir = new File(pathDirToSave);
            dir.mkdirs();
            File fileTosave = new File(dir, childFileName);
            if (!fileTosave.exists())
                fileTosave.createNewFile();
            else System.out.println("file is existed");

             fileOutputStream = new FileOutputStream(fileTosave, append);
             outputStreamWriter = new OutputStreamWriter(fileOutputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public MyWriter appendStringInFile(String s) throws IOException {
        outputStreamWriter.write(s);
        outputStreamWriter.flush();
        return this;
    }

    public MyWriter appendStringInNewLine(String s) throws IOException {
        outputStreamWriter.write(s+"\r\n");
        outputStreamWriter.flush();
        return this;
    }

    public void close() {
        try {
            outputStreamWriter.close();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
