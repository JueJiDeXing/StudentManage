package com.jjdx.studentmanage.Controller;

import com.jjdx.studentmanage.DBMS.StudentService;
import com.jjdx.studentmanage.Util.AlertUtil;
import com.jjdx.studentmanage.Util.CheckUtil;
import com.jjdx.studentmanage.pojo.SelectCondition;
import com.jjdx.studentmanage.pojo.Student;
import javafx.event.ActionEvent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.util.Duration;

import java.time.LocalDate;
import java.util.List;

import static com.jjdx.studentmanage.Util.StageUtil.addTip;


/**
 查询控制台

 @ Author: 绝迹的星 <br>
 @ Time: 2024/7/1 <br> */
public class SelectController {

    public TextField id;
    public TextField name;
    public TextField dept;
    public TextField className;
    public TextField age_start, age_end;
    public ChoiceBox<String> sex;
    public DatePicker birthday_start, birthday_end;
    public ChoiceBox<String> outlook;
    public TextField nativePlace;
    public TextField address;
    public TextField phone;
    public TextField email;

    public void initialize() {
        addTip(id, "10位数字");
        addTip(name, "2~4个中文字符");
        addTip(dept, "2~10个中文字符");
        addTip(className, "专业+xxx班");
        addTip(age_start, ">0");
        addTip(age_end, "<100");
        addTip(nativePlace, "2~10个中文字符");
        addTip(address, "2~50个合法字符");
        addTip(phone, "以1开始的11位数字");
        addTip(email, "用户名@域名");
    }

    /**
     按表单查询学生
     */
    public void query(ActionEvent actionEvent) {
        SelectCondition condition = getInfo();
        String valid = CheckUtil.conditionValidInfo(condition);
        if (valid != null) {
            AlertUtil.alertError(valid,"hideAlert.hideSelect");
            return;
        }
        List<Student> result = StudentService.queryStudents(condition);
        studentController.setData(result);
        AlertUtil.alertInfo("查询成功","hideAlert.hideSelect");
    }

    /**
     获取查询条件, 集成为SelectCondition返回
     */
    private SelectCondition getInfo() {
        String studentId = id.getText(), studentName = name.getText(),
                studentNativePlace = nativePlace.getText(), studentDept = dept.getText(),
                studentClass = className.getText(), studentSex = sex.getValue(),
                studentPhone = phone.getText(), studentEmail = email.getText(),
                studentOutlook = outlook.getValue(), studentAddress = address.getText();

        LocalDate studentStartBirthday = birthday_start.getValue(), studentEndBirthday = birthday_end.getValue();

        Integer studentStartAge = null, studentEndAge = null;
        String age = age_start.getText();
        if (CheckUtil.isValidAge(age)) studentStartAge = Integer.parseInt(age);
        age = age_end.getText();
        if (CheckUtil.isValidAge(age)) studentEndAge = Integer.parseInt(age);

        return new SelectCondition(studentId, studentName, studentDept, studentClass, studentStartAge, studentEndAge, studentSex,
                studentStartBirthday, studentEndBirthday, studentOutlook, studentNativePlace,
                studentAddress, studentPhone, studentEmail);
    }


    /**
     清空查询条件
     */
    public void clear(ActionEvent actionEvent) {
        id.clear();
        name.clear();
        nativePlace.clear();
        birthday_start.setValue(null);
        birthday_end.setValue(null);
        age_start.clear();
        age_end.clear();
        dept.clear();
        className.clear();
        sex.setValue(null);
    }

    StudentController studentController;

    /**
     数据回调(查询的学生信息需要显示到StudentController里)
     */
    public void setDataCallback(StudentController studentController) {
        this.studentController = studentController;
    }
}
