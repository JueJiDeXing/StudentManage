package com.jjdx.studentmanage.DBMS.RandomData;

import java.util.Random;

public class Address {
    static Random random = new Random();

    public static String get() {
        return "南昌大学" + random.nextInt(1, 28) + "栋";
    }

}
