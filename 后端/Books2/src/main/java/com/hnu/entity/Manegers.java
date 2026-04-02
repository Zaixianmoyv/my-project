package com.hnu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName manegers
 */
@TableName(value ="manegers")
@Data
public class Manegers implements Serializable {
    /**
     * 表里的编号
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 名字
     */
    private String name;

    /**
     * 密码
     */
    private String password;

    /**
     * 头像
     */
    private String image;

    /**
     * 账号
     */
    private String username;

    /**
     * 性别
     */
    private String sex;

    /**
     * 电话
     */
    private String phone;

    /**
     * 管理员使用状态
     */
    private Integer status;
    private String passwords;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}