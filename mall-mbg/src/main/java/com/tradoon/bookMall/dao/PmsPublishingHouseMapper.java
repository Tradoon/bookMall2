package com.tradoon.bookMall.dao;

import com.tradoon.bookMall.model.PulishingHouse;
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
    List<PulishingHouse> houseList(@Param("keyword") String keyword);
}
