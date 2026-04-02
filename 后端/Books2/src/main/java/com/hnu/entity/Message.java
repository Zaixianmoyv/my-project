package com.hnu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName message
 */
@TableName(value ="message")
@Data
public class Message implements Serializable {
    /**
     * 
     */
    private Long id;

    /**
     * 发布时间
     */
    private Date createtime;

    /**
     * 发布内容
     */
    private String content;

    /**
     * 发布者
     */
    private Long manegerid;

    /**
     * 标题
     */
    private String title;

    /**
     * 管理员名
     */
    @TableField(exist = false)
    private String mname;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}