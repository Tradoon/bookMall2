package com.tradoon.bookMall.dao;

import com.tradoon.bookMall.model.PmsProduct;
import com.tradoon.bookMall.model.PmsProductResult;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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

    /**
     * 按照筛选条件进行查询
     * @param pmsProduct
     * @return
     */
    List<PmsProduct> findBySelective(PmsProduct pmsProduct);

    /**
     * 对商品信息进行更改
     * @param pmsProduct
     */
    void updateByPrimaryKeySelective(@Param("pmsProduct") PmsProduct pmsProduct, @Param("ids") List<Long> ids);

}
