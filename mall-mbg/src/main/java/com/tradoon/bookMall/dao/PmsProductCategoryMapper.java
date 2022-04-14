package com.tradoon.bookMall.dao;

import com.tradoon.bookMall.model.PmsProductCategory;
import com.tradoon.bookMall.model.PmsProductCategoryWithChildrenItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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

    /**
     * 更改相关信息
     * @param ids
     * @param pmspc
     */
    void updateByInfo(@Param("ids") List<Long> ids, @Param("pmspc") PmsProductCategory pmspc);

    /**
     * 刪除指定id的信息
     * @param id
     */
    void delete(@Param("id") Long id);

    /**
     * 筛选子分类
     * @param parentId
     * @return
     */
    List<PmsProductCategoryWithChildrenItem> listWithChildren(Long parentId);
}
