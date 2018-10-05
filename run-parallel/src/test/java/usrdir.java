import org.junit.Test;

import java.io.File;

/**
 * is created by aMIN on 10/5/2018 at 8:31 PM
 */
public class usrdir {
    @Test
    public void test(){
        final String s = System.getProperty("user.dir") + "/../config";
        File file=new File(s);
        System.out.println(file.isDirectory());
        System.out.println(s);
    }

}
