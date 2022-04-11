package com.tradoon.bookMall.service;

import com.tradoon.bookMall.api.CommonPage;
import com.tradoon.bookMall.api.CommonResult;
import com.tradoon.bookMall.dto.PmsProductParam;
import com.tradoon.bookMall.dto.PmsProductQueryParam;
import com.tradoon.bookMall.model.PmsProduct;

/**
 * author:tradoon
 * desciption:
 * date:2022/ / /
 */

public interface PmsProductService {
    CommonResult create(PmsProductParam product);

    /**
     * 分页查询商品信息
     * @param productQueryParam
     * @param pageSize
     * @param pageNum
     * @return
     */
    CommonResult<CommonPage<PmsProduct>> getPageList(PmsProductQueryParam productQueryParam, Integer pageSize, Integer pageNum);

    CommonResult update(Long id, PmsProductParam productParam);
}
