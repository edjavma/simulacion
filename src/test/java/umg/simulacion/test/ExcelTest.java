package umg.simulacion.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import umg.simulacion.model.Ventas;

public class ExcelTest {

	public static void main(String[] args) throws IOException {
        String excelFilePath = "C:\\Users\\ejmorales\\Documents\\prueba.xlsx";
        FileInputStream inputStream = new FileInputStream(new File(excelFilePath));
         
        List<Ventas> ventas = new ArrayList<Ventas>();
        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet firstSheet = workbook.getSheetAt(0);
        Iterator<Row> iterator = firstSheet.iterator();
         
        while (iterator.hasNext()) {
            Row nextRow = iterator.next();
            Iterator<Cell> cellIterator = nextRow.cellIterator();
            Ventas venta = new Ventas(); 
            
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                 
                switch (cell.getCellType()) {
                    case Cell.CELL_TYPE_STRING:
                        //System.out.print(cell.getStringCellValue());
                        break;
                    case Cell.CELL_TYPE_BOOLEAN:
                        //System.out.print(cell.getBooleanCellValue());
                        break;
                    case Cell.CELL_TYPE_NUMERIC:                    	                        
                        if(HSSFDateUtil.isCellDateFormatted(cell)){
                        	venta.setFecha(cell.getDateCellValue());
                        }else{
                        	venta.setTotal(cell.getNumericCellValue());
                        }
                        break;
                }           
            }
            ventas.add(venta);
        }
         
        System.out.println(ventas.size());
        workbook.close();
        inputStream.close();
    }
		
}
