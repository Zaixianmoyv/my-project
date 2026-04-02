package com.hnu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName student
 */
@TableName(value ="student")
@Data
public class Student implements Serializable {
    /**
     * 表里的编号
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 学生真实姓名
     */
    private String name;

    /**
     * 登录密码
     */
    private String password;

    /**
     * 是否禁用
     */
    private Integer status;

    /**
     * 电话
     */
    private String phone;

    /**
     * 地址
     */
    private String address;

    /**
     * 头像
     */
    private String image;

    /**
     * 可借阅书的数量
     */
    private Integer number;

    /**
     * 账号名
     */
    private String username;

    /**
     * 性别
     */
    private String sex;

    /**
     * 信誉值
     */
    private Integer reputation;

    /**
     * 是否登录（0未登录1已登录）
     */
    private Integer islogin;
    private String passwords;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}