package com.tradoon.bookMall.dao;

import com.tradoon.bookMall.model.PmsProduct;
import org.apache.ibatis.annotations.Mapper;

/**
 * author:tradoon
 * desciption:
 * date:2022/ / /
 */
@Mapper
public interface PmsProductMapper {
    /**
     * 插入商品
     * @param pmsProduct
     */
    void insertSelective(PmsProduct pmsProduct);
}
