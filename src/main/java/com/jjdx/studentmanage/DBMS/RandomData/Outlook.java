package com.jjdx.studentmanage.DBMS.RandomData;

import java.util.Random;
/**
 随机学生数据生成器 - 政治面貌
 @ Author: 绝迹的星 <br>
 @ Time: 2024/7/3 <br>
 */
public class Outlook {
    static Random random = new Random();
    static String[] outlooks = {"党员", "共青团员", "群众"};

    public static String get() {
        return outlooks[random.nextInt(outlooks.length)];
    }

}
