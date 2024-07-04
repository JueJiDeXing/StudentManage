package com.jjdx.studentmanage.DBMS.RandomData;

import java.util.Random;

public class Age {
    static Random random = new Random();


    public static Integer get() {
        return random.nextInt(18, 22) ;
    }


}
