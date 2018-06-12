package test.analys;

import com.config.C;
import com.database.Driver;
import org.junit.Test;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * is created by aMIN on 6/6/2018 at 01:38
 */
public class T {
    @Test
    public void test(){
        String line = "   ADANA/INCIRLIK A LTAG        17350  37 00N  035 25E   66   X     T          6 TR";
        System.out.println(line);
        String x = line.substring(3, 19).replaceAll(" ", "^");
        line=line.substring(19,line.length());
        line="   "+x+line;
        System.out.println(x);
        System.out.println(line);

        System.out.println("   ABADAN INTL AIRP OIAA        40831".length());

    }


    @Test
    public void test2(){
        String string="ANKAR^^K";
        System.out.println(string.replaceAll("[?^]",C.SPACE));

        int num = Integer.parseInt("000008");
        String text = (num < 10 ? "0" : "") + num;
        System.out.println(text);
    }


    @Test
    public void test34(){
        try {
            ResultSet resultSet = Driver.getDriver().getConnection().createStatement().executeQuery("select *from  f");
            Array array = resultSet.getArray(1);

            while (resultSet.next())
                System.out.println(resultSet.getString(1));

        }catch (NumberFormatException w){
            System.out.println("a");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void test11(){
        String s="23;2323;23.23;2323.3;N;23;N;2323.23;N;N";
        System.out.println(s.replaceAll("N", "NULL"));
        System.out.println(s);
    }







}
