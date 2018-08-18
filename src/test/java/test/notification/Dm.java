

package test.notification;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import test.sounds.Tes;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;

/**
 * is created by aMIN on 8/7/2018 at 1:16 AM
 */
public class Dm extends Application {


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

        new Thread(() -> {
            try {
                new Tes().start(stage);
            } catch (Exception e) {
                e.printStackTrace();
            }
            double d = 0.0;
            while (true) {
                progressBar.setProgress((d++)/100);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
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


    void sendmsg(String ip, int port, String msg) throws IOException {
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
