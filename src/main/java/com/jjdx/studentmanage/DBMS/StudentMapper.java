package com.jjdx.studentmanage.DBMS;

import com.jjdx.studentmanage.pojo.SelectCondition;
import com.jjdx.studentmanage.pojo.Student;

import java.util.List;

/**
 学生信息数据交互

 @ Author: 绝迹的星 <br>
 @ Time: 2024/7/1 <br> */
public interface StudentMapper {

    List<Student> findAllStudent();

    List<Student> queryStudents(SelectCondition condition);

    void insert(Student student);

    void update(Student student);

    void delete(String id);
}

