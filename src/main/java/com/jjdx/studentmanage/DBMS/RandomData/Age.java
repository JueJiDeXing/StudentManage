package com.jjdx.studentmanage.DBMS.RandomData;

import java.util.Random;
/**
 随机学生数据生成器 - 年龄
 @ Author: 绝迹的星 <br>
 @ Time: 2024/7/3 <br>
 */
public class Age {
    static Random random = new Random();


    public static Integer get() {
        return random.nextInt(18, 22) ;
    }


}
