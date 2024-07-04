package com.jjdx.studentmanage.Util;

import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

/**
 窗口工具

 @ Author: 绝迹的星 <br>
 @ Time: 2024/7/2 <br> */
public class StageUtil {
    /**
     添加图标

     @param clazz 当前类
     @param stage 当前窗口
     @param name  图片名称
     */
    public static void addIcon(Class clazz, Stage stage, String name) {
        try {
            URL insertUrl = clazz.getResource(name);
            stage.getIcons().add(new Image(Objects.requireNonNull(insertUrl).toString()));
        } catch (Exception ignored) {// 图标加载不出来就算了
        }
    }

/**
 加载页面, 如果已加载则直接返回null

 @param clazz    当前类
 @param xmlPath  页面路径
 @param stage    当前窗口
 @param title    窗口标题
 @param iconPath 图标路径
 */
public static FXMLLoader loadStage(Class clazz, String xmlPath, Stage stage, String title, String iconPath) throws IOException {
    if (stage.isShowing()) {// 已在展示, 防止多开
        stage.requestFocus();
        return null;
    }
    try {// 尝试加载图标
        stage.getIcons().add(new Image(String.valueOf(clazz.getResource(iconPath))));
    } catch (Exception e) {
        e.printStackTrace();
    }
    // 加载UI
    FXMLLoader loader = new FXMLLoader(clazz.getResource(xmlPath));
    Parent root = loader.load();
    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.setTitle(title);
    stage.show();
    return loader;// 将loader返回出去
}

    /**
     添加提示弹窗
     */
    public static void addTip(TextField node, String tip) {
        Tooltip tooltip = new Tooltip(tip);
        tooltip.setShowDelay(Duration.millis(0));
        tooltip.setHideDelay(Duration.millis(0));
        node.setTooltip(tooltip);
    }

    /**
     设置聚焦函数, 聚焦后取消聚焦

     @param root 根节点
     @param need 需要加的类型
     */
    public static void initFocus(Pane root, Class... need) {
        root.getChildren().forEach(node -> initFocus(root, node, need));
    }

    private static void initFocus(Pane root, Node node, Class... need) {
        // 是需要加的类型
        for (Class clazz : need) {
            if (ClassUtil.isInstanceOf(node, clazz)) {
                node.setOnMouseClicked(event -> root.requestFocus());
                return;
            }
        }
        // 尝试转换为容器类型进行递归
        try {
            ObservableList<Node> children = ((Pane) node).getChildren();
            children.forEach(child -> initFocus(root, child, need));
        } catch (Exception ignored) {
        }
    }

    /**
     透明按钮
     */
    public static Button createTransparentButton(int width, int height, Image image, String text) {
        Button button = new Button();
        button.setStyle("-fx-background-color: transparent");
        button.setPrefWidth(width);
        button.setPrefHeight(height);
        if (image == null) {
            button.setText(text);
        } else {
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(width);
            imageView.setFitHeight(height);
            button.setGraphic(imageView);
        }
        button.setOpacity(0);
        return button;
    }
}
