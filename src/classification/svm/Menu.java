package classification.svm;

import java.io.File;
import java.io.IOException;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

/**
 *
 * @author
 */
public class Menu {

    private int banyak_data = 10;
    private int parameter = 4;
    private double[][] data = new double[banyak_data][parameter];
    private double[] aktual = new double[banyak_data];
    // togle untuk tranning dan testing
    private boolean signal = true;

    public static void main(String[] args) throws IOException, BiffException {
        Menu m = new Menu();
    }

    // input data
    public void input_data() throws IOException, BiffException {
        // memilih file dan directorynya
        File f = new File("./xls/10data.xls");
        // membaca workbooknya
        Workbook w = Workbook.getWorkbook(f);
        // lalu pilih sheetnya
        Sheet sheet = w.getSheet(1);
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[0].length; j++) {
                Cell cell = sheet.getCell(j, i); // kolom, baris
                data[i][j] = Double.valueOf(cell.getContents());
            }
        }
        for (int i = 0; i < aktual.length; i++) {
            Cell cell = sheet.getCell(4, i);
            aktual[i] = Double.valueOf(cell.getContents());
        }
    }
}