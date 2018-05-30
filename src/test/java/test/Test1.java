package test;

import com.get.data.Methods;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

public class Test1 {
    public static void main(String[] args) throws IOException {
        System.out.println(System.getProperty("user.dir") + File.separator + "config/fallenUrls.conf");
        System.out.println(new File(System.getProperty("user.dir") + File.separator + "config/fallenUrls.conf").createNewFile());
        System.out.println("U_ARAB_EMIRATES".toLowerCase());


        for (int i = 0; i < 23; i++) {
            Methods.writeFallenUrls("gkod isj great");

        }


        String[] split = "syria;israel;turkey;u_arab_emirates;saudi_arabia;qatar;OMAN;yemen;pakistan;bahrain;azerbaijan;afghanistan;armenia"
                .split(";");
        for (int i = 0; i < split.length; i++) {
            System.out.println(
                    split[i].toLowerCase()
            );
        }
    }


    @Test
    public void test1() {
        StringBuilder myName = new StringBuilder("kiri ta");
        myName.setCharAt(4, 'x');
        myName.insert(4, 'u');
        System.out.println(myName);
        System.out.println(myName.length());
        int desire = 10;
        for (int i = myName.length(); i < desire; i++) {
            myName.insert(i, " ");
        }
        StringBuilder kir = myName.insert(desire, "gof");
        System.out.println(kir);
    }

    @Test
    public void test2() throws IOException {
        SXSSFWorkbook wb = new SXSSFWorkbook(100); // keep 100 rows in memory, exceeding rows will be flushed to disk
        Sheet sh = wb.createSheet();
        for (int rownum = 0; rownum < 1000; rownum++) {
            Row row = sh.createRow(rownum);
            for (int cellnum = 0; cellnum < 10; cellnum++) {
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


    @Test
    public void exportTwoSheets() throws Exception {
        HSSFWorkbook workbook = new HSSFWorkbook();

        HSSFSheet sheet = workbook.createSheet();

        String[] names = new String[]{"name1", "name2", "name3"};
        Integer[] values = new Integer[]{1, 2, 3};

        for (int index = 0; index < names.length; index++) {
            HSSFRow row = sheet.createRow(index);
            HSSFCell nameCell = row.createCell(0);
            nameCell.setCellType(Cell.CELL_TYPE_STRING);
            nameCell.setCellValue(names[index]);

            HSSFCell valueCell = row.createCell(1);
            valueCell.setCellType(Cell.CELL_TYPE_NUMERIC);
            valueCell.setCellValue(values[index]);
        }

        HSSFRow formulaRow = sheet.createRow(names.length);
        HSSFCell formulaCell = formulaRow.createCell(1);
        formulaCell.setCellValue(Cell.CELL_TYPE_FORMULA);
        formulaCell.setCellFormula("SUM(B1:B3)");

        File file = new File("assets/testFormula.xls");
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        workbook.write(fileOutputStream);
        fileOutputStream.close();
    }


    @Test
    public void writeSimpleTemplate() throws Exception {

        XSSFWorkbook wb = new XSSFWorkbook(OPCPackage.open("assets/simpleTemplate.xlsx"));
        XSSFSheet sheet = wb.getSheetAt(0);

        sheet.createRow(3).createCell(0).setCellValue((new Date()).toString());
        sheet.getRow(3).createCell(1).setCellValue(1);

        sheet.createRow(4).createCell(0).setCellValue((new Date()).toString());
        sheet.getRow(4).createCell(1).setCellValue(3);

        sheet.createRow(5).createCell(0).setCellValue((new Date()).toString());
        sheet.getRow(5).createCell(1).setCellValue(8);

        FileOutputStream fileOut = new FileOutputStream("assets/testSimpleTemplate.xlsx");
        wb.write(fileOut);
        fileOut.close();
    }


}
