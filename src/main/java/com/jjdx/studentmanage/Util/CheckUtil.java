package com.jjdx.studentmanage.Util;

import com.jjdx.studentmanage.DBMS.StudentService;
import com.jjdx.studentmanage.pojo.SelectCondition;
import com.jjdx.studentmanage.pojo.Student;

import java.time.LocalDate;

/**
 约束检查工具

 @ Author: 绝迹的星 <br>
 @ Time: 2024/7/2 <br> */
public class CheckUtil {
  public   static class DataIllegalException extends RuntimeException {
        public DataIllegalException(String message) {
            super(message);
        }
    }

    /**
     判断非空对象
     - 特别的: 如果是String, 空格组成的也视为空
     */
    public static boolean notNull(Object obj) {
        return !isNull(obj);
    }

    /**
     判断空对象
     - 特别的: 如果是String, 空格组成的也视为空
     */
    public static boolean isNull(Object obj) {
        if (obj == null) return true;
        if (obj instanceof String) {
            return ((String) obj).trim().isEmpty();
        }
        return false;
    }

    /**
     存在性判别, obj是否存在于给定的os列表中
     */
    public static boolean exist(Object obj, Object... os) {
        for (Object o : os) {
            if (o.equals(obj)) return true;
        }
        return false;
    }

    /**
     检查输入的查询条件是否合法
     - 所有属性均可为null

     @return 返回非法信息, 返回值为null表示合法
     */
    public static String conditionValidInfo(SelectCondition s) {
        if (notNull(s.getId()) && !isValidId(s.getId())) {
            return "学号不合法";
        }
        if (notNull(s.getName()) && !isValidName(s.getName())) {
            return "姓名不合法";
        }
        if (notNull(s.getDept()) && !isValidDept(s.getDept())) {
            return "专业不合法";
        }
        if (notNull(s.getClassName())) {
            if (!isValidClassName(s.getClassName())) return "班级不合法";
            if (isNull(s.getDept())) return "需填写专业";
            if (!s.getClassName().startsWith(s.getDept())) return "专业不匹配";
        }
        if (notNull(s.getStartAge()) && notNull(s.getEndAge()) && s.getStartAge() >= s.getEndAge()) {
            return "年龄范围不合法";
        }
        if (notNull(s.getSex()) && !isValidSex(s.getSex())) {
            return "性别不合法";
        }
        if (notNull(s.getNativePlace()) && !isValidNativePlace(s.getNativePlace())) {
            return "籍贯不合法";
        }
        if (notNull(s.getEmail()) && !isValidEmail(s.getEmail())) {
            return "邮箱不合法";
        }
        if (notNull(s.getStartBirthday()) && notNull(s.getEndBirthday())) {
            if (s.getStartBirthday().isAfter(s.getEndBirthday())) return "出生日期范围不合法";
        }
        if (notNull(s.getOutlook()) && !isValidOutlook(s.getOutlook())) {
            return "政治面貌不合法";
        }
        if (notNull(s.getAddress()) && !isValidAddress(s.getAddress())) {
            return "家庭住址不合法";
        }
        if (notNull(s.getPhone()) && !isValidPhone(s.getPhone())) {
            return "电话号码不合法";
        }
        return null;
    }

    /**
     判断学生记录是否能插入
     - 学号和姓名非空, 学号唯一, 其余项可为空

     @return 非法信息, 如果合法返回null
     */
    public static String studentInsertValid(Student s) {
        if (isNull(s.getId())) {
            return "学号不能为空";
        }
        if (!isValidId(s.getId())) {
            return "学号不合法";
        }
        if (isExistId(s.getId())) {
            return "学号已存在";
        }
        if (isNull(s.getName())) {
            return "姓名不能为空";
        }
        if (!isValidName(s.getName())) {
            return "姓名不合法";
        }
        if (notNull(s.getDept()) && !isValidDept(s.getDept())) {
            return "专业不合法";
        }
        if (notNull(s.getClassName())) {
            if (!isValidClassName(s.getClassName())) return "班级不合法";
            if (!notNull(s.getDept())) return "需填写专业";
            if (!s.getClassName().startsWith(s.getDept())) return "专业不匹配";
        }
        if (notNull(s.getAge()) && !isValidAge(s.getAge())) {
            return "年龄不合法";
        }
        if (notNull(s.getSex()) && !isValidSex(s.getSex())) {
            return "性别不合法";
        }
        if (notNull(s.getNativePlace()) && !isValidNativePlace(s.getNativePlace())) {
            return "籍贯不合法";
        }
        if (notNull(s.getEmail()) && !isValidEmail(s.getEmail())) {
            return "邮箱不合法";
        }
        if (notNull(s.getBirthday())) {
            if (!isValidBirthday(s.getBirthday())) return "出生日期不合法";
            if (notNull(s.getAge()) && s.getBirthday().plusYears(s.getAge()).getYear() != LocalDate.now().getYear()) {
                return "年龄与出生日期不匹配";
            }
        }
        if (notNull(s.getOutlook()) && !isValidOutlook(s.getOutlook())) {
            return "政治面貌不合法";
        }
        if (notNull(s.getAddress()) && !isValidAddress(s.getAddress())) {
            return "家庭住址不合法";
        }
        if (notNull(s.getPhone()) && !isValidPhone(s.getPhone())) {
            return "电话号码不合法";
        }
        return null;
    }

    /**
     判断学生记录是否能更新
     - 学号非空且存在, 其余均可为空

     @return 非法信息, 如果合法返回null
     */
    public static String studentUpdateValid(Student s) {
        if (isNull(s.getId())) {
            return "学号不能为空";
        }
        if (!isValidId(s.getId())) {
            return "学号不合法";
        }
        if (!isExistId(s.getId())) {
            return "学号不存在";
        }
        if (notNull(s.getName()) && !isValidName(s.getName())) {
            return "姓名不合法";
        }
        if (notNull(s.getDept()) && !isValidDept(s.getDept())) {
            return "专业不合法";
        }
        if (notNull(s.getClassName())) {
            if (!isValidClassName(s.getClassName())) return "班级不合法";
            if (isNull(s.getDept())) return "需填写专业";
            if (!s.getClassName().startsWith(s.getDept())) return "专业不匹配";
        }
        if (notNull(s.getAge()) && !isValidAge(s.getAge())) {
            return "年龄不合法";
        }
        if (notNull(s.getSex()) && !isValidSex(s.getSex())) {
            return "性别不合法";
        }
        if (notNull(s.getNativePlace()) && !isValidNativePlace(s.getNativePlace())) {
            return "籍贯不合法";
        }
        if (notNull(s.getEmail()) && !isValidEmail(s.getEmail())) {
            return "邮箱不合法";
        }
        if (notNull(s.getBirthday())) {
            if (!isValidBirthday(s.getBirthday())) return "出生日期不合法";
            if (notNull(s.getAge()) && s.getBirthday().plusYears(s.getAge()).getYear() != LocalDate.now().getYear()) {
                return "年龄与出生日期不匹配";
            }
        }
        if (notNull(s.getOutlook()) && !isValidOutlook(s.getOutlook())) {
            return "政治面貌不合法";
        }
        if (notNull(s.getAddress()) && !isValidAddress(s.getAddress())) {
            return "家庭住址不合法";
        }
        if (notNull(s.getPhone()) && !isValidPhone(s.getPhone())) {
            return "电话号码不合法";
        }
        return null;
    }


    /**
     合法id, 10位数字
     */
    public static boolean isValidId(String id) {
        return CheckUtil.notNull(id) && id.matches("\\d{10}");
    }

    /**
     数据库中是否存在该id
     */
    public static boolean isExistId(String id) {
        if (!isValidId(id)) return false;
        for (Student student : StudentService.findAllStudent()) {
            if (student.getId().equals(id)) return true;
        }
        return false;
    }

    /**
     合法姓名, [2,4]位数字
     */
    public static boolean isValidName(String name) {
        return CheckUtil.notNull(name) && name.matches("^[\\u4e00-\\u9fa5]{2,4}$");
    }

    /**
     合法专业, [2,10]位中文字符
     */
    public static boolean isValidDept(String dept) {
        return CheckUtil.notNull(dept) && dept.matches("^[\\u4e00-\\u9fa5]{2,10}$");
    }

    /**
     合法班级, 3位数字 + '班'
     */
    public static boolean isValidClassName(String className) {
        return CheckUtil.notNull(className) && className.matches("[\\u4e00-\\u9fa5]+^\\d{3}班$");
    }

    /**
     合法年龄, 0~100
     */
    public static boolean isValidAge(Integer age) {
        return CheckUtil.notNull(age) && 0 <= age && age <= 100;
    }

    /**
     合法年龄, 0~100
     */
    public static boolean isValidAge(String age) {
        return CheckUtil.notNull(age) && age.length() <= 3 && isValidAge(Integer.parseInt(age));
    }

    /**
     合法性别, 男|女
     */
    public static boolean isValidSex(String sex) {
        return CheckUtil.notNull(sex) && (sex.equals("男") || sex.equals("女"));
    }

    /**
     合法籍贯, [2,10]位中文字符
     */
    public static boolean isValidNativePlace(String nativePlace) {
        return CheckUtil.notNull(nativePlace) &&
                nativePlace.matches("^[\\u4e00-\\u9fa5]{2,10}$");
    }

    /**
     合法邮箱,  用户名 + '@' + 域名
     */
    public static boolean isValidEmail(String email) {
        return CheckUtil.notNull(email) &&
                email.matches("^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$");
    }

    /**
     合法出生日期, 在现在之前
     */
    public static boolean isValidBirthday(LocalDate birthday) {
        return CheckUtil.notNull(birthday) &&
                birthday.isBefore(LocalDate.now());
    }

    /**
     合法政治面貌, 党员|共青团员|群众
     */
    public static boolean isValidOutlook(String outlook) {
        return CheckUtil.notNull(outlook) &&
                (outlook.equals("党员") || outlook.equals("共青团员") || outlook.equals("群众"));
    }

    /**
     合法家庭住址, [2,50]位合法字符
     */
    public static boolean isValidAddress(String address) {
        return CheckUtil.notNull(address) &&
                address.matches("^[\\u4e00-\\u9fa5a-zA-Z0-9]{2,50}$");
    }

    /**
     合法手机号, 以1开始的11位数字
     */
    public static boolean isValidPhone(String phone) {
        return CheckUtil.notNull(phone) &&
                phone.matches("^1\\d{10}$");
    }


}
