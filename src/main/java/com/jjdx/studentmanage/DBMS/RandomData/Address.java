package com.jjdx.studentmanage.DBMS.RandomData;

import java.util.Random;
/**
 随机学生数据生成器 - 家庭地址
 @ Author: 绝迹的星 <br>
 @ Time: 2024/7/3 <br>
 */
public class Address {
    static Random random = new Random();

    public static String get() {
        return "南昌大学" + random.nextInt(1, 28) + "栋";
    }

}
