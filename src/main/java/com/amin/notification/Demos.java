package com.amin.notification;

import com.amin.gfs.data.Download;

import java.io.IOException;

/**
 * created By gOD on 9/2/2019 5:42 PM
 */

public class Demos {
    public static void main(String[] args) {
        try {
            String run = new Download().run("https://google.com");

            System.out.println(run);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
