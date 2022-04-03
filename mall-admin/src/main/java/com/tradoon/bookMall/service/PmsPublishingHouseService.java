package com.tradoon.bookMall.service;

import com.tradoon.bookMall.api.CommonPage;
import com.tradoon.bookMall.api.CommonResult;
import com.tradoon.bookMall.model.PublishingHouse;

/**
 * author:tradoon
 * desciption:
 * date:2022/ / /
 */
public interface PmsPublishingHouseService {
    CommonResult<CommonPage<PublishingHouse>> houseList(String keyword, int pageNum, int pageSize);

    CommonResult insertPublishingHouse(PublishingHouse publishingHouse);

    CommonResult deletePublishHouse(Long id);

    CommonResult updatePublishHouse(Long id, PublishingHouse publishingHouse);
}
