package com.tradoon.bookMall.dao;

import com.tradoon.bookMall.model.PmsSkuStock;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * author:tradoon
 * desciption:
 * date:2022/ / /
 */
@Mapper
public interface PmsSkuStockMapper {
    /**
     * 根据相关信息进行查询
     * @param pmsSkuStock
     * @return
     */
    List<PmsSkuStock> findByInfo(PmsSkuStock pmsSkuStock);

    /**
     * 批量更新
     * @param pid
     * @param skuStockList
     */
    void update(@Param("list") List<PmsSkuStock> skuStockList);
}
