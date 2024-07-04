package com.jjdx.studentmanage.DBMS.RandomData;

import com.jjdx.studentmanage.DBMS.StudentService;
import com.jjdx.studentmanage.pojo.Student;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DataCreator {
    static Random random = new Random();


    public static void main(String[] args) throws Exception {
        //生成一些随机学生信息
        //List<Student> studentList = create(100);
        //studentList.forEach(System.out::println);
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
