package com.tradoon.bookMall.service;

import com.tradoon.bookMall.api.CommonPage;
import com.tradoon.bookMall.api.CommonResult;
import com.tradoon.bookMall.dto.PmsProductCategoryParam;
import com.tradoon.bookMall.model.PmsProductAttributeCategory;
import com.tradoon.bookMall.model.PmsProductCategory;
import org.springframework.transaction.annotation.Transactional;

/**
 * author:tradoon
 * desciption:
 * date:2022/ / /
 */
public interface PmsProductCategoryService {

    @Transactional
    CommonResult create(PmsProductCategoryParam param);

    CommonResult<CommonPage<PmsProductCategory>> getList(Long parentId, Integer pageSize, Integer pageNum);
}
