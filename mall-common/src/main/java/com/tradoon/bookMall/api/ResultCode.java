package com.tradoon.bookMall.api;

/**
 * author:tradoon
 * desciption: 枚举成功/失败的各种情况
 * date:
 */
public enum ResultCode {
    SUCCESS(200,"请求成功"),
    FAILED(5000,"程序结束，前端问题"),
//200x:注册和登录错误
    NameRepeated(2000,"用户名重复"),
    PASSWORDERRO(2001,"用户名或密码错误"),
    NOADMIN(2002,"用户名不存在"),
    PASSWORDREPEAT(2003,"新旧密码相同"),
//300x权限相关错误
    UNAUTHORIZED(3001,"暂未登录或者登录已经过期或redis服务未启动"),
    FORBIDDEN(3002,"用户没有该权限")
    ;

    //枚举依赖构造函数
    //枚举之间是，分割
    //todo 还待增加

    private long code;

    private String message;


    ResultCode(int code, String message) {
        this.code=code;
        this.message=message;
    }
    public long getCode(){
        return this.code;
    }

    public String getMessage(){
        return this.message;
    }
}
