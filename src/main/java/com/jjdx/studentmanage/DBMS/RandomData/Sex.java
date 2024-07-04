package com.jjdx.studentmanage.DBMS.RandomData;

import java.util.Random;
/**
 随机学生数据生成器 - 性别
 @ Author: 绝迹的星 <br>
 @ Time: 2024/7/3 <br>
 */
public class Sex {

    static Random random = new Random();


    public static String get() {
        return random.nextBoolean() ? "男" : "女";
    }
}
