package test;

import com.get.data.Methods;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
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

@Test
public void test2() throws IOException {
    SXSSFWorkbook wb = new SXSSFWorkbook(100); // keep 100 rows in memory, exceeding rows will be flushed to disk
    Sheet sh = wb.createSheet();
    for(int rownum = 0; rownum < 1000; rownum++){
        Row row = sh.createRow(rownum);
        for(int cellnum = 0; cellnum < 10; cellnum++){
            Cell cell = row.createCell(cellnum);
            String address = new CellReference(cell).formatAsString();
            cell.setCellValue(address);
        }

    }

//    // Rows with rownum < 900 are flushed and not accessible
//    for(int rownum = 0; rownum < 900; rownum++){
//        Assert.assertNull(sh.getRow(rownum));
//    }
//
//    // ther last 100 rows are still in memory
//    for(int rownum = 900; rownum < 1000; rownum++){
//        Assert.assertNotNull(sh.getRow(rownum));
//    }

    FileOutputStream out = new FileOutputStream(new File("assets/sxssf.xlsx"));
    wb.write(out);
    out.close();

    // dispose of temporary files backing this workbook on disk
    wb.dispose();
}
}
