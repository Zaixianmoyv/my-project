package com.hnu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName booksort
 */
@TableName(value ="booksort")
@Data
public class Booksort implements Serializable {
    /**
     * 分类id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 图书类名
     */
    private String name;


    /**
     * 备注
     */
    private String description;


    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}