package com.jjdx.studentmanage.DBMS.RandomData;

import java.util.Random;
/**
 随机学生数据生成器 - 电话号码
 @ Author: 绝迹的星 <br>
 @ Time: 2024/7/3 <br>
 */
public class Phone {
    static Random random = new Random();

    public static String get() {
        StringBuilder s = new StringBuilder("1");
        for (int i = 0; i < 10; i++) {
            s.append(random.nextInt(10));
        }
        return s.toString();
    }

}
