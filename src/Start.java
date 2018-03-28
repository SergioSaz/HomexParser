import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Start {

    public static ArrayList<String> sources = new ArrayList();
    public static String[] names = null;

    public static void download(){

        ArrayList<Integer> cells = new ArrayList();

        try {
            HSSFWorkbook workBook = new HSSFWorkbook(new FileInputStream("C:\\Users\\User\\Desktop\\Сазонов С\\Java\\HomexParser\\Ошибки.xls"));
            HSSFSheet sheet = workBook.getSheetAt(0);
            Iterator<Row> rows = sheet.rowIterator();
            int cellCount = 0;
            while(rows.hasNext()){
                HSSFRow row = (HSSFRow) rows.next();
                HSSFCell cell = row.getCell(0);
                sources.add(cell.getStringCellValue());

                HSSFCell cellFull = row.getCell(3);
                if(cellFull != null && cellFull.getStringCellValue().length() > 10){
                    cells.add(cellCount);
                }
                cellCount++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        names = new String[sources.size()];
        System.out.println("Размер массива ссылок = " + names.length);

        Pattern pName = Pattern.compile("(?<=<h1>).+?(?=</h1>)");

        int count = 0;
        String nextLine = null;

        for(String s : sources){
            if(!cells.contains(count)){
                try(BufferedReader buff =
                            new BufferedReader(new InputStreamReader(new URL(s).openConnection().getInputStream(), "UTF8"))){
                    while(buff.ready()){
                        nextLine = buff.readLine();
                        Matcher mName = pName.matcher(nextLine);
                        if(mName.find()){
                            names[count] = mName.group().trim();
                            break;
                        }
                    }
                }catch(Exception e){
                    System.out.println("Ошибка в старте " + e.getMessage());
                }
            }
            System.out.println("Ссылка номер " + count++ + " обработана");
        }

    }
}
