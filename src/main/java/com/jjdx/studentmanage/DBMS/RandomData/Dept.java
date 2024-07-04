package com.jjdx.studentmanage.DBMS.RandomData;

import java.util.Random;
/**
 随机学生数据生成器 - 专业
 @ Author: 绝迹的星 <br>
 @ Time: 2024/7/3 <br>
 */
public class Dept {
    static Random random = new Random();

    static String[] depts = {"计科", "大数据", "人工智能"};

    public static String get() {
        return depts[random.nextInt(depts.length)];
    }
}
