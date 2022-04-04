package com.tradoon.bookMall.dao;

import com.tradoon.bookMall.model.PmsProductAttributeCategory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * author:tradoon
 * desciption:
 * date:2022/ / /
 */
@Mapper
public interface PmsProductAttributeCategoryMapper {
    /**
     * 根据名字找到对应对象
     * @param name
     * @return
     */
    PmsProductAttributeCategory findByName(@Param("name") String name);

    /**
     * 插入属性
     * @param pmsProductAttributeCategory
     */
    void insertAttribute(PmsProductAttributeCategory pmsProductAttributeCategory);
}
