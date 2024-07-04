package com.jjdx.studentmanage.DBMS.RandomData;

import java.util.Random;
/**
 随机学生数据生成器 - 班级
 @ Author: 绝迹的星 <br>
 @ Time: 2024/7/3 <br>
 */
public class ClassName {
    static Random random = new Random();

    public static String get(String dept) {
        return dept + "2" + random.nextInt(1, 4) + random.nextInt(1, 5) + "班";
    }
}
