package com.tradoon.bookMall.dao;

import com.tradoon.bookMall.model.CmsSubjectProductRelation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * author:tradoon
 * desciption: 商品的主题
 * date:2022/ / /
 */
@Mapper
public interface CmsSubjectProductRelationDao {
    /**
     * 批量增加商品的主题
     * @param listWithId
     */
    void insertList(@Param("list") List<CmsSubjectProductRelation> listWithId);
}
