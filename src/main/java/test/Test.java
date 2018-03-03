package test;

import com.jsoupway.Methods;

import java.io.File;
import java.io.IOException;

public class Test
{
    public static void main(String[] args) throws IOException {
        System.out.println(System.getProperty("user.dir")+File.separator+"config/fallenUrls.conf");
        System.out.println(new File(System.getProperty("user.dir") + File.separator + "config/fallenUrls.conf").createNewFile());
        Methods.writeFallenUrls("gkod isj great");
    }
}
