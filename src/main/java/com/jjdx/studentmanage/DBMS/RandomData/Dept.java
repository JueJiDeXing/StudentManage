package com.jjdx.studentmanage.DBMS.RandomData;

import java.util.Random;

public class Dept {
    static Random random = new Random();

    static String[] depts = {"计科", "大数据", "人工智能"};

    public static String get() {
        return depts[random.nextInt(depts.length)];
    }
}
