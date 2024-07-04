package com.jjdx.studentmanage.DBMS.RandomData;

import com.jjdx.studentmanage.pojo.Student;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 随机学生数据生成器
 @ Author: 绝迹的星 <br>
 @ Time: 2024/7/3 <br>
 */
public class DataCreator {

    public static void main(String[] args) {
        //生成一些随机学生信息
        //List<Student> studentList = create(1000);
        //StudentService.insert(studentList);
    }

    private static List<Student> create(int n) {
        List<Student> studentList = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            String id = ID.get();
            String name = Name.get();
            String dept = Dept.get();
            String className = ClassName.get(dept);
            Integer age = Age.get();
            String sex = Sex.get();
            String nativePlace = NativePlace.get();
            String email = Email.get(id);
            LocalDate birthday = Birthday.get(age);
            String outlook = Outlook.get();
            String address = Address.get();
            String phone = Phone.get();
            Student student = new Student(id, name, dept, className, age, sex, nativePlace, email, birthday, outlook, address, phone);
            studentList.add(student);
        }
        return studentList;
    }

}
