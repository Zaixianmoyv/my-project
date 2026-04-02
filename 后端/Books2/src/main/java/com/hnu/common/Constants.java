package com.hnu.common;

/**
 * @Author hj
 * @Date 2023/11/28 14:40
 * @PackageName:com.hnu.common
 * @ClassName: Constants
 * @Description: create a class
 * @Version 1.0
 */

import lombok.Getter;

/**
 * 枚举是一种类
 * 注解是一种接口
 */
@Getter
public enum Constants {

    LOGIN_OK(0,"登录成功"),
    LOGIN_UNKNOW_USERNAME(1,"账号不存在"),
    LOGIN_ERROR_PASSWORD(2,"密码错误"),
    LOGIN_ACCOUNT_DISABLED(3,"账号禁用"),
    UNKNOWN_REASON(-1,"未知异常"),
    DATE_HANDLER_OK(4,"日期处理成功"),
    UPLOAD_ERROR(5,"上传异常"),
    LOGINOUT_OK(6,"退出成功"),
    REGISTER_OK(10011,"注册成功"),
    DELETE_OK(10012,"删除成功"),
    UPDATE_OK(10013,"修改成功"),
    OK(1000,"操作成功"),
    NO_FIND(1001,"没有找到相关数据"),
    FIND_SUCCESS(1002,"找到相关数据"),
    ADD_SUCCESS(1003,"添加成功"),
    ADD_ERROR(1004,"添加失败"),
    UPDATE_ERROR(10014,"修改失败"),
    DELETE_ERROR(10015,"删除失败"),
    UPLOAD_SUCCESS(10016,"上传成功"),
    REGISTER_ERRROR(10017,"账号已存在");

    private final Integer code;
    private final String msg;

    Constants(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
