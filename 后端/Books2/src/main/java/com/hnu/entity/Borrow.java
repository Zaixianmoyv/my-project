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
 * @TableName bookstatus
 */
@TableName(value ="borrow")
@Data
public class Borrow implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 图书状态
     */
    private Integer status;

    /**
     * 借阅人id
     */
    private Long studentid;

    /**
     * 图书id
     */
    private Long bookid;

    /**
     * 借出时间
     */
    private Date createtime;

    /**
     * 归还时间
     */
    private Date backtime;

    /**
     * 截止时间
     */
    private Date endtime;

    private Integer sback;

    private Integer mback;

    @TableField(exist = false)
    private String studentName;

    @TableField(exist = false)
    private String bookName;

    @TableField(exist = false)
    private String author;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}