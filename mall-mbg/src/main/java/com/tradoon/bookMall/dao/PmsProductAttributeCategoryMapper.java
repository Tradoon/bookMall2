package com.tradoon.bookMall.dao;

import com.tradoon.bookMall.model.PmsProductAttributeCategory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * author:tradoon
 * desciption:
 * date:2022/ / /
 */
@Mapper
public interface PmsProductAttributeCategoryMapper {
    /**
     * 根据名字找到对应对象
     * @param pmsAC
     * @return
     */
    List<PmsProductAttributeCategory> findByList(PmsProductAttributeCategory pmsAC);

    /**
     * 插入属性
     * @param pmsProductAttributeCategory
     */
    void insertAttribute(PmsProductAttributeCategory pmsProductAttributeCategory);

    /**
     * 更新
     * @param pmsAC
     */
    void updateAC(PmsProductAttributeCategory pmsAC);

    /**
     * 删除attribute-Catgroy
     * @param id
     */
    void delAC(@Param("id") Long id);

    /**
     * 删除对应的attribute
     * @param id
     */
    void delAttribute(@Param("acid") Long id);
}
