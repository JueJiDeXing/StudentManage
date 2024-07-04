package com.jjdx.studentmanage.Util;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.util.HashMap;

/**
 Yaml配置文件的读取器, (需要在程序启动前加载信息)

 @ Author: 绝迹的星 <br>
 @ Time: 2024/5/7 <br> */
public class YamlUtil {
    private static HashMap<String, Object> yMap = new HashMap<>();//从配置文件读取的信息
    private static String configFile = "app-config.yml";// 配置文件的文件名
    private static final Yaml yaml;

    static {
        DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);// 块式写入
        options.setPrettyFlow(true);
        yaml = new Yaml(options);
    }

    private YamlUtil() {// 无实例
    }

    /**
     项目启动, 读取配置文件, 若无法读取,则强制停止程序运行
     */
    public static void start() {
        yMap = reLoad();
        if (yMap != null) return;
        System.out.println("配置文件加载失败, 尝试读取备份");
        configFile = "app-config-备份.yml";
        yMap = reLoad();
        if (yMap == null) {
            throw new NullPointerException("备份文件加载失败, 请检查文件");
        }
        try {
            FileWriter output = new FileWriter("app-config.yml");
            yaml.dump(yMap, output);
            configFile = "app-config.yml";
        } catch (Exception e) {
            System.out.println("备份文件读回失败 -- " + e.getMessage());
        }

    }

    /**
     重新加载配置文件, 由于实现实时更新的效果, 所以每次都需要重新读入配置文件

     @return 配置文件中的信息
     */
    private static HashMap<String, Object> reLoad() {
        try {
            return yaml.load(new FileInputStream(configFile));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     分隔获取最后一个HashMap
     - 返回的HashMap是可修改的, 不允许外界访问该方法
     @param split 键名
     @return 最后一个HashMap, 若不存在则返回null
     */
    private static HashMap<String, Object> split(String[] split) {
        HashMap<String, Object> m = new HashMap<>(yMap);
        int n = split.length;
        for (int i = 0; i < n - 1; i++) {
            if (!m.containsKey(split[i])) return null;
            Object o = m.get(split[i]);
            if (!(o instanceof HashMap)) return null;
            m = (HashMap<String, Object>) o;
        }
        return m;
    }

    /**
     获取配置文件的值

     @param name 以"."分隔的键名
     @return 不存在键时返回默认值
     */
    public static <T> T get(String name, T defaultValue) {
        reLoad();
        String[] split = name.split("\\.");
        HashMap<String, Object> lastMap = split(split);
        if (lastMap == null) return defaultValue;
        Object value = lastMap.get(split[split.length - 1]);
        if (!ClassUtil.isInstanceOf(value, defaultValue.getClass())) {
            return defaultValue;
        }
        return (T) value;
    }


    /**
     获取配置文件的int值

     @param name 以"."分隔的键名
     @return 值, 若不存在则返回默认值0
     */
    public static int getInt(String name) {
        reLoad();
        return getInt(name, 0);
    }
    /**
     获取配置文件的int值
     @param name 以"."分隔的键名
     @param defaultVal 不存在则返回默认值
     */
    public static int getInt(String name, int defaultVal) {
        reLoad();
        String[] split = name.split("\\.");
        HashMap<String, Object> lastMap = split(split);
        if (lastMap == null) return defaultVal;
        Object value = lastMap.get(split[split.length - 1]);
        if (!ClassUtil.isInstanceOf(value, Integer.class)) {
            return defaultVal;
        }
        return (int) value;

    }
    /**
     获取配置文件的String值
     @param name 以"."分隔的键名
     @return 不存在则返回默认值""
     */
    public static String getString(String name) {
        return getString(name, "");
    }
    /**
     获取配置文件的String值
     @param name 以"."分隔的键名
     @param defaultVal 不存在则返回默认值
     */
    public static String getString(String name, String defaultVal) {
        reLoad();
        String[] split = name.split("\\.");
        HashMap<String, Object> lastMap = split(split);
        if (lastMap == null) return defaultVal;
        Object value = lastMap.get(split[split.length - 1]);
        if (!ClassUtil.isInstanceOf(value, String.class)) {
            return defaultVal;
        }
        return (String) value;
    }

    /**
     获取配置文件的boolean值
     @param name 以"."分隔的键名
     @return 不存在则返回false
     */
    public static boolean getBoolean(String name) {
        reLoad();
        String[] split = name.split("\\.");
        HashMap<String, Object> lastMap = split(split);
        if (lastMap == null) return false;
        Object value = lastMap.get(split[split.length - 1]);
        if (!ClassUtil.isInstanceOf(value, Boolean.class)) {
            return false;
        }
        return (boolean) value;
    }

    /**
     更改配置文件

     @param name  以"."分隔的键名, 必须存在才能更改
     @param value 新值
     */
    public static void change(String name, Object value) {
        reLoad();
        //获取最后一个map
        String[] split = name.split("\\.");
        HashMap<String, Object> lastMap = split(split);
        if (lastMap == null) return;
        //设置值
        lastMap.put(split[split.length - 1], value);
        try {
            FileWriter output = new FileWriter(configFile);
            yaml.dump(yMap, output);
        } catch (Exception e) {
            System.out.println("配置文件更改失败 -- " + e.getMessage());
        }
    }

    /**
     获取最后一个层级对应的HashMap

     @param name 以"."分隔的键名, 应当以"."或".null"结尾,表示层级终止
     @return 只读数据
     */
    public static HashMap<String, Object> getLastMap(String name) {
        reLoad();
        if (!(name.endsWith(".") || name.endsWith(".null"))) name += ".null";
        String[] split = name.split("\\.");
        HashMap<String, Object> lastMap = split(split);
        if (lastMap == null) return null;
        return new HashMap<>(lastMap);
    }

}
