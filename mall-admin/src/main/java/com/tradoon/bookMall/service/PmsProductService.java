package com.tradoon.bookMall.service;

import com.tradoon.bookMall.api.CommonResult;
import com.tradoon.bookMall.dto.PmsProductParam;
import com.tradoon.bookMall.model.PmsProduct;

/**
 * author:tradoon
 * desciption:
 * date:2022/ / /
 */

public interface PmsProductService {
    CommonResult create(PmsProductParam product);
}
