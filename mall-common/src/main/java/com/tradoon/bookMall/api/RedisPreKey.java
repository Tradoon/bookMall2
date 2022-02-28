package com.tradoon.bookMall.api;

/**
 * author:tradoon
 * desciption:
 * date:2022/ / /
 */
public enum RedisPreKey {
    ADMIN_PREKEY("admin:id:","redis中存储用户的信息,AdminUserDetails类型"),
    ADMIN_ROLE_RESOURCE("admin:role:resource:","redis中存储用户权限和能访问的资源");
    private String preKey;
    private String des;
    RedisPreKey( String preKey,String des){
        this.preKey=preKey;
        this.des=des;
    }
    public String getPreKey(){
        return preKey;
    }

    public String getDescription(){
        return des;
    }
}
