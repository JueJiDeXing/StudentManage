package com.jjdx.studentmanage.DBMS.RandomData;

import java.util.Random;
/**
 随机学生数据生成器 - 籍贯
 @ Author: 绝迹的星 <br>
 @ Time: 2024/7/3 <br>
 */
public class NativePlace {
    static Random random = new Random();
    static String[] place = {"南昌", "景德镇", "萍乡", "九江", "新余", "鹰潭", "赣州", "吉安", "宜春", "抚州", "上饶"};

    public static String get() {
        return "江西" + place[random.nextInt(place.length)];
    }

}
