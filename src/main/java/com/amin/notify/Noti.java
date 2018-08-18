package com.amin.notify;

import com.amin.notification.Notification;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
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
public class Noti extends Application {
    public static void main(String[] args) {
        launch(args);


    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setScene(new Scene(new VBox()));
        Notification.Notifier instance = Notification.Notifier.INSTANCE;
        instance.setPopupLifetime(Duration.millis(2000));
        this.socket = new DatagramSocket(8687);

        new Thread(() -> {
            while (true) {
                try {
                    final String message = recieveMSg();
                    Platform.runLater(() -> instance.notifyError("crashed", message));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();






        instance.setOnNotificationPressed(event -> {
            System.out.println("god is great");
        });
        instance.setOnHideNotification(event -> {
            System.out.println("gos isso great");
        });

    }


    public static void sendmsg(String ip, int port, String msg) throws IOException {
        DatagramSocket socket = new DatagramSocket();
        DatagramPacket packet = new DatagramPacket(msg.getBytes(), msg.length(), InetAddress.getByName(ip), (int) port);
        socket.send(packet);
    }

    DatagramSocket socket;


    String recieveMSg() throws IOException {
        final byte[] bytes = new byte[1024];
        DatagramPacket packet = new DatagramPacket(bytes, bytes.length);
        socket.receive(packet);
        return new String(packet.getData(), StandardCharsets.UTF_8);
    }


}
