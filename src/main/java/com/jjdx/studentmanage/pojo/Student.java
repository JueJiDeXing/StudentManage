package com.jjdx.studentmanage.pojo;

import lombok.Data;

import java.time.LocalDate;

/**
 学生类

 @ Author: 绝迹的星 <br>
 @ Time: 2024/7/1 <br> */
@Data
public class Student {
    String id; // 学号
    String name; // 姓名
    String dept; // 专业
    String className; // 班级
    Integer age; // 年龄
    String sex; // 性别
    String nativePlace; // 籍贯
    String email; // 邮箱
    LocalDate birthday; // 出生年月
    String outlook; // 政治面貌
    String address; // 家庭住址
    String phone; // 电话号码

    public Student() {}

    public Student(String id, String name, String dept, String className, Integer age, String sex, String nativePlace, String email, LocalDate birthday, String outlook, String address, String phone) {
        this.id = id;
        this.name = name;
        this.dept = dept;
        this.className = className;
        this.age = age;
        this.sex = sex;
        this.birthday = birthday;
        this.outlook = outlook;
        this.nativePlace = nativePlace;
        this.address = address;
        this.phone = phone;
        this.email = email;
    }

}
