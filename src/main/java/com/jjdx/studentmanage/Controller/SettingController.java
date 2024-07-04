package com.jjdx.studentmanage.Controller;

import com.jjdx.studentmanage.Util.AlertUtil;
import com.jjdx.studentmanage.Util.YamlUtil;
import com.jjdx.studentmanage.pojo.Column;
import javafx.event.Event;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.*;

import static com.jjdx.studentmanage.Util.StageUtil.createTransparentButton;

/**
 设置页面

 @ Author: 绝迹的星 <br>
 @ Time: 2024/7/3 <br> */
public class SettingController {

    public CheckBox hideSelect;
    public CheckBox hideInsert;
    public CheckBox hideUpdate;
    public ChoiceBox<String> selectLevel;
    public TextField id;
    public VBox widthControllerBox;
    Image yes, no;

    {
        yes = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/jjdx/studentmanage/images/Content/commit.png")));
        no = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/jjdx/studentmanage/images/Content/cancel.png")));
    }

    public void initialize() {
        // 弹窗控制
        hideSelect.setSelected(YamlUtil.getBoolean("hideAlert.hideSelect"));
        hideInsert.setSelected(YamlUtil.getBoolean("hideAlert.hideInsert"));
        hideUpdate.setSelected(YamlUtil.getBoolean("hideAlert.hideUpdate"));
        selectLevel.setValue(YamlUtil.getString("hideAlert.level"));

        // 宽度控制
        List<Column> columns = new ArrayList<>();
        HashMap<String, Object> fieldMap = YamlUtil.getLastMap("tableView.columns");// 字段名 -> 数据对
        if (fieldMap == null) {
            throw new RuntimeException(" tableView.columns - 配置错误 ");
        }
        fieldMap.forEach((key, value) -> {
            HashMap<String, Object> map = (HashMap<String, Object>) value;
            Column column = new Column(key, (String) map.get("attribute"), (Integer) map.get("prefWidth"), (Integer) map.get("priority"));
            columns.add(column);
        });
        columns.sort(Comparator.comparingInt(o -> o.priority));
        for (Column column : columns) {
            addLine(column);
        }
    }

    private void addLine(Column column) {
        HBox hBox = new HBox();
        hBox.setSpacing(5);
        hBox.setPrefWidth(400);
        String name = column.getTitle();// 字段名
        //String attribute = column.getAttribute();// 属性名
        Label label = new Label(name + ":");
        label.setPrefWidth(80);
        TextField textField = new TextField(column.getPrefWidth() + ""); // 学号: 100
        textField.setPrefWidth(80);

        Button commit = createTransparentButton(20, 20, yes, "提交"),
                cancel = createTransparentButton(20, 20, no, "取消");
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            commit.setOpacity(1);
            cancel.setOpacity(1);
        });
        commit.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
            if (commit.getOpacity() == 0) return;
            commit(name + ".prefWidth", textField);
            commit.setOpacity(0);
            cancel.setOpacity(0);
        });
        cancel.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
            if (cancel.getOpacity() == 0) return;
            cancel(name + ".prefWidth", textField);
            commit.setOpacity(0);
            cancel.setOpacity(0);
        });
        hBox.getChildren().addAll(label, textField, commit, cancel);
        widthControllerBox.getChildren().add(hBox);
    }


    public void selectChange(MouseEvent actionEvent) {
        YamlUtil.change("hideAlert.hideSelect", hideSelect.isSelected());
    }

    public void insertChange(MouseEvent actionEvent) {
        YamlUtil.change("hideAlert.hideInsert", hideInsert.isSelected());
    }

    public void updateChange(MouseEvent actionEvent) {
        YamlUtil.change("hideAlert.hideUpdate", hideUpdate.isSelected());
    }

    public void levelChange(Event actionEvent) {
        YamlUtil.change("hideAlert.level", selectLevel.getValue());
    }

    /**
     将textField的信息提交到配置文件

     @param key       配置键名
     @param textField 要提交的TextField对象
     */
    public void commit(String key, TextField textField) {
        int width;
        try {
            width = Integer.parseInt(textField.getText().trim());
        } catch (Exception e) {
            AlertUtil.alertWarning("请输入有效值");
            return;
        }
        YamlUtil.change("tableView.columns." + key, width);
        AlertUtil.alertInfo("设置成功");
    }

    /**
     将配置项信息还原到textField

     @param key       配置键名
     @param textField 要提交的TextField对象
     */
    public void cancel(String key, TextField textField) {
        int width = YamlUtil.getInt("tableView.columns." + key);
        textField.setText(width + "");
    }
}
