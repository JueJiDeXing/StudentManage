package com.jjdx.studentmanage.DBMS.RandomData;

import java.util.Random;

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
