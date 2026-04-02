package com.hnu.dto;

import com.hnu.common.Constants;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author hj
 * @Date 2023/11/28 14:39
 * @PackageName:com.hnu.dto
 * @ClassName: Result
 * @Description: create a class
 * @Version 1.0
 */
@Data
public class Result<I extends Serializable> {
    /**
     * 状态码
     */
    private Integer code;
    /**
     * 消息内容
     */
    private String msg;
    /**
     * 是否成功
     */
    private Boolean success;
    /**
     * 需要传递的数据
     */
    private Object data;
    /**
     * 总记录
     */
    private Long totalNum;


    public static Result<Serializable> ok(Constants c){
        Result<Serializable> result = new Result<Serializable>();
        result.setSuccess(true);
        result.setCode(c.getCode());
        result.setMsg(c.getMsg());
        return result;
    }

    public static Result<Serializable> error(Constants c){
        Result<Serializable> result = new Result<Serializable>();
        result.setSuccess(false);
        result.setCode(c.getCode());
        result.setMsg(c.getMsg());
        return result;
    }

    /**链式编程方法*/
    public Result msg(String msg){
        this.setMsg(msg);
        return this;
    }

    public Result code(Integer code){
        this.setCode(code);
        return this;
    }

    public Result data(Object data){
        this.setData(data);
        return this;
    }

    public Result success(Boolean success){
        this.setSuccess(success);
        return this;
    }

    public Result totalNum(Long totalNum){
        this.setTotalNum(totalNum);
        return this;
    }
}
