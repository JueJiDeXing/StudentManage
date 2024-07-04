package com.jjdx.studentmanage.DBMS.RandomData;

import java.util.Random;

public class Outlook {
    static Random random = new Random();
    static String[] outlooks = {"党员", "共青团员", "群众"};

    public static String get() {
        return outlooks[random.nextInt(outlooks.length)];
    }

}
