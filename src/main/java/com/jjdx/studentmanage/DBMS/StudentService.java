package com.jjdx.studentmanage.DBMS;

import com.jjdx.studentmanage.pojo.SelectCondition;
import com.jjdx.studentmanage.pojo.Student;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

/**
 学生服务类

 @ Author: 绝迹的星 <br>
 @ Time: 2024/7/1 <br> */
public class StudentService {
public static SqlSession sqlSession;
public static StudentMapper studentMapper;

static {
    try {
        Reader reader = Resources.getResourceAsReader("mybatis-config.xml");// 加载mybatis配置文件
        sqlSession = new SqlSessionFactoryBuilder().build(reader).openSession();// 开启数据库会话
        studentMapper = sqlSession.getMapper(StudentMapper.class);// 注册Mapper接口
    } catch (IOException e) {
        e.printStackTrace();
        throw new RuntimeException("未成功开启数据库会话", e);
    }
}
    /**
     事务提交
     */
    public static void commit() {
        sqlSession.commit();
    }

    /**
     更新学生信息
     */
    public static void update(Student student) {
        studentMapper.update(student);
        commit();
    }

    /**
     查询所有学生
     */
    public static List<Student> findAllStudent() {
        return studentMapper.findAllStudent();
    }

    /**
     查询学生
     */
    public static List<Student> queryStudents(SelectCondition condition) {
        return studentMapper.queryStudents(condition);
    }

    /**
     插入学生
     */
    public static boolean insert(Student student) {
        try {
            studentMapper.insert(student);
            commit();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     插入学生
     */
    public static int[] insert(List<Student> studentList) {
        int yes = 0, no = 0;
        for (Student student : studentList) {
            try {
                studentMapper.insert(student);
                commit();
                yes++;
            } catch (Exception ignored) {
                no++;
            }
        }
        return new int[]{yes, no};

    }


    /**
     删除学生信息
     */
    public static void delete(String id) {
        studentMapper.delete(id);
        commit();
    }
}
