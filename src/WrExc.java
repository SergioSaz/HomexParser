import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

public class WrExc {
    public static void main(String[] args) throws IOException {

        Long start = new Date().getTime();

        System.out.println("ПРОГРАММА СТАРТОВАЛА");
        Start.download();
        System.out.println("ЗАГРУЗКА ПРОШЛА");
        ArrayList<DataModel> dataModel = new ArrayList();

        HSSFWorkbook workBook = new HSSFWorkbook(new FileInputStream("C:\\Users\\User\\Desktop\\Сазонов С\\Java\\HomexParser\\Ошибки.xls"));
        HSSFSheet sheet = workBook.getSheetAt(0);
        Iterator<Row> rows = sheet.rowIterator();
        int count = 0;

        while(rows.hasNext()){
            DataModel tempData = new DataModel();
            HSSFRow row = (HSSFRow) rows.next();

            HSSFCell sourceCell = row.getCell(0);
            tempData.setSource(sourceCell.getStringCellValue());

            HSSFCell oldValueCell = row.getCell(1);
            if(oldValueCell != null){
                tempData.setOldValue(oldValueCell.getStringCellValue());
            }

            HSSFCell newValueCell = row.getCell(2);
            tempData.setNewValue(newValueCell.getStringCellValue());

            HSSFCell nameCell = row.getCell(3);
            if(nameCell != null && nameCell.getStringCellValue().length() > 10){
                tempData.setName(nameCell.getStringCellValue());
            }else{
                tempData.setName(Start.names[count]);
            }

            count++;
            dataModel.add(tempData);

        }

        workBook.close();

        HSSFWorkbook newWorkBook = new HSSFWorkbook();
        Sheet sheetWr = newWorkBook.createSheet("Итоговый лист");
        int rowCount = 0;

        for(DataModel d : dataModel){

            Row row = sheetWr.createRow(rowCount);

            Cell sourceCell = row.createCell(0);
            sourceCell.setCellValue(d.getSource());

            Cell oldValueCell = row.createCell(1);
            oldValueCell.setCellValue(d.getOldValue());

            Cell newValueCell = row.createCell(2);
            newValueCell.setCellValue(d.getNewValue());

            if(d.getName() != null){
                Cell nameCell = row.createCell(3);
                nameCell.setCellValue(d.getName());
            }

            rowCount++;
        }

        newWorkBook.write(new FileOutputStream(new File("C:\\Users\\User\\Desktop\\Сазонов С\\Java\\HomexParser\\Ошибки.xls")));
        newWorkBook.close();

        long finish = new Date().getTime();

        System.out.printf("Время работы %d секунд", (finish - start) / 1000);

    }
}
