package com.tradoon.bookMall.service;

import com.tradoon.bookMall.api.CommonPage;
import com.tradoon.bookMall.api.CommonResult;
import com.tradoon.bookMall.dto.PmsProductAttributeParam;
import com.tradoon.bookMall.model.PmsProductAttribute;

/**
 * author:tradoon
 * desciption:
 * date:2022/ / /
 */
public interface PmsProductAttributeService {
    CommonResult creat(PmsProductAttributeParam productAttributeParam);

    CommonResult<CommonPage<PmsProductAttribute>> getList(Long id, Integer pageSize, Integer pageNum,Integer type);

    CommonResult update(Long id, PmsProductAttributeParam productAttributeParam);
}
