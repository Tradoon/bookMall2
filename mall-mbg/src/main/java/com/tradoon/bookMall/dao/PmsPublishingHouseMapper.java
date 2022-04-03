package com.tradoon.bookMall.dao;

import com.tradoon.bookMall.model.PublishingHouse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * author:tradoon
 * desciption:
 * date:2022/ / /
 */
@Mapper
public interface PmsPublishingHouseMapper {
    /**
     * 根据关键字查询出版社
     * @param keyword
     * @return
     */
    List<PublishingHouse> houseList(@Param("keyword") String keyword);

    /**
     * 插入出版社
     * @param publishingHouse
     */
    void insertPublishingHouse(PublishingHouse publishingHouse);

    /**
     * 删除出版社下的所有书
     * 逻辑删除
     * @param id
     * @param updateTime
     */
    void updateByHouse(@Param("id") Long id, @Param("updateTime") Date updateTime, @Param("status") int status);

    void updateHouse(PublishingHouse house);

    PublishingHouse findByInfo(PublishingHouse publishingHouse);

    List<PublishingHouse> mutiFind(PublishingHouse publishingHouse);
}
