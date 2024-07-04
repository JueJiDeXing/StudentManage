package com.jjdx.studentmanage.DBMS.RandomData;

import java.util.Random;

public class Email {
    static Random random = new Random();

    public static String get(String id) {
        return id + "@jjdx.com";
    }

}
