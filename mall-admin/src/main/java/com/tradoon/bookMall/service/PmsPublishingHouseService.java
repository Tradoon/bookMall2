package com.tradoon.bookMall.service;

import com.tradoon.bookMall.api.CommonPage;
import com.tradoon.bookMall.api.CommonResult;
import com.tradoon.bookMall.model.PulishingHouse;

/**
 * author:tradoon
 * desciption:
 * date:2022/ / /
 */
public interface PmsPublishingHouseService {
    CommonResult<CommonPage<PulishingHouse>> houseList(String keyword, int pageNum, int pageSize);
}
