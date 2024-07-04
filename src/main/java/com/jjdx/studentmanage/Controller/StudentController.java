package com.jjdx.studentmanage.Controller;

import com.jjdx.studentmanage.DBMS.StudentService;
import com.jjdx.studentmanage.Util.*;
import com.jjdx.studentmanage.pojo.Column;
import com.jjdx.studentmanage.pojo.Student;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 主页面学生信息管理

 @ Author: 绝迹的星 <br>
 @ Time: 2024/7/1 <br> */
public class StudentController {
    public CheckBox checkId;
    public CheckBox checkName;
    public CheckBox checkDept;
    public CheckBox checkClassName;
    public CheckBox checkAge;
    public CheckBox checkSex;
    public CheckBox checkNativePlace;
    public CheckBox checkEmail;
    public CheckBox checkBirthday;
    public CheckBox checkOutlook;
    public CheckBox checkAddress;
    public CheckBox checkPhone;

    public TableView tableView;
    public AnchorPane root;


    List<Student> studentData;// 学生数据


    /**
     初始化
     */
    @FXML
    public void initialize() {
        setData(StudentService.findAllStudent()); // 设置表格初始值
        StageUtil.initFocus(root, Button.class, CheckBox.class); // 设置焦点
    }

    /**
     获取列数据
     */
    private List<Column> getColumnList() {
        List<Column> dataList = new ArrayList<>();
        List<CheckBox> checkBoxes = List.of(checkId, checkName, checkDept, checkClassName, checkAge, checkSex,
                checkNativePlace, checkBirthday, checkOutlook, checkAddress, checkPhone, checkEmail);// 勾选框
        HashMap<String, Object> fieldMap = YamlUtil.getLastMap("tableView.columns");// 字段名 -> 数据对
        for (CheckBox checkBox : checkBoxes) {
            String name = checkBox.getText();// 列字段名
            HashMap<String, Object> map = (HashMap<String, Object>) fieldMap.get(name);// 获取该字段的数据对
            String attribute = (String) map.get("attribute");// 对应的属性
            Integer prefWidth = (Integer) map.get("prefWidth");// 列宽
            Integer priority = (Integer) map.get("priority");// 优先级
            boolean selected = checkBox.isSelected();// 是否绘制
            dataList.add(new Column(name, attribute, prefWidth, priority, selected));
        }
        return dataList;
    }


    /**
     设置表格数据

     @param studentData 要展示的学生数据
     */
    public void setData(List<Student> studentData) {
        // 清空数据
        tableView.getColumns().clear();
        tableView.getItems().clear();
        // 获取数据
        this.studentData = studentData;//studentData维护当前展示的学生数据
        List<Column> columnData = getColumnList();// 获取列数据
        // 创建列并设置列名
        // 序号列
        TableColumn<Student, Integer> indexColumn = new TableColumn<>("");
        indexColumn.setMinWidth(60);
        indexColumn.setCellValueFactory(param -> {
            int value = tableView.getItems().indexOf(param.getValue()) + 1;// 序号
            return new SimpleIntegerProperty(value).asObject();
        });
        tableView.getColumns().add(indexColumn);
        // 数据列
        for (Column data : columnData) {
            if (!data.needDraw) continue;// 不需要绘制
            TableColumn<Student, Integer> c = new TableColumn<>(data.title);// 列字段名
            c.setCellValueFactory(new PropertyValueFactory<>(data.attribute));// 列属性名
            c.setPrefWidth(data.prefWidth);// 列宽
            tableView.getColumns().add(c);
        }
        // 填充数据到表格视图
        tableView.getItems().addAll(studentData);
    }

    /**
     列选择触发, 重设表格列
     */
    public void chooseColumn(ActionEvent actionEvent) {
        setData(studentData);
    }

    /**
     刷新记录
     */
    public void refresh(ActionEvent actionEvent) {
        setData(StudentService.findAllStudent());
    }

    // -------------------------新页面--------------------------------
    Stage selectStage = new Stage();
    Stage insertStage = new Stage();
    Stage updatepStage = new Stage();
    Stage settingStage = new Stage();

    /**
     弹出查询控制台, 选定查询条件
     */
    public void select(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = StageUtil.loadStage(getClass(), "select.fxml", selectStage,
                "查询控制台", "/com/jjdx/studentmanage/images/Frame/select.png");
        //设置回调, select出的数据需要回到主界面
        if (loader != null) ((SelectController) loader.getController()).setDataCallback(this);
    }

    /**
     弹出表单, 录入学生信息
     */
    public void insert(ActionEvent actionEvent) throws IOException {
        StageUtil.loadStage(getClass(), "insert.fxml", insertStage, "录入", "/com/jjdx/studentmanage/images/Frame/insert.png");
    }

    /**
     弹出更新页面, 更新学生信息
     */
    public void update(ActionEvent actionEvent) throws IOException {
        StageUtil.loadStage(getClass(), "update.fxml", updatepStage, "更新", "/com/jjdx/studentmanage/images/Frame/update.png");
    }

    /**
     删除页面, 只有按学号删除以单个输入框的形式弹出
     */
    public void delete(ActionEvent actionEvent) {
        // 弹出输入框
        String id = AlertUtil.alertInput("请输入要删除的学生学号");
        // 检查输入学号合法性
        if (!CheckUtil.isValidId(id)) {
            AlertUtil.alertWarning("学号不合法");
            return;
        }
        if (!CheckUtil.isExistId(id)) {
            AlertUtil.alertWarning("学号不存在");
            return;
        }
        // 由于是删除操作, 所有弹出确认框
        ButtonType buttonType = AlertUtil.alertChoose("确认删除学生(" + id + ")吗?").getResult();
        if (buttonType != ButtonType.OK) return;
        try {
            StudentService.delete(id);
            AlertUtil.alertInfo("删除成功");
        } catch (Exception e) {
            AlertUtil.alertInfo("删除失败");
        }
    }

    /**
     统计信息页面
     */
    public void info(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = StageUtil.loadStage(getClass(), "info.fxml", new Stage(), "信息统计", "/com/jjdx/studentmanage/images/Frame/info.png");
        assert loader != null;// new Stage().isShowing() = false
        ((InfoController) loader.getController()).show(studentData);
    }

    /**
     设置页面
     */
    public void setting(ActionEvent actionEvent) throws IOException {
        StageUtil.loadStage(getClass(), "setting.fxml", settingStage, "设置", "/com/jjdx/studentmanage/images/Frame/setting.png");
    }

    // ----------------------------------文件交互-----------------------------------------

    /**
     将TableView导出到excel
     */
    public void convertToExcel(ActionEvent actionEvent) {
        File file = AlertUtil.alertDirectorySave();
        if (file == null) return;
        try {
            File savePos = new File(file.getPath() + "/" + "学生信息表.xlsx");
            ExcelUtil.save(tableView, savePos, true);
            AlertUtil.alertInfo("保存成功");
        } catch (Exception e) {
            AlertUtil.alertError("保存失败");
        }
    }


    /**
     批量导入学生数据
     */
    public void insertByExcel(ActionEvent actionEvent) {
        File file = AlertUtil.alertExcelScan();
        if (file == null) return;
        if (!file.getName().endsWith(".xlsx")) {
            AlertUtil.alertError("不支持的文件类型");
            return;
        }
        try {
            List<Student> studentList = ExcelUtil.loadInfo(file);
            int[] cnt = StudentService.insert(studentList);
            AlertUtil.alertInfo("成功插入了" + cnt[0] + "条数据, 失败" + cnt[1] + "条");
        } catch (Exception e) {
            AlertUtil.alertError("导入失败");
        }
    }

}
