package com.tradoon.bookMall.dao;

import com.tradoon.bookMall.model.PmsMemberPrice;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * author:tradoon
 * desciption:
 * date:2022/ / /
 */
@Mapper
public interface PmsMemberPriceDao {
    /**
     * 插入会员价格信息
     * @param memberPrice
     */
    void insertList(@Param("list") List<PmsMemberPrice> memberPrice);
}
