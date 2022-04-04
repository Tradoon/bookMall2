package com.tradoon.bookMall.dao;

import com.tradoon.bookMall.model.PmsProductAttribute;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * author:tradoon
 * desciption:
 * date:2022/ / /
 */
@Mapper
public interface PmsProductAttributeMapper {
    /**
     * 根据相关属性的各个列值查询属性信息
     * @param pmsParam
     * @return
     */
    List<PmsProductAttribute> selectByInfo(PmsProductAttribute pmsParam);

    /**
     * 新增属性信息
     * @param pmsParam
     */
    void create(PmsProductAttribute pmsParam);

    /**
     * 更改属性信息
     * @param pmsParam
     */
    void update(PmsProductAttribute pmsParam);

    /**
     * 删除属性信息
     * @param id
     */
    void delAttribute(@Param("acid") Long acid,@Param("id") List<Long> id);


}
