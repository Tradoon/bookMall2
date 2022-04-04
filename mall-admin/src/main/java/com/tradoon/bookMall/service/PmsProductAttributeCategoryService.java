package com.tradoon.bookMall.service;

import com.tradoon.bookMall.api.CommonPage;
import com.tradoon.bookMall.api.CommonResult;
import com.tradoon.bookMall.model.PmsProductAttributeCategory;

/**
 * author:tradoon
 * desciption:
 * date:2022/ / /
 */
public interface PmsProductAttributeCategoryService {
    CommonResult create(String name);

    CommonResult<CommonPage<PmsProductAttributeCategory>> selectList(Integer pageNum, Integer pageSize);

    CommonResult update(Long id, String name);
}
