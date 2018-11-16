package test.datastructures;

import com.sun.org.apache.xalan.internal.xsltc.util.IntegerArray;
import org.junit.Test;

import java.util.ArrayList;
import java.util.stream.IntStream;

public class Arrlis {
    @Test
    public void test1() {
        ArrayList<String> strings = new ArrayList<>();
        IntStream.range(0, 23).forEach(value -> strings.add(String.valueOf(value)));

        strings.add(0, "abolfazl");

        strings.forEach(System.out::println);
        System.out.println(strings.size());


    }
}
