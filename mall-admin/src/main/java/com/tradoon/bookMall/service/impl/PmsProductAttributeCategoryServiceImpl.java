package com.tradoon.bookMall.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tradoon.bookMall.api.CommonPage;
import com.tradoon.bookMall.api.CommonResult;
import com.tradoon.bookMall.api.ResultCode;
import com.tradoon.bookMall.dao.PmsProductAttributeCategoryMapper;
import com.tradoon.bookMall.model.PmsProductAttributeCategory;
import com.tradoon.bookMall.service.PmsProductAttributeCategoryService;
import com.tradoon.bookMall.utils.SnowflakeConfig;
import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
        PmsProductAttributeCategory pmsAC = new PmsProductAttributeCategory();
        if(!StringUtils.isNotBlank(name)){
         return CommonResult.failed(ResultCode.ATTRIBUTTE_NAME_INPUT_NULL.getCode(),ResultCode.ATTRIBUTTE_NAME_INPUT_NULL.getMessage());
        }else{
            pmsAC.setName(name);
        }
        List<PmsProductAttributeCategory> exit=pmsACMapper.findByList(pmsAC);
        if(!exit.isEmpty()){
            return CommonResult.failed(ResultCode.ATTRIBUTTE_NAME_REPEAT.getCode(),ResultCode.ATTRIBUTTE_NAME_REPEAT.getMessage());
        }
        // 进行属性插入
        PmsProductAttributeCategory pmsProductAttributeCategory = new PmsProductAttributeCategory();
        pmsProductAttributeCategory.setId(snowflakeConfig.snowFlackId());
        pmsProductAttributeCategory.setName(name);
//        todo 分类下的属性如何处理呢 param count 如何计算呢
        pmsACMapper.insertAttribute(pmsProductAttributeCategory);
        return CommonResult.success(null);
    }

    @Override
    public CommonResult<CommonPage<PmsProductAttributeCategory>> selectList(Integer pageNum, Integer pageSize) {
        Page<Object> pageHelper = PageHelper.startPage(pageNum, pageSize);
        PmsProductAttributeCategory pmsAc = new PmsProductAttributeCategory();
        List<PmsProductAttributeCategory> ACategoryList=pmsACMapper.findByList(pmsAc);

        PageInfo<PmsProductAttributeCategory> info = new PageInfo<>(ACategoryList);
        info.setPages(pageHelper.getPages());
        info.setTotal(pageHelper.getTotal());
        info.setPageSize(pageHelper.getPageSize());
        info.setPageNum(info.getPageNum());

        return CommonResult.success(new CommonPage<>(info));
    }
}
