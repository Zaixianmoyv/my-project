package com.hnu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName books
 */
@TableName(value ="books")
@Data
public class Books implements Serializable {
    /**
     * 书籍编号
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 书籍名称
     */
    private String name;

    /**
     * 书籍描述
     */
    private String description;

    /**
     * 库存数量
     */
    private Integer number;

    /**
     * 书籍图片
     */
    private String image;

    /**
     * 书籍分类
     */
    private Long sortid;

    /**
     * 作者
     */
    private String author;

    /**
     * 语言
     */
    private String language;

    /**
     * 版本
     */
    private String version;

    /**
     * 图书价格
     */
    private Integer price;

    /**
     * 图书价格
     */
    private Integer news;
    private Integer hot;

    private Integer isdeleted;

    @TableField(exist = false)
    private String sortname;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}