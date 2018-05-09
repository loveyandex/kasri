package test;

import com.jsoupway.Methods;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class Test1
{
    public static void main(String[] args) throws IOException {
        System.out.println(System.getProperty("user.dir")+File.separator+"config/fallenUrls.conf");
        System.out.println(new File(System.getProperty("user.dir") + File.separator + "config/fallenUrls.conf").createNewFile());
        System.out.println("U_ARAB_EMIRATES".toLowerCase());


        for (int i = 0; i < 23; i++) {
            Methods.writeFallenUrls("gkod isj great");

        }





        String[] split = "syria;israel;turkey;u_arab_emirates;saudi_arabia;qatar;OMAN;yemen;pakistan;bahrain;azerbaijan;afghanistan;armenia"
                .split(";");
        for (int i = 0; i <split.length; i++) {
            System.out.println(
            split[i].toLowerCase()
            );
        }
    }


    @Test
    public void  test1(){
        StringBuilder myName = new StringBuilder("kiri ta");
        myName.setCharAt(4, 'x');
        myName.insert(4, 'u');
        System.out.println(myName);
        System.out.println(myName.length());
        int desire=10;
        for (int i = myName.length(); i < desire; i++) {
            myName.insert(i," ");
        }
        StringBuilder kir = myName.insert(desire,"gof");
        System.out.println(kir);
    }

}
