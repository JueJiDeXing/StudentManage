USE s_t;
drop TABLE if EXISTS student;
CREATE TABLE student
(
    id          CHAR(10) primary key CHECK (length(id) = 10) comment '学号',
    name        VARCHAR(4) not null comment '姓名',
    dept        VARCHAR(10) comment '专业',
    className   VARCHAR(13) comment '班级',
    age         INT CHECK (0 < age and age < 100) comment '年龄',
    sex         CHAR(1) CHECK ( sex = '男' or sex = '女' ) comment '性别',
    nativePlace VARCHAR(10) comment '籍贯',
    email       VARCHAR(50) comment '邮箱',
    birthday    DATE CHECK (birthday < now()) comment '出生日期',
    outlook     VARCHAR(4) CHECK ( outlook = '党员' or outlook = '共青团员' or outlook = '群众' ) comment '政治面貌',
    address     VARCHAR(50) comment '家庭住址',
    phone       CHAR(11) CHECK (phone like '1%') comment '电话号码',
    CONSTRAINT chk_class_dept CHECK
        ((className is null and dept is null) or
         (dept is not null and className like CONCAT(dept, '%')))
);
