package com.jjdx.studentmanage;


import com.jjdx.studentmanage.Util.YamlUtil;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 学生信息管理系统启动类

 @ Author: 绝迹的星 <br>
 @ Time: 2024/7/1 <br> */
public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        YamlUtil.start();// 加载配置文件, 若无法加载则报错
        // 启动主界面
        FXMLLoader loader = new FXMLLoader(getClass().getResource("student.fxml"));// 加载UI界面
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.requestFocus();// 聚焦窗口
        stage.setTitle("学生信息管理系统");
        stage.setResizable(false);// 不可调整大小
        try {
            Image image = new Image(String.valueOf(getClass().getResource("/com/jjdx/studentmanage/images/Frame/icon.png")));
            stage.getIcons().add(image);
        } catch (Exception e) {
            e.printStackTrace();
        }
        stage.setScene(scene);
        stage.setOnCloseRequest(event -> System.exit(0));// 退出模式
        stage.show();// 显示窗口, 等待用户操作
    }

    public static void main(String[] args) {
        launch(args);
    }
}
