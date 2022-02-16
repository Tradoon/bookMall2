package com.tradoon.bookMall.exception;

/**
 * author:tradoon
 * desciption:在进行token认证时期的一场
 * date:2022/ / /
 */

public class TokenException extends  RuntimeException{
    public TokenException(String message){
        super(message);
    }
}
