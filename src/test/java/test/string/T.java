package test.string;

import com.amin.database.Driver;
import com.amin.database.Queries;
import org.junit.Test;

import java.io.File;
import java.sql.SQLException;

/**
 * is created by aMIN on 9/28/2018 at 3:17 AM
 */
public class T {
    @Test
    public void test1() {
        System.out.println(String.format("%02d", 12));


        System.out.println("asdfg".substring(3, 5));
    }

    @Test
    public void test2() {
        final String insertAllTableToOne = Queries.insertAllTableToOne;

        final String query = String.format(insertAllTableToOne, "armenia", "12122", 1999, 12, 12, "00Z", "arm2");

        System.out.println(query);
    }


    @Test
    public void test3() throws SQLException {


        File dataFile = new File("G:\\lastdir\\armenia\\year_1973\\month_1\\37789\\00Z_05_Jan_1973.csv");
        final String namefile = dataFile.getName().replaceAll(".csv", "");

        final String tablename = "armenia_37789" + "_" + namefile;


        String sql = Queries.load_dataInto.replaceAll("aminTable",
                "armenia")
                .replaceAll("aminFile", dataFile.getAbsolutePath().replaceAll("\\\\", "/"));
        Driver.getDriver().getConnection().createStatement().executeQuery(sql);
    }


}
