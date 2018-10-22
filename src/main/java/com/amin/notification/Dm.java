package com.amin.notification;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;

/**
 * is created by aMIN on 8/7/2018 at 1:16 AM
 */
public class Dm extends Application {


    DatagramSocket socket;

    public static void main(String[] args) {
        launch(args);


    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setScene(new Scene(new VBox()));
        NotificationProgress.Notifier instance = NotificationProgress.Notifier.INSTANCE;
        instance.setPopupLifetime(Duration.millis(120000));

        instance.notifyInfo("god is grea", "ddddddddddd");
        final ProgressBar progressBar = instance.progressBar;
        progressBar.setDisable(true);


        instance.setOnNotificationPressed(event -> {
            System.out.println("god is great");
        });
        instance.setOnHideNotification(event -> {
            System.out.println("gos isso great");
        });

    }

    void sendmsg(String ip, int port, String msg) throws IOException {
        DatagramSocket socket = new DatagramSocket();
        DatagramPacket packet = new DatagramPacket(msg.getBytes(), msg.length(), InetAddress.getByName(ip), (int) port);
        socket.send(packet);
    }

    String recieveMSg() throws IOException {
        final byte[] bytes = new byte[1024];
        DatagramPacket packet = new DatagramPacket(bytes, bytes.length);
        socket.receive(packet);
        return new String(packet.getData(), StandardCharsets.UTF_8);
    }


}
