package org.amin;

import com.google.gson.Gson;
import org.amin.jsons.TestPojo;

/**
 * is created by aMIN on 10/5/2018 at 6:40 AM
 */
public class J {
    public static void main(String[] args) {
        System.out.println(new Gson().toJson(new TestPojo()
                .setAge("333333333")
                .setName("GOD")
                .setAvg(20.0d)));

    }
}
