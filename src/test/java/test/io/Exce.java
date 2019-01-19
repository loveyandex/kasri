package test.io;

import com.amin.config.C;
import com.amin.io.MyWriter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * is created by aMIN on 8/5/2018 at 11:14 PM
 */
public class Exce {
    public static void main(String[] args) throws IOException {
        MyWriter myWriter = new MyWriter("config", "old-exception-stations.conf", true);
        File file = new File(C.STATES_PATH);
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                final File file1 = files[i];
                if (file1.getName().contains(".conf.csv")) {
                    final String x = file1.getName().replaceAll(".conf.csv", "");
                    File file2 = new File("config/states", x + ".conf");
                    file2.delete();
                }


            }
            {
                LinkedList<String> linkedList = new LinkedList();
                ArrayList<String> strings = new ArrayList<>();

                strings.add("sss");


                linkedList.addLast("god is great");

                linkedList.addFirst("mohamad");
                linkedList.addLast("abolfazl");

                linkedList.stream().filter(s -> s.contains("abolf")).forEach(System.out::println);


            }
        }
    }
}
