package com.jjdx.studentmanage.DBMS.RandomData;

import java.util.Random;

public class Name {
    static Random random = new Random();

    static String[] firstNames = {"赵", "钱", "孙", "李", "周", "吴", "郑", "王",
            "冯", "陈", "褚", "卫", "蒋", "沈", "韩", "杨",
            "朱", "秦", "尤", "许", "何", "吕", "施", "张",
            "孔", "曹", "严", "华", "金", "魏", "陶", "姜",
            "戚", "谢", "邹", "喻", "柏", "水", "窦", "章",};
    static String[] middleNames = {"权", "羽", "月", "蒙", "朋", "略", "奎", "韵", "嘉", "方",
            "紫", "淼", "姗", "春", "炳", "纯", "麒", "彩", "洺", "顺",
            "凝", "宣", "辰", "诒", "家", "川", "恒", "谨", "可", "山",
            "璟", "棋", "丞", "雪", "晔", "献", "岩", "莲", "友", "振",
            "璐", "廷", "佩", "言", "学", "焘", "选", "昌", "俪", "洪",
            "章", "诗", "厚", "承", "雨", "儒", "珑", "芷", "歌", "旭"};
    static String[] lastNames = {"凯", "臣", "志", "妹", "珮", "金", "焕", "加", "卉", "力",
            "钢", "备", "寿", "灵", "基", "娆", "凤", "子", "任", "中",
            "磊", "升", "曦", "安", "光", "雯", "如", "风", "定", "西",
            "琳", "济", "向", "发", "湘", "英", "源", "星", "非", "迅",
            "雅", "洋", "秀", "捷", "丛", "心", "雁", "进", "科", "望"};

    public static String get() {
        return firstNames[random.nextInt(firstNames.length)] + middleNames[random.nextInt(middleNames.length)] + lastNames[random.nextInt(lastNames.length)];
    }
}
