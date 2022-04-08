package com.tradoon.bookMall.dao;

import com.tradoon.bookMall.model.PmsProductCategoryAttributeRelation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * author:tradoon
 * desciption:
 * date:2022/ / /
 */
@Mapper
public interface PmsProductCategoryAttributeRelationDao {
    /**
     * 添加属性和分类的关联信息
     * @param ppacList
     */
    void insertRelation(@Param("ppacList") List<PmsProductCategoryAttributeRelation> ppacList);
}
