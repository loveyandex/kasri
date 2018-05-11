package test

import com.get.data.Methods
import org.apache.poi.hssf.usermodel.HSSFCell
import org.apache.poi.hssf.usermodel.HSSFRow
import org.apache.poi.hssf.usermodel.HSSFSheet
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.openxml4j.opc.OPCPackage
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.util.CellReference
import org.apache.poi.xssf.streaming.SXSSFWorkbook
import org.apache.poi.xssf.usermodel.XSSFSheet
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.junit.Test

import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.ArrayList
import java.util.Arrays
import java.util.Date

class Test1 {


    @Test
    fun test1() {
        val myName = StringBuilder("kiri ta")
        myName.setCharAt(4, 'x')
        myName.insert(4, 'u')
        println(myName)
        println(myName.length)
        val desire = 10
        for (i in myName.length until desire) {
            myName.insert(i, " ")
        }
        val kir = myName.insert(desire, "gof")
        println(kir)
    }

    @Test
    @Throws(IOException::class)
    fun test2() {
        val wb = SXSSFWorkbook(100) // keep 100 rows in memory, exceeding rows will be flushed to disk
        val sh = wb.createSheet()
        for (rownum in 0..999) {
            val row = sh.createRow(rownum)
            for (cellnum in 0..9) {
                val cell = row.createCell(cellnum)
                val address = CellReference(cell).formatAsString()
                cell.setCellValue(address)
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

        val out = FileOutputStream(File("assets/sxssf.xlsx"))
        wb.write(out)
        out.close()
        // dispose of temporary files backing this workbook on disk
        wb.dispose()
    }


    @Test
    @Throws(Exception::class)
    fun exportTwoSheets() {
        val workbook = HSSFWorkbook()

        val sheet = workbook.createSheet()

        val names = arrayOf("name1", "name2", "name3")
        val values = arrayOf(1, 2, 3)

        for (index in names.indices) {
            val row = sheet.createRow(index)
            val nameCell = row.createCell(0)
            nameCell.cellType = Cell.CELL_TYPE_STRING
            nameCell.setCellValue(names[index])

            val valueCell = row.createCell(1)
            valueCell.cellType = Cell.CELL_TYPE_NUMERIC
            valueCell.setCellValue(values[index].toDouble())
        }

        val formulaRow = sheet.createRow(names.size)
        val formulaCell = formulaRow.createCell(1)
        formulaCell.setCellValue(Cell.CELL_TYPE_FORMULA.toDouble())
        formulaCell.cellFormula = "SUM(B1:B3)"

        val file = File("assets/testFormula.xls")
        val fileOutputStream = FileOutputStream(file)
        workbook.write(fileOutputStream)
        fileOutputStream.close()
    }


    @Test
    @Throws(Exception::class)
    fun writeSimpleTemplate() {
        val wb = XSSFWorkbook(OPCPackage.open("assets/simpleTemplate.xlsx"))
        val sheet = wb.getSheetAt(0)

        sheet.createRow(3).createCell(0).setCellValue(Date().toString())
        sheet.getRow(3).createCell(1).setCellValue(1.0)

        sheet.createRow(4).createCell(0).setCellValue(Date().toString())
        sheet.getRow(4).createCell(1).setCellValue(3.0)

        sheet.createRow(5).createCell(0).setCellValue(Date().toString())
        sheet.getRow(5).createCell(1).setCellValue(8.0)

        val fileOut = FileOutputStream("assets/simpleTemplate.xlsx")
        wb.write(fileOut)
        fileOut.close()
    }

    companion object {
        @Throws(IOException::class)
        @JvmStatic
        fun main(args: Array<String>) {
            println(System.getProperty("user.dir") + File.separator + "config/fallenUrls.conf")
            println(File(System.getProperty("user.dir") + File.separator + "config/fallenUrls.conf").createNewFile())
            println("U_ARAB_EMIRATES".toLowerCase())


            for (i in 0..22) {
                Methods.writeFallenUrls("gkod isj great")

            }


            val split = "syria;israel;turkey;u_arab_emirates;saudi_arabia;qatar;OMAN;yemen;pakistan;bahrain;azerbaijan;afghanistan;armenia"
                    .split(";".toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray()
            for (i in split.indices) {
                println(
                        split[i].toLowerCase()
                )
            }
        }
    }


}
