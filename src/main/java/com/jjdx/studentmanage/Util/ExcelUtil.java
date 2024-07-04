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
import org.jetbrains.annotations.NotNull;

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

    private ExcelUtil() {// 无实例
    }

    /**
     保存表信息到excel

     @param tableView    表
     @param file         保存位置
     @param skipFirstCOl 第一列是否需要导出
     */
    public static void save(TableView tableView, File file, boolean skipFirstCOl) throws IOException {
        Workbook workbook = new XSSFWorkbook();// 创建工作文件
        Sheet sheet = workbook.createSheet(file.getName());// 建表
        int offset = skipFirstCOl ? 1 : 0;// 是否跳过第一列
        ObservableList<TableColumn> columns = tableView.getColumns();// 从TableView中获取列
        // 创建标题行
        Row sheetHeadRow = sheet.createRow(0);
        for (int i = offset; i < columns.size(); i++) {// 创建标题行
            String columnName = columns.get(i).getText();// 列名
            sheetHeadRow.createCell(i - offset).setCellValue(columnName);
        }
        // 设置数据行
        int rowIndex = 1;// 行索引, 第一行为标题行
        for (Object tableRow : tableView.getItems()) {
            Row sheetDataRow = sheet.createRow(rowIndex++);
            for (int i = offset; i < columns.size(); i++) {
                String tableCellData = columns.get(i).getCellData(tableRow).toString();// 数据
                sheetDataRow.createCell(i - offset).setCellValue(tableCellData);// 设置单元格数据
            }
            try (FileOutputStream fos = new FileOutputStream(file)) {
                workbook.write(fos);
            }
        }
        workbook.close();
    }

    /**
     加载excel文件

     @param file excel文件, 格式若不正确则抛出异常
     */
    public static List<Student> loadInfo(@NotNull File file) throws IOException {
        if (!file.exists() || !file.getName().endsWith("xlsx")) {
            throw new IllegalArgumentException("文件格式不正确");
        }
        List<Student> students = new ArrayList<>();
        Workbook workbook = new XSSFWorkbook(new FileInputStream(file));
        Sheet sheet = workbook.getSheetAt(0);
        Row sheetHead = sheet.getRow(0);

        int numCols = sheetHead.getLastCellNum(); // 总行数
        int numRows = sheet.getLastRowNum();// 总列数

        for (int i = 1; i <= numRows; i++) {
            Row row = sheet.getRow(i);
            if (row == null) continue;
            Student student = createStudent(numCols, row, sheetHead);
            students.add(student);
        }
        workbook.close();
        return students;
    }

    /**
     从行中创建一个学生对象
     */
    private static Student createStudent(int numCols, Row row, Row headerRow) {
        Student student = new Student();
        for (int i = 0; i < numCols; i++) {
            Cell cell = row.getCell(i);
            if (cell == null) continue;
            String value = cell.getStringCellValue();
            switch (headerRow.getCell(i).getStringCellValue()) {
                // 注意事项: 配置文件中的字段 = excel表的列名 = case项
                case "学号" -> student.setId(value);
                case "姓名" -> student.setName(value);
                case "专业" -> student.setDept(value);
                case "班级" -> student.setClassName(value);
                case "年龄" -> student.setAge(Integer.parseInt(value));
                case "性别" -> student.setSex(value);
                case "籍贯" -> student.setNativePlace(value);
                case "邮箱" -> student.setEmail(value);
                case "出生日期" -> student.setBirthday(cell.getDateCellValue().toInstant()
                        .atZone(ZoneId.systemDefault()).toLocalDate());
                case "政治面貌" -> student.setOutlook(value);
                case "家庭住址" -> student.setAddress(value);
                case "电话号码" -> student.setPhone(value);
            }
        }
        return student;
    }

}
