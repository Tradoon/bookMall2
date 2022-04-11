package com.tradoon.bookMall.dao;

import com.tradoon.bookMall.model.CmsPrefrenceAreaProductRelation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * author:tradoon
 * desciption:
 * date:2022/ / /
 */
@Mapper
public interface CmsPrefrenceAreaProductRelationDao {
    /**
     * 批量增加优选信息
     * @param listWithId
     */
    void insertList(@Param("list") List<CmsPrefrenceAreaProductRelation> listWithId);

    /**
     * 根据product 查询相关主题信息
     * @param productId
     * @return
     */
    List<CmsPrefrenceAreaProductRelation> findByInfo(Long productId);

    /**
     * 批量删除
     * @param id
     */
    void multiDel(List<Long> id);
}
