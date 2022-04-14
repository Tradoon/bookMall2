package com.tradoon.bookMall.service;

import com.tradoon.bookMall.api.CommonResult;
import com.tradoon.bookMall.model.PmsSkuStock;

import java.util.List;

/**
 * author:tradoon
 * desciption:
 * date:2022/ / /
 */
public interface PmsSkuStockService {
    CommonResult<List<PmsSkuStock>> getList(Long pid, String keyword);

    CommonResult update(Long pid, List<PmsSkuStock> skuStockList);
}
