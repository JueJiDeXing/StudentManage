package com.jjdx.studentmanage.Util;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.function.BiFunction;
import java.util.logging.Level;

/**
 弹窗工具

 @ Author: 绝迹的星 <br>
 @ Time: 2024/7/2 <br> */
public class AlertUtil {
    static HashMap<String, Image> imageMap = new HashMap<>();

    static {
        List<String> imageNames = List.of("yes", "warning", "no", "graphic", "input");
        String pre = "/com/jjdx/studentmanage/images/Alert/";
        for (String imageName : imageNames) {
            try {
                URL url = AlertUtil.class.getResource(pre + imageName + ".png");
                Image image = new Image(String.valueOf(url));
                imageMap.put(imageName, image);
            } catch (Exception ignored) {
            }
        }
    }

/**
 显示弹窗
 - 底层代码, 弹窗从此处统一生成

 @param type    弹窗类型
 @param message 弹窗内容
 @param icon    图标
 */
public static Alert alert(Alert.AlertType type, String message, Image icon) {
    Alert alert = new Alert(type, message, ButtonType.OK);
    alert.setHeaderText("操作提示");
    ImageView imageView = new ImageView(imageMap.get("graphic"));
    imageView.setFitWidth(50);
    imageView.setFitHeight(50);
    alert.setGraphic(imageView);
    DialogPane dialogPane = alert.getDialogPane();
    dialogPane.setStyle("-fx-font-size: 20;-fx-font-weight: bold;-fx-font-family: SimSun-ExtB;");
    dialogPane.setPrefSize(400, 200);
    Stage stage = (Stage) dialogPane.getScene().getWindow();
    try {stage.getIcons().add(icon);} catch (Exception ignored) {}
    stage.setTitle("提示");
    alert.showAndWait();
    return alert;
}

/**
 弹窗级别:
 info: 显示[错误,警告,信息]弹窗
 warning: 显示[错误,警告]弹窗
 error: 显示[错误]弹窗
 null: 无弹窗

 @param alertLevel 现在要弹出的弹窗级别
 @return 是否能弹出
 */
public static boolean checkLevel(Level alertLevel) {
    String level = YamlUtil.getString("hideAlert.level");// 配置文件的级别
    if (CheckUtil.notNull(level)) return true;//无配置信息
    if (alertLevel.equals(Level.OFF)) {// error: 为NULL时不能弹出
        return !CheckUtil.exist(level, "NULL");
    } else if (alertLevel.equals(Level.WARNING)) {// warning: 为NULL和ERROR时不能弹出
        return !CheckUtil.exist(level, "NULL", "ERROR");
    } else if (alertLevel.equals(Level.INFO)) {// info: 为NULL,ERROR,WARNING时不能弹出
        return !CheckUtil.exist(level, "NULL", "ERROR", "WARNING");
    }
    // 能弹出
    return true;
}

public static Alert alertInfo(String message) {
    if (!checkLevel(Level.INFO)) return null;
    return alert(Alert.AlertType.INFORMATION, message, imageMap.get("yes"));
}

public static Alert alertWarning(String message) {
    if (!checkLevel(Level.WARNING)) return null;
    return alert(Alert.AlertType.WARNING, message, imageMap.get("warning"));
}

public static Alert alertError(String message) {
    if (!checkLevel(Level.OFF)) return null;
    return alert(Alert.AlertType.ERROR, message, imageMap.get("no"));
}

    public static Alert alertInfo(String message, String isHideConfig) {
        if (YamlUtil.getBoolean(isHideConfig)) return null;
        return alertInfo(message);
    }

    public static Alert alertWarning(String message, String isHideConfig) {
        if (YamlUtil.getBoolean(isHideConfig)) return null;
        return alertWarning(message);
    }

    public static Alert alertError(String message, String config) {
        if (YamlUtil.getBoolean(config)) return null;
        return alertError(message);
    }

    /**
     选择文件

     @param title    弹窗标题
     @param function 弹窗类型, 导出 or 导入
     */
    public static File alertFileChoose(String title, Window window, BiFunction<FileChooser, Window, File> function, FileChooser.ExtensionFilter filter) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(title);
        fileChooser.getExtensionFilters().addAll(filter);
        return function.apply(fileChooser, window);
    }

    /**
     选择文件夹
     */
    public static File alertDirectorySave() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("保存文件");
        return directoryChooser.showDialog(null);   // 仅选择文件夹
    }

    /**
     选择Excel文件
     */
    public static File alertExcelScan() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("选择文件");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Excel Files", "*.xlsx"));
        return fileChooser.showOpenDialog(null);
    }

    /**
     弹出输入框
     */
    public static String alertInput(String text) {
        TextInputDialog textInputDialog = new TextInputDialog();
        textInputDialog.setTitle("删除");
        textInputDialog.setGraphic(null);
        textInputDialog.setHeaderText(null);
        textInputDialog.setContentText(text);
        Stage stage = (Stage) textInputDialog.getDialogPane().getScene().getWindow();
        try {stage.getIcons().add(imageMap.get("input"));} catch (Exception ignored) {}
        return textInputDialog.getResult();
    }

    /**
     弹出确认框
     */
    public static Alert alertChoose(String text) {
        Alert alert = alertWarning(text);
        alert.getButtonTypes().setAll(ButtonType.OK, ButtonType.CANCEL);
        return alert;
    }
}
