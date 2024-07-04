package com.jjdx.studentmanage.Controller;

import com.jjdx.studentmanage.DBMS.StudentService;
import com.jjdx.studentmanage.Util.AlertUtil;
import com.jjdx.studentmanage.Util.CheckUtil;
import com.jjdx.studentmanage.pojo.Student;
import javafx.event.ActionEvent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.time.LocalDate;

import static com.jjdx.studentmanage.Util.StageUtil.addTip;

/**
 插入学生信息表单

 @ Author: 绝迹的星 <br>
 @ Time: 2024/7/1 <br> */
public class InsertController {
    public TextField id;
    public TextField name;
    public TextField dept;
    public TextField className;
    public TextField age;
    public ChoiceBox<Integer> sex;
    public DatePicker birthday;
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
        addTip(age, "0~100");
        addTip(nativePlace, "2~10个中文字符");
        addTip(address, "2~50个合法字符");
        addTip(phone, "以1开始的11位数字");
        addTip(email, "合法的邮箱地址");
    }


    /**
     插入学生记录
     */
    public void insert(ActionEvent actionEvent) {
        Student student = getInfo();
        String valid = CheckUtil.studentInsertValid(student);
        if (valid != null) {
          AlertUtil.alertError(valid,"hideAlert.hideInsert");
            return;
        }
        StudentService.insert(student);
     AlertUtil.alertInfo("插入成功","hideAlert.hideInsert");
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
                studentOutlook = outlook.getValue(),
                studentSex = null;
        Integer studentAge = null;
        LocalDate studentBirthday = birthday.getValue();
        String ageText = age.getText();
        if (CheckUtil.notNull(ageText)) studentAge = Integer.parseInt(ageText);
        if (CheckUtil.notNull(sex.getValue())) studentSex = sex.getValue().toString();
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
