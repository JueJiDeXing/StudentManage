package com.jjdx.studentmanage.DBMS.RandomData;

import java.util.Random;

public class ClassName {
    static Random random = new Random();


    public static String get(String dept) {
        return dept + "2" + random.nextInt(1, 5) + random.nextInt(1, 10) + "班";
    }
}
