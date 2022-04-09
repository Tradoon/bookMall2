package com.tradoon.bookMall.dao;

import com.tradoon.bookMall.model.PmsProductLadder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * author:tradoon
 * desciption:阶梯价格
 * date:2022/ / /
 */
@Mapper
public interface PmsProductLadderDao {
    /**
     * 增加阶级信息
     * @param ladderList
     */
    void insertList(@Param("list") List<PmsProductLadder> ladderList);
}
