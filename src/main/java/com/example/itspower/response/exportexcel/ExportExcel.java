package com.example.itspower.response.exportexcel;


import com.example.itspower.util.DateUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.*;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.List;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExportExcel {
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<? extends Object> data;
    private String file;
    List<String> headers;

    public void initializeData(List<?> data, String file, List<String> headers) {
        this.data = data;
        this.file = file;
        this.headers = headers;
    }

    static void createCell(Row row, int columnCount, Object value, XSSFCellStyle style) {
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Byte) {
            cell.setCellValue((Byte) value);
        } else if (value instanceof Double) {
            cell.setCellValue((Double) value);
        } else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }

    private void writeDataLines() throws IOException, NoSuchFieldException, IllegalAccessException {
        FileInputStream target = new FileInputStream(file);
        InputStream targetStream = new ByteArrayInputStream(target.readAllBytes());
        workbook = (XSSFWorkbook) WorkbookFactory.create(target.readAllBytes());
        sheet = workbook.getSheetAt(0);
        int rowCount = 1;
        XSSFCellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);
        for (Object data1 : data) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
            createCellValue(row, columnCount, data1, style, headers);
        }
    }

    public static void createCellValue(Row row, int column, Object ob, XSSFCellStyle style, List<String> headers) throws NoSuchFieldException, IllegalAccessException {
        for (String fieldName : headers) {
            if (hasField(ob, fieldName)) {
                createCell(row, column++, getValueFieldByName(ob, fieldName), style);
            } else {
                createCell(row, column++, " ", style);
            }
        }
    }

    public static Object getValueFieldByName(Object object, String fieldName) throws NoSuchFieldException, IllegalAccessException {
        Field field = object.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        if (field.get(object) instanceof String && isDate((String) field.get(object), "yyyy-MM-dd")) {
            return DateUtils.formatDateString((String) field.get(object), "yyyy-MM-dd");
        } else if (field.get(object) instanceof Boolean) {
            return Boolean.TRUE.equals(field.get(object)) ? "TRUE" : "FALSE";
        }
        return field.get(object) == null ? "" : field.get(object);
    }

    public static boolean hasField(Object object, String fieldName) {
        try {
            object.getClass().getDeclaredField(fieldName);
            return true;
        } catch (NoSuchFieldException e) {
            return false;
        }
    }

    public static boolean isDate(String strDate, String dateFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        sdf.setLenient(false);
        try {
            sdf.parse(strDate);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public byte[] export() throws IOException, NoSuchFieldException, IllegalAccessException {
        writeDataLines();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        workbook.write(bos);
        workbook.close();
        bos.close();
        return bos.toByteArray();
    }
}
