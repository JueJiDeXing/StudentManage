package com.jjdx.studentmanage.DBMS.RandomData;
/**
 随机学生数据生成器 - 学号
 @ Author: 绝迹的星 <br>
 @ Time: 2024/7/3 <br>
 */
public class ID {
    static int start = 2024070001;// 初始id

    public static String get() {
        return String.valueOf(start++);
    }
}
