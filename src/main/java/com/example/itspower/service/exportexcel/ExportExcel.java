package com.example.itspower.service.exportexcel;


import com.example.itspower.response.export.EmployeeExportExcelContractEnd;
import com.example.itspower.response.export.ExportExcelDtoReport;
import com.example.itspower.response.export.ExportExcelEmpRest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.List;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExportExcel {
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private XSSFSheet sheet1;
    private XSSFSheet sheet2;
    private XSSFSheet sheet3;
    private XSSFSheet sheet4;
    private XSSFSheet sheet5;
    private List<ExportExcelDtoReport> reportExcel;
    private List<EmployeeExportExcelContractEnd> reportEmpContractEnd;
    private List<ExportExcelEmpRest> exportExcelEmpRests;
    private String file;

    public void initializeData(List<ExportExcelDtoReport> reportExcel, List<EmployeeExportExcelContractEnd> reportEmpContractEnd, List<ExportExcelEmpRest> exportExcelEmpRests, String file) {
        this.reportExcel = reportExcel;
        this.reportEmpContractEnd = reportEmpContractEnd;
        this.exportExcelEmpRests = exportExcelEmpRests;
        this.file = file;
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
        workbook = (XSSFWorkbook) WorkbookFactory.create(targetStream);
        sheet = workbook.getSheetAt(0);
        sheet1 = workbook.getSheetAt(1);
        sheet2 = workbook.getSheetAt(2);
        sheet3 = workbook.getSheetAt(0);
        sheet4 = workbook.getSheetAt(0);
        sheet5 = workbook.getSheetAt(0);
        int rowCount = 7;
        int rowCount1 = 7;
        int rowCount2 = 7;
        XSSFCellStyle style = workbook.createCellStyle();
        XSSFCellStyle style1 = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);
        style.setBorderTop(BorderStyle.THIN); // Đường viền mỏng phía dưới
        style.setBorderBottom(BorderStyle.THIN); // Đường viền mỏng phía dưới
        style.setBorderLeft(BorderStyle.THIN); // Đường viền mỏng phía dưới
        style.setBorderRight(BorderStyle.THIN);
        style.setAlignment(HorizontalAlignment.CENTER); // Căn giữa ngang
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style1.setAlignment(HorizontalAlignment.CENTER); // Căn giữa ngang
        style1.setVerticalAlignment(VerticalAlignment.CENTER); // Căn giữa dọc
        Row row1 = sheet.createRow(4);
        Row row2 = sheet1.createRow(4);
        Row row3 = sheet2.createRow(4);
        creatCellFormat(row1, String.valueOf(reportExcel.get(0).getReportDate()), style1);
        creatCellFormat(row2, String.valueOf(reportExcel.get(0).getReportDate()), style1);
        creatCellFormat(row3, String.valueOf(reportExcel.get(0).getReportDate()), style1);
        Integer sumEmp = 0;
        Integer sumCus = 0;
        int rowString;
        int rowCell = 0;
        int rowKey;
        for (ExportExcelDtoReport data1 : reportExcel) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
            // Đường viền mỏng phía dưới
            createCell(row, columnCount++, data1.getGroupName(), style);
            createCell(row, columnCount++, data1.getRiceEmp(), style);
            createCell(row, columnCount++, data1.getRiceCus(), style);
            createCell(row, columnCount, "", style);
            sumEmp += data1.getRiceEmp();
            sumCus += data1.getRiceCus();
        }
        rowString = rowCount + 2;
        rowKey = rowCount + 6;
        Row rowTotal = sheet3.createRow(rowCount);
        Row row10 = sheet4.createRow(rowString);
        Row rowNameKey = sheet5.createRow(rowKey);
        creatCellFormatStr(rowTotal, 0, "Tổng", style);
        creatCellFormatStr(rowTotal, 1, sumEmp, style);
        creatCellFormatStr(rowTotal, 2, sumCus, style);
        creatCellFormatStr(rowTotal, 3, "", style);
        creatCellFormatStr(row10, 0, "Kế toán", style1);
        creatCellFormatStr(row10, 1, "Giám sát", style1);
        creatCellFormatStr(row10, 2, "Nhà bếp", style1);
        creatCellFormatStr(row10, 3, "Người lập bảng", style1);
        creatCellFormatStr(rowNameKey, 3, "Nguyễn Công Lương", style1);
        for (ExportExcelEmpRest data2 : exportExcelEmpRests) {
            Row row = sheet1.createRow(rowCount1++);
            int columnCount = 0;

            createCell(row, columnCount++, String.valueOf(data2.getReportDate()), style);
            createCell(row, columnCount++, data2.getRestName(), style);
            createCell(row, columnCount++, data2.getLabor(), style);
            createCell(row, columnCount++, data2.getGroupName(), style);
            createCell(row, columnCount, data2.getReasonName(), style);
        }
        for (EmployeeExportExcelContractEnd employee : reportEmpContractEnd) {
            Row row = sheet2.createRow(rowCount2++);
            int columnCount = 0;
            createCell(row, columnCount++, employee.getStartDate(), style);
            createCell(row, columnCount++, employee.getEmployeeName(), style);
            createCell(row, columnCount++, employee.getLaborCode(), style);
            createCell(row, columnCount, employee.getGroupName(), style);
            createCell(row, columnCount, "", style);
        }
    }
    private void creatCellFormatStr(Row row, int getCell, String value, CellStyle cellStyle) {
        Cell cell = row.createCell(getCell);
        cell.setCellValue(value);
        cell.setCellStyle(cellStyle);
    }

    private void creatCellFormatStr(Row row, int getCell, float value, CellStyle cellStyle) {
        Cell cell = row.createCell(getCell);
        cell.setCellValue(value);
        cell.setCellStyle(cellStyle);
    }

    private void creatCellFormat(Row row, String value, CellStyle cellStyle) {
        Cell cell = row.createCell(0);
        cell.setCellValue(value);
        cell.setCellStyle(cellStyle);
    }

    public InputStreamResource export() throws IOException, NoSuchFieldException, IllegalAccessException {
        writeDataLines();
        ByteArrayOutputStream targetStream = new ByteArrayOutputStream(file.length());
        workbook.write(targetStream);
        workbook.close();
        targetStream.close();
        InputStreamResource resource = new InputStreamResource(new ByteArrayInputStream(targetStream.toByteArray()));
        return resource;
    }
}
