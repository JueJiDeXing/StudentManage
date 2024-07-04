package com.jjdx.studentmanage.DBMS.RandomData;

import java.time.LocalDate;
import java.util.Random;

public class Birthday {
    static Random random = new Random();

    public static LocalDate get(Integer age) {
        return LocalDate.now().minusYears(age).minusDays(random.nextInt(-365 / 2, 365 / 2));
    }
}
