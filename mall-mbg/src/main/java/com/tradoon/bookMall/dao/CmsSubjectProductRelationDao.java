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

    /**
     *
     * @param productId
     * @return
     */
    List<CmsSubjectProductRelation> findByInfo(@Param("productId") Long productId);

    void multiDel(@Param("id") List<Long> id);
}
