package com.jjdx.studentmanage.pojo;

import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.Data;

/**
 表示表的绘制信息

 @ Author: 绝迹的星 <br>
 @ Time: 2024/7/1 <br> */
@Data
public class Column {
    /**
     数据列字段名
     */
    public String title;
    /**
     数据列对应的类属性名
     */
    public String attribute;
    /**
     绘制优先级
     */
    public Integer priority;
    /**
     列宽
     */
    public Integer prefWidth;
    /**
     是否绘制该列
     */
    public boolean needDraw = true;

    public Column(String title, String attribute, Integer prefWidth, Integer priority, boolean needDraw) {
        this.title = title;
        this.attribute = attribute;
        this.needDraw = needDraw;
        this.priority = priority;
        this.prefWidth = prefWidth;
    }

    public Column(String title, String attribute, Integer prefWidth, Integer priority) {
        this.title = title;
        this.attribute = attribute;
        this.priority = priority;
        this.prefWidth = prefWidth;
    }

    public Column(String title, String attribute, Integer prefWidth) {
        this.title = title;
        this.attribute = attribute;
        this.prefWidth = prefWidth;
    }

    public TableColumn toColumn() {
        TableColumn tableColumn = new TableColumn(title);
        tableColumn.setPrefWidth(prefWidth);
        tableColumn.setCellValueFactory(new PropertyValueFactory<>(attribute));
        return tableColumn;
    }
}
