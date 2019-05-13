package com;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * created By aMIN on 5/13/2019 2:50 PM
 */

public class JCheck {
    public static void main(String[] args) {
        OkHttpClient client = new OkHttpClient();
        class Net {

            String run(String url) throws IOException {
                Request request = new Request.Builder()
                        .url(url)
                        .build();

                try (Response response = client.newCall(request).execute()) {
                    return response.body().string();
                }
            }

        }
        new Thread(() -> {

            try {
                System.out.println(new Net().run("https://google.com"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        String hostname = "Unknown";

        try
        {
            InetAddress addr;
            addr = InetAddress.getLocalHost();
            hostname = addr.getHostName();
        }
        catch (UnknownHostException ex)
        {
            System.out.println("Hostname can not be resolved");
        }
        System.out.println(hostname);
        System.out.println("after them");
    }
}
