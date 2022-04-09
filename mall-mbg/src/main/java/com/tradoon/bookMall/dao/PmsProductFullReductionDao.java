package com.tradoon.bookMall.dao;

import com.tradoon.bookMall.model.PmsProductFullReduction;
import org.apache.ibatis.annotations.Mapper;

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
}
