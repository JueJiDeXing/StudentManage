package com.jjdx.studentmanage.Controller;

import com.jjdx.studentmanage.DBMS.StudentService;
import com.jjdx.studentmanage.Util.AlertUtil;
import com.jjdx.studentmanage.Util.CheckUtil;
import com.jjdx.studentmanage.pojo.Student;
import javafx.event.ActionEvent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.time.LocalDate;

import static com.jjdx.studentmanage.Util.StageUtil.addTip;

/**
 更新页面

 @ Author: 绝迹的星 <br>
 @ Time: 2024/7/2 <br> */
public class UpdateController {
    public TextField id;
    public TextField name;
    public TextField dept;
    public TextField className;
    public TextField age;
    public DatePicker birthday;
    public ChoiceBox<String> sex;
    public TextField nativePlace;
    public ComboBox outlook;
    public TextField address;
    public TextField phone;
    public TextField email;
    public AnchorPane root;

    public void initialize() {
        addTip(id, "10位数字");
        addTip(name, "2~4个中文字符");
        addTip(dept, "2~10个中文字符");
        addTip(className, "专业+xxx班");
        addTip(age, "0~100");
        addTip(nativePlace, "2~10个中文字符");
        addTip(address, "2~50个合法字符");
        addTip(phone, "以1开始的11位数字");
        addTip(email, "用户名@域名");
    }

    /**
     按学号更新学生记录
     */
    public void update(ActionEvent actionEvent) {
        Student student = getInfo();
        String valid = CheckUtil.studentUpdateValid(student);
        if (valid != null) {
            AlertUtil.alertError(valid, "hideAlert.hideUpdate");
            return;
        }
        StudentService.update(student);
        AlertUtil.alertInfo("更新成功","hideAlert.hideUpdate");
    }


    /**
     获取表单信息, 生成Student对象
     */
    private Student getInfo() {
        String studentId = id.getText(),
                studentName = name.getText(),
                studentNativePlace = nativePlace.getText(),
                studentAddress = address.getText(),
                studentPhone = phone.getText(),
                studentEmail = email.getText(),
                studentDept = dept.getText(),
                studentClassName = className.getText(),
                studentSex = null, studentOutlook = null;
        Integer studentAge = null;
        LocalDate studentBirthday = birthday.getValue();

        String ageText = age.getText();
        if (CheckUtil.notNull(ageText)) studentAge = Integer.parseInt(ageText);

        Object value = sex.getValue();
        if (value != null) studentSex = value.toString();
        value = outlook.getValue();
        if (value != null) studentOutlook = value.toString();

        return new Student(studentId, studentName, studentDept, studentClassName, studentAge, studentSex,
                studentAddress, studentOutlook, studentBirthday, studentNativePlace, studentPhone, studentEmail);
    }

    /**
     清空表单
     */
    public void clear(ActionEvent actionEvent) {
        id.clear();
        name.clear();
        dept.clear();
        className.clear();
        age.clear();
        birthday.setValue(null);
        sex.setValue(null);
        nativePlace.clear();
        address.clear();
        phone.clear();
        email.clear();
        outlook.setValue(null);
    }
}
