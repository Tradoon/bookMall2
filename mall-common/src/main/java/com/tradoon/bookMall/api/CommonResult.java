package com.tradoon.bookMall.api;

import lombok.Data;

/**
 * author:tradoon
 * desciption:前后端分离通用返回对象
 * date:2022/02/09
 */
@Data
public class CommonResult<T> {
    /**
     * 状态码
     */
    private long code;

    /**
     *提示信息
     */
    private String message;
    /**
     * 数据
     */
    private T data;

    CommonResult(){

    }
    CommonResult(long code, String message, T data){
        this.code=code;
        this.message=message;
        this.data=data;
    }

    public static <T> CommonResult<T> success(T data){
        return new CommonResult(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(),data);
    }

    public static <T> CommonResult<T> success(T data,String message){
        return new CommonResult(ResultCode.SUCCESS.getCode(), message,data);
    }
    /**
     * 返回失敗的結果
     */

    public static <T> CommonResult<T> failed(){
        return new CommonResult<T>(ResultCode.FAILED.getCode(),ResultCode.FAILED.getMessage(), null);
    }

    public static <T> CommonResult<T> failed(String message){
        return new CommonResult<T>(ResultCode.FAILED.getCode(),message, null);
    }
    public static <T> CommonResult<T> failed(long code,String message){
        return new CommonResult<T>(code,message, null);
    }
    public static <T> CommonResult<T> unauthorized(T data){
        return new CommonResult<T>(ResultCode.UNAUTHORIZED.getCode(),ResultCode.UNAUTHORIZED.getMessage(),data);
    }
    public static <T> CommonResult<T> forbidden(T data){
        return new CommonResult<T>(ResultCode.FORBIDDEN.getCode(),ResultCode.FORBIDDEN.getMessage(), data);
    }
}