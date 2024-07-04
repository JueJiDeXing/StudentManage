package com.jjdx.studentmanage.DBMS;

import com.jjdx.studentmanage.Util.CheckUtil;
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
    public static SqlSession sqlSession;// 数据库会话
    public static StudentMapper studentMapper;// 数据库交互接口

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
    public static List<Student> queryStudents(SelectCondition condition) throws CheckUtil.DataIllegalException {
        String info = CheckUtil.conditionValidInfo(condition);
        if (info != null) {
            throw new CheckUtil.DataIllegalException(info);
        }
        return studentMapper.queryStudents(condition);
    }

    /**
     插入学生

     @param student 学生信息
     @return 插入结果, null为插入成功
     */
    public static String insert(Student student) {
        String valid = CheckUtil.studentInsertValid(student);
        if (valid != null) return valid;
        try {
            studentMapper.insert(student);
            commit();
            return null;
        } catch (Exception e) {
            return "插入失败";
        }
    }

    /**
     插入学生

     @param studentList 学生信息列表
     @return 插入结果[成功数量, 失败数量]
     */
    public static int[] insert(List<Student> studentList) {
        int no = 0;
        for (Student student : studentList) {
            String result = insert(student);
            if (result != null) no++;
        }
        return new int[]{studentList.size() - no, no};
    }


    /**
     删除学生信息
     */
    public static void delete(String id) {
        studentMapper.delete(id);
        commit();
    }
}
