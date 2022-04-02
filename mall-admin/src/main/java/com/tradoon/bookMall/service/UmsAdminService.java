package com.tradoon.bookMall.service;

import com.tradoon.bookMall.api.CommonPage;
import com.tradoon.bookMall.api.CommonResult;
import com.tradoon.bookMall.dto.UpdateAdminPasswordParam;
import com.tradoon.bookMall.model.UmsAdmin;

import java.util.List;
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

    CommonResult<CommonPage<UmsAdmin>> list(String keyword, Integer pageSize, Integer pageNum);

    CommonResult updatePassword(UpdateAdminPasswordParam udAdmiPwd);

    CommonResult changeStatus(Long id, Integer status);

    CommonResult updateRole(Long adminId, List<Long> roleIds);
}
