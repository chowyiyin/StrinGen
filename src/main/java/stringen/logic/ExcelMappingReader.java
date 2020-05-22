package stringen.logic;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/***
 * Reads key-value mappings from Excel files of a given path.
 * There should only be two columns in the excel sheet.
 */
public class ExcelMappingReader {

    /***
     * Reads excel file at given path and returns key-value mappings stored in the file.
     * The sheet name will be appended to the start of the key string
     * @param inputStream Input stream of excel file
     * @return Key-value mappings
     * @throws IOException
     * @throws InvalidFormatException
     */
    public static HashMap<String, ArrayList<String>> readFile(InputStream inputStream) throws IOException, InvalidFormatException {
        Workbook workbook = WorkbookFactory.create(inputStream);

        HashMap<String, ArrayList<String>> mapping = new HashMap<>();

        for (Sheet sheet: workbook) {
            HashMap<String, ArrayList<String>> sheetMapping = readSheet(sheet);
            mapping.putAll(sheetMapping);
        }
        return mapping;
    }

    private static HashMap<String, ArrayList<String>> readSheet(Sheet sheet) {
        HashMap<String, ArrayList<String>> mapping = new HashMap<>();
        DataFormatter dataFormatter = new DataFormatter();
        String sheetName  = sheet.getSheetName();
        for (Row row: sheet) {
            String key = sheetName + " " + dataFormatter.formatCellValue(row.getCell(0));
            String value = dataFormatter.formatCellValue(row.getCell(1));
            ArrayList<String> values = new ArrayList<>();
            if (mapping.containsKey(key)) {
                values = mapping.get(key);
            } else {
                values = new ArrayList<>();
            }
            values.add(value);
            mapping.put(key, values);
        }
        return mapping;
    }

}