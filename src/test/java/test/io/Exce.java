package test.io;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * is created by aMIN on 8/5/2018 at 11:14 PM
 */
public class Exce {
    public static void main(String[] args) throws IOException {
        {
            LinkedList<String> linkedList = new LinkedList() ;
            ArrayList<String> strings=new ArrayList<>();

            strings.add("sss");


            linkedList.addLast("god is great");

            linkedList.addFirst("mohamad");
            linkedList.addLast("abolfazl");

            linkedList.stream().filter(s -> s.contains("abolf")).forEach(System.out::println);



        }
    }
}
