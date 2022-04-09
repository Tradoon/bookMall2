package com.tradoon.bookMall.dao;

import com.tradoon.bookMall.model.PmsProductAttributeValue;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * author:tradoon
 * desciption:
 * date:2022/ / /
 */
@Mapper
public interface PmsProductAttributeValueDao {
    /**
     * 批量添加attribute value
     * @param listWithId
     */
    void insertList(List<PmsProductAttributeValue> listWithId);
}
