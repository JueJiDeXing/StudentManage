module com.jjdx.studentmanage {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires java.sql;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;
    requires org.yaml.snakeyaml;
    requires org.mybatis;
    requires lombok;
    requires annotations;
    requires org.apache.poi.poi;
    requires org.apache.poi.ooxml;


    opens com.jjdx.studentmanage to javafx.fxml;
    exports com.jjdx.studentmanage;
    exports com.jjdx.studentmanage.pojo;
    opens com.jjdx.studentmanage.pojo to javafx.fxml;
    exports com.jjdx.studentmanage.DBMS;
    opens com.jjdx.studentmanage.DBMS to javafx.fxml;
    opens com.jjdx.studentmanage.Controller to javafx.fxml;
    exports com.jjdx.studentmanage.Controller;
    exports com.jjdx.studentmanage.DBMS.RandomData;
    opens com.jjdx.studentmanage.DBMS.RandomData to javafx.fxml;
}
