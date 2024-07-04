package com.jjdx.studentmanage.DBMS.RandomData;

import java.util.Random;
/**
 随机学生数据生成器 - 邮箱
 @ Author: 绝迹的星 <br>
 @ Time: 2024/7/3 <br>
 */
public class Email {
    static Random random = new Random();

    public static String get(String id) {
        return id + "@jjdx.com";
    }

}
