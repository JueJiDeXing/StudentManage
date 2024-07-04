package com.jjdx.studentmanage.Util;

/**
 类工具

 @ Author: 绝迹的星 <br>
 @ Time: 2024/5/7 <br> */
public class ClassUtil {
    public static <T, U> boolean isInstanceOf(T obj, Class<U> clazz) {
        return clazz.isInstance(obj);//null->false
    }
}
