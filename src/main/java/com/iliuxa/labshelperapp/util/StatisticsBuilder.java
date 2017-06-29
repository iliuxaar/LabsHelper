package com.iliuxa.labshelperapp.util;

import com.iliuxa.labshelperapp.application.MainApp;
import com.iliuxa.labshelperapp.model.LabsLoader;
import com.iliuxa.labshelperapp.pojo.Student;
import com.iliuxa.labshelperapp.pojo.StudentInfo;
import javafx.stage.FileChooser;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class StatisticsBuilder {

    private static Workbook workbook;
    private static boolean isReady = false;
    private static File file;

    public static void writeIntoExcel(String group, List<StudentInfo> students, MainApp mainApp) throws IOException {
        openFileChooser(group,mainApp);
        workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet(group);
        int labCount = 0;
        for(int i=0; i < students.size(); i++){
            Row row = sheet.createRow(i+1);
            StudentInfo studentInfo = students.get(i);
            setNameAndSubGroup(row, studentInfo.getStudent(), i+1);
            labCount = students.get(i).getMarks().size();
            setFirstRow(sheet, labCount);
            for(int k = 0; k < labCount; k++){
                CellStyle cellStyle = getCellStyle();
                Cell cell = row.createCell(k+3);
                Integer mark = studentInfo.getMarks().get(k);
                if(mark == null){
                    cell.setCellValue(0);
                } else {
                    cell.setCellValue(mark);
                    isReady = true;
                }
                if(mark == null || mark<4) {
                    cellStyle.setFillForegroundColor(IndexedColors.RED.getIndex());
                    cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
                    isReady = false;
                }
                cell.setCellStyle(cellStyle);
            }
            Cell total = row.createCell(labCount + 3);
            CellStyle totalStyle = getCellStyle();
            if(isReady) {
                total.setCellValue("+");
                totalStyle.setFillForegroundColor(IndexedColors.GREEN.getIndex());
                totalStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
            }
            else {
                total.setCellValue("-");
                totalStyle.setFillForegroundColor(IndexedColors.RED.getIndex());
                totalStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
            }
            total.setCellStyle(totalStyle);
        }
        sheet.autoSizeColumn(1);
        sheet.autoSizeColumn(2);
        sheet.autoSizeColumn(labCount + 3);
        workbook.write(new FileOutputStream(file));
        workbook.close();
        String path = file.getPath();
        if (Desktop.isDesktopSupported()) {
            Desktop.getDesktop().open(new File(path));
        }
    }

    private static void setFirstRow(Sheet sheet, int labCount) {

        Row row = sheet.createRow(0);
        Cell number = row.createCell(0);
        number.setCellValue("№");
        number.setCellStyle(getCellStyle());
        Cell name = row.createCell(1);
        name.setCellValue("Имя");
        name.setCellStyle(getCellStyle());
        Cell subGroup = row.createCell(2);
        subGroup.setCellValue("Подгруппа");
        subGroup.setCellStyle(getCellStyle());
        for(int i = 0; i < labCount; i++){
            Cell lab = row.createCell(i+3);
            lab.setCellValue("Лаб " + (i+1));
            lab.setCellStyle(getCellStyle());
        }
        Cell total = row.createCell(labCount + 3);
        total.setCellValue("Готовность");
        total.setCellStyle(getCellStyle());
    }

    private static CellStyle getCellStyle(){
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyle.setBorderBottom(CellStyle.BORDER_MEDIUM);
        cellStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle.setBorderLeft(CellStyle.BORDER_MEDIUM);
        cellStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle.setBorderRight(CellStyle.BORDER_MEDIUM);
        cellStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle.setBorderTop(CellStyle.BORDER_MEDIUM);
        cellStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
        return cellStyle;
    }

    private static void setNameAndSubGroup(Row row, Student student, int number){
        Cell studentNumber = row.createCell(0);
        studentNumber.setCellValue(number);
        studentNumber.setCellStyle(getCellStyle());
        Cell name = row.createCell(1);
        name.setCellValue(student.getname());
        name.setCellStyle(getCellStyle());
        Cell subGroup = row.createCell(2);
        subGroup.setCellValue(student.getSubGroup());
        subGroup.setCellStyle(getCellStyle());
    }

    private static void openFileChooser(String group, MainApp mainApp) throws IOException {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Excel file (*.xlsx)", "*.xlsx");
        fileChooser.getExtensionFilters().add(extFilter);
        fileChooser.setInitialFileName(group);
        file = fileChooser.showSaveDialog(mainApp.getPrimaryStage());
        if (file != null) {
            File savedFile = new File(file.getPath() + ".xlsx");
            savedFile.createNewFile();
        }
    }
}
