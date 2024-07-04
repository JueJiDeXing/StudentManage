package com.jjdx.studentmanage.DBMS.RandomData;

public class ID {
    static int start = 2024070001;

    public static String get() {
        return String.valueOf(start++);
    }
}
