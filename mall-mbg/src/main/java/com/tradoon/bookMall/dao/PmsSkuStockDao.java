package com.tradoon.bookMall.dao;

import com.tradoon.bookMall.model.PmsSkuStock;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
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

    /**
     * 根据
     * @param projectId
     * @return
     */
    List<PmsSkuStock> findByInfo(@Param("productId") Long productId);

    /**
     * 批量删除sku信息
     * @param delListProdctId
     */
    void delMulti(@Param("id") ArrayList<Long> delListProdctId);
}
