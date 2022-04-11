package com.tradoon.bookMall.dao;

import com.tradoon.bookMall.model.PmsProductFullReduction;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;

/**
 * author:tradoon
 * desciption:满减信息
 * date:2022/ / /
 */
@Mapper
public interface PmsProductFullReductionDao {
    /**
     * 批量插入满减信息
     * @param fullRed
     */
    void insertList(List<PmsProductFullReduction> fullRed);

    /**
     * 根据project id查询
     * @param id
     * @return
     */
    List<PmsProductFullReduction> findByInfo(Long id);

    /**
     * 批量删除
     * @param delListProdctId
     */
    void delMulti(@Param("id") ArrayList<Long> delListProdctId);
}
