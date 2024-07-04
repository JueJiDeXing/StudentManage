package com.jjdx.studentmanage.DBMS.RandomData;

import java.time.LocalDate;
import java.util.Random;
/**
 随机学生数据生成器 - 出生日期
 @ Author: 绝迹的星 <br>
 @ Time: 2024/7/3 <br>
 */
public class Birthday {
    static Random random = new Random();

    public static LocalDate get(Integer age) {
        return LocalDate.now().minusYears(age).minusDays(random.nextInt(-365 / 2, 365 / 2));
    }
}
