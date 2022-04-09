package com.tradoon.bookMall.dao;

import com.tradoon.bookMall.model.PmsSkuStock;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * author:tradoon
 * desciption:库存信息
 * date:2022/ / /
 */
@Mapper
public interface PmsSkuStockDao {
    /**
     * 批量插入库存信息
     * @param listWithId
     */
    void insertList(@Param("list") List<PmsSkuStock> listWithId);
}
