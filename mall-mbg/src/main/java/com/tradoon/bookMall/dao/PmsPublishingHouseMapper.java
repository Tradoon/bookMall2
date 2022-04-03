package com.tradoon.bookMall.dao;

import com.tradoon.bookMall.api.CommonResult;
import com.tradoon.bookMall.model.PublishingHouse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * author:tradoon
 * desciption:
 * date:2022/ / /
 */
@Mapper
public interface PmsPublishingHouseMapper {
    List<PublishingHouse> houseList(@Param("keyword") String keyword);

    void insertPublishingHouse(PublishingHouse publishingHouse);
}
