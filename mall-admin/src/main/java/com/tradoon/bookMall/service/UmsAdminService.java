package com.tradoon.bookMall.service;

import com.tradoon.bookMall.api.CommonResult;
import com.tradoon.bookMall.model.UmsAdmin;

import java.util.Map;

/**
 * author:tradoon
 * desciption:
 * date:2022/ / /
 */
public interface UmsAdminService {
    /**
     *
     * @param user
     * @return
     */
    CommonResult<UmsAdmin> register(UmsAdmin user);

    CommonResult<UmsAdmin> getItem(Long id);

    /**
     *
     * @param id
     * @param admin
     */
    void update(Long id, UmsAdmin admin);

    CommonResult login(UmsAdmin user);

    CommonResult logout();

    CommonResult<Map<String, Object>> getAdminInfo();

    CommonResult refreshToken(String token);
}
