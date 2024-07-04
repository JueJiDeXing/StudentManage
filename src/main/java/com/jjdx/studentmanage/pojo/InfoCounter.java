package com.jjdx.studentmanage.pojo;

import lombok.Data;

import java.util.HashMap;

/**
 信息数据, 表示列信息, 用于统计

 @ Author: 绝迹的星 <br>
 @ Time: 2024/7/2 <br> */
@Data
public class InfoCounter {
    String title;// 列名, 如:性别
    CntFun cntMethod;// 统计方法
    HashMap<String, Integer> cntMap = new HashMap<>();// 统计表

    /**
     统计方法接口
     */
    public interface CntFun {
        String cnt(Student s);
    }

    public InfoCounter(String title, CntFun f) {
        this.title = title;
        this.cntMethod = f;
    }
}
