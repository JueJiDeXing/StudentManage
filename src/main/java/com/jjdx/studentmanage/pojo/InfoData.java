package com.jjdx.studentmanage.pojo;

import lombok.Data;

/**
 信息数据, 表示行信息

 @ Author: 绝迹的星 <br>
 @ Time: 2024/7/2 <br> */
@Data
public class InfoData {
    public String kind;// 该列的信息项, 如:男、女
    public int cnt;// 统计
    public String ratio;// 信息项在该类信息中的占比

    public InfoData(String key, int cnt, String ratio) {
        this.kind = key;
        this.cnt = cnt;
        this.ratio = ratio;
    }
}
