package test.trycatch;

import com.amin.notify.Noti;

import java.io.IOException;
import java.io.UncheckedIOException;

/**
 * is created by aMIN on 8/7/2018 at 4:31 PM
 */
public class Tes {
    public static void main(String[] args) {
     try{
         throw new org.jsoup.UncheckedIOException(new IOException("ss"));
     }catch (org.jsoup.UncheckedIOException s){

         new Thread(() -> {
             while (true){
                 try {
                     Noti.sendmsg("172.24.65.93",8687,new RuntimeException("god").toString());

                     Thread.sleep(250);
                 } catch (InterruptedException e) {
                     e.printStackTrace();
                 } catch (IOException e) {
                     e.printStackTrace();
                 }
             }
         }).start();     }
    }

}
