package com.jjdx.studentmanage.Util;

import com.jjdx.studentmanage.pojo.Student;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

/**
 Excel交互

 @ Author: 绝迹的星 <br>
 @ Time: 2024/7/2 <br> */
public class ExcelUtil {
    /**
     保存表信息到excel

     @param table 表
     @param file  保存位置
     */
    public static void saveAsExcel(TableView table, File file, boolean withOrderId) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet(file.getName());

        ObservableList<TableColumn> columns = table.getColumns();
        Row headerRow = sheet.createRow(0);
        int offset = withOrderId ? 1 : 0;// 如果有序号, 则跳过第一列
        for (int i = offset; i < columns.size(); i++) {// 创建标题行
            headerRow.createCell(i - offset).setCellValue(columns.get(i).getText());
        }
        int rowIndex = 1;
        for (Object row : table.getItems()) {// 数据行
            Row dataRow = sheet.createRow(rowIndex++);
            for (int i = offset; i < columns.size(); i++) {
                dataRow.createCell(i - offset).setCellValue(columns.get(i).getCellData(row).toString());
            }
            try (FileOutputStream fos = new FileOutputStream(file)) {
                workbook.write(fos);
            }
        }
    }

    /**
     加载excel文件
     */
    public static List<Student> loadInfo(File file) throws IOException {
        List<Student> students = new ArrayList<>();
        Workbook workbook = new XSSFWorkbook(new FileInputStream(file));
        Sheet sheet = workbook.getSheetAt(0);
        Row headerRow = sheet.getRow(0);
        int numColumns = headerRow.getLastCellNum();

        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row == null) continue;
            Student student = new Student();
            for (int j = 0; j < numColumns; j++) {
                Cell cell = row.getCell(j);
                if (cell == null) continue;
                switch (headerRow.getCell(j).getStringCellValue()) {
                    case "学号" -> student.setId(cell.getStringCellValue());
                    case "姓名" -> student.setName(cell.getStringCellValue());
                    case "专业" -> student.setDept(cell.getStringCellValue());
                    case "班级" -> student.setClassName(cell.getStringCellValue());
                    case "年龄" -> student.setAge(Integer.parseInt(cell.getStringCellValue()));
                    case "性别" -> student.setSex(cell.getStringCellValue());
                    case "生日" -> student.setBirthday(cell.getDateCellValue().toInstant()
                            .atZone(ZoneId.systemDefault()).toLocalDate());
                    case "政治面貌" -> student.setOutlook(cell.getStringCellValue());
                    case "籍贯" -> student.setNativePlace(cell.getStringCellValue());
                    case "家庭住址" -> student.setAddress(cell.getStringCellValue());
                    case "手机号" -> student.setPhone(cell.getStringCellValue());
                    case "邮箱" -> student.setEmail(cell.getStringCellValue());
                }
            }
            students.add(student);
        }
        workbook.close();
        return students;
    }

}
