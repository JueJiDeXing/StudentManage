package com.jjdx.studentmanage.pojo;

import lombok.Data;

import java.time.LocalDate;

/**
 查询条件

 @ Author: 绝迹的星 <br>
 @ Time: 2024/7/1 <br> */
@Data
public class SelectCondition {
    String id;
    String name;
    String dept;
    String className;
    Integer startAge, endAge;
    String sex;
    LocalDate startBirthday, endBirthday;
    String outlook;
    String nativePlace;
    String address;
    String phone;
    String email;

    public SelectCondition(String id, String name, String dept, String className,
                           Integer startAge, Integer endAge, String sex, LocalDate startBirthday, LocalDate endBirthday, String outlook, String nativePlace,
                           String address, String phone, String email) {
        this.id = id;
        this.name = name;
        this.dept = dept;
        this.className = className;
        this.sex = sex;
        this.nativePlace = nativePlace;
        this.startBirthday = startBirthday;
        this.endBirthday = endBirthday;
        this.startAge = startAge;
        this.endAge = endAge;
        this.outlook = outlook;
        this.phone = phone;
        this.email = email;
        this.address = address;
    }


}
