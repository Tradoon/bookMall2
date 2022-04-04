package com.tradoon.bookMall.service.impl;

import com.tradoon.bookMall.api.CommonResult;
import com.tradoon.bookMall.api.ResultCode;
import com.tradoon.bookMall.dao.PmsProductAttributeCategoryMapper;
import com.tradoon.bookMall.model.PmsProductAttributeCategory;
import com.tradoon.bookMall.service.PmsProductAttributeCategoryService;
import com.tradoon.bookMall.utils.SnowflakeConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * author:tradoon
 * desciption:
 * date:2022/ / /
 */
@Service
public class PmsProductAttributeCategoryServiceImpl implements PmsProductAttributeCategoryService {
@Autowired
    PmsProductAttributeCategoryMapper pmsACMapper;
@Autowired
    SnowflakeConfig snowflakeConfig;

    @Override
    public CommonResult create(String name) {
        // 是否有重复属性
        PmsProductAttributeCategory exit=pmsACMapper.findByName(name);
        if(!Objects.isNull(exit)){
            return CommonResult.failed(ResultCode.ATTRIBUTTE_NAME_REPEAT.getCode(),ResultCode.ATTRIBUTTE_NAME_REPEAT.getMessage());
        }
        // 进行属性插入
        PmsProductAttributeCategory pmsProductAttributeCategory = new PmsProductAttributeCategory();
        pmsProductAttributeCategory.setId(snowflakeConfig.snowFlackId());
        pmsProductAttributeCategory.setName(name);
//        分类下的属性如何处理呢 param count 如何计算呢
        pmsACMapper.insertAttribute(pmsProductAttributeCategory);
        return CommonResult.success(null);
    }
}
