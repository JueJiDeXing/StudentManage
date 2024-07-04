package com.jjdx.studentmanage.DBMS.RandomData;

import java.util.Random;

public class Sex {

    static Random random = new Random();


    public static String get() {
        return random.nextBoolean() ? "男" : "女";
    }
}
