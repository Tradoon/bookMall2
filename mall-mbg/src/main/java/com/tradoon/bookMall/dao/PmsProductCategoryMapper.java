package com.tradoon.bookMall.dao;

import com.tradoon.bookMall.model.PmsProductCategory;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * author:tradoon
 * desciption: 书本分类
 * date:2022/ / /
 */
@Mapper
public interface PmsProductCategoryMapper {
    /**
     * 通过相关信息对category进行查询
     * @param tmpCategory
     * @return
     */
    List<PmsProductCategory> findByInfo(PmsProductCategory tmpCategory);

    /**
     * 插入分类信息
     * @param pmsProductCategory
     */
    void insertInfo(PmsProductCategory pmsProductCategory);
}
