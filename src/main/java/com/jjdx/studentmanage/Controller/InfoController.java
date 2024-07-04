package com.jjdx.studentmanage.Controller;

import com.jjdx.studentmanage.Util.AlertUtil;
import com.jjdx.studentmanage.Util.ExcelUtil;
import com.jjdx.studentmanage.pojo.InfoCounter;
import com.jjdx.studentmanage.pojo.InfoData;
import com.jjdx.studentmanage.pojo.Student;
import com.jjdx.studentmanage.pojo.Column;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.TableView;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.HashMap;
import java.util.List;

/**
 统计信息展示页面

 @ Author: 绝迹的星 <br>
 @ Time: 2024/7/1 <br> */
public class InfoController {

    public TableView sexTable;
    public TableView ageTable;
    public TableView classTable;
    public TableView outlookTable;
    public TableView deptTable;
    List<String> tableNames;// 表名 & 第一列列名
    List<TableView> tables;// 表对象
    List<InfoCounter.CntFun> cntFuns;// 计数方法

    public void initialize() {
        tableNames = List.of("性别", "年龄", "专业", "班级", "政治面貌");
        tables = List.of(sexTable, ageTable, classTable, outlookTable, deptTable);
        cntFuns = List.of(Student::getSex, s -> s.getAge().toString(), Student::getDept, Student::getClassName, Student::getOutlook);
    }

    /**
     显示表
     */
    public void show(List<Student> students) {
        // 统计信息
        HashMap<TableView, InfoCounter> map = doCount(students);
        // 展示
        for (var entry : map.entrySet()) {
            InfoCounter value = entry.getValue();
            draw(value.getTitle(), entry.getKey(), value.getCntMap());
        }
    }

    /**
     统计信息

     @param students 数据源
     @return 每个表对象各自进行计数, 映射为InfoCounter
     */
    @NotNull
    private HashMap<TableView, InfoCounter> doCount(List<Student> students) {
        HashMap<TableView, InfoCounter> map = new HashMap<>();
        for (int i = 0; i < tables.size(); i++) {
            map.put(tables.get(i), new InfoCounter(tableNames.get(i), cntFuns.get(i)));
        }
        for (var entry : map.entrySet()) {// 计数
            InfoCounter value = entry.getValue();
            for (Student student : students) put(value.getCntMap(), value.getCntMethod().cnt(student));
        }
        return map;
    }

    private void put(HashMap<String, Integer> map, String key) {
        map.put(key, map.getOrDefault(key, 0) + 1);
    }

    private void draw(String tableTitle, TableView table, HashMap<String, Integer> map) {
        // 添加列到表格
        List<Column> dataList = List.of(
                new Column(tableTitle, "kind", 120),
                new Column("人数", "cnt", 100),
                new Column("占比", "ratio", 100 ));
        dataList.forEach(data -> {
            if (data.isNeedDraw()) table.getColumns().add(data.toColumn());
        });
        // 计算总数
        int total = 0;
        for (var entry : map.entrySet()) total += entry.getValue();
        // 填充表格数据
        ObservableList<InfoData> data = FXCollections.observableArrayList();
        for (var entry : map.entrySet()) {
            int cnt = entry.getValue();
            String radio = cnt * 100 / total + "%";
            data.add(new InfoData(entry.getKey(), cnt, radio));
        }
        // 设置表格数据源
        table.setItems(data);
    }

    /**
     保存5张表信息
     */
    public void save(ActionEvent actionEvent) {
        System.out.println("?");
        File file = AlertUtil.alertDirectorySave();
        for (int i = 0; i < tables.size(); i++) {
            try {
                ExcelUtil.saveAsExcel(tables.get(i), new File(file.getPath() + "/" + tableNames.get(i)));
            } catch (Exception e) {
            }
        }
    }
}
