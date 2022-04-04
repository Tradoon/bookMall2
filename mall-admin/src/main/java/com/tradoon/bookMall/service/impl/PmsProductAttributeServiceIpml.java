package com.tradoon.bookMall.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tradoon.bookMall.api.CommonPage;
import com.tradoon.bookMall.api.CommonResult;
import com.tradoon.bookMall.api.ResultCode;
import com.tradoon.bookMall.dao.PmsProductAttributeMapper;
import com.tradoon.bookMall.dto.PmsProductAttributeParam;
import com.tradoon.bookMall.model.PmsProductAttribute;
import com.tradoon.bookMall.service.PmsProductAttributeService;
import io.micrometer.core.instrument.util.StringUtils;
import org.springframework.beans.BeanUtils;
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
public class PmsProductAttributeServiceIpml implements PmsProductAttributeService {
    @Autowired
    PmsProductAttributeMapper pmsAMapper;

    @Override
    public CommonResult creat(PmsProductAttributeParam productAttributeParam) {
        // 名字是否重复
        if(StringUtils.isNotBlank(productAttributeParam.getName())){
            PmsProductAttribute pmsParam = new PmsProductAttribute();
            pmsParam.setName(productAttributeParam.getName());
           List<PmsProductAttribute> findByName=pmsAMapper.selectByInfo(pmsParam);
            if(!findByName.isEmpty()){
                return  CommonResult.failed(ResultCode.ATTRIBUTTE_NAME_REPEAT.getCode(),ResultCode.ATTRIBUTTE_NAME_REPEAT.getMessage());
            }
            BeanUtils.copyProperties(pmsParam,productAttributeParam);
            pmsAMapper.create(pmsParam);
        }
//
        return CommonResult.success(null);
    }

    @Override
    public CommonResult<CommonPage<PmsProductAttribute>> getList(Long id, Integer pageSize, Integer pageNum,Integer type) {
        Page<Object> pageHelper = PageHelper.startPage(pageNum, pageSize);
        if(Objects.isNull(id)){
            return CommonResult.failed(ResultCode.ATTRIBUTTE_NAME_INPUT_NULL.getCode(), ResultCode.ATTRIBUTTE_NAME_INPUT_NULL.getMessage());
        }
        PmsProductAttribute pmsA = new PmsProductAttribute();
        pmsA.setProductAttributeCategoryId(id);

        if(!Objects.isNull(type)){
            pmsA.setType(type);
        }
        List<PmsProductAttribute> attributeList= pmsAMapper.selectByInfo(pmsA);
        PageInfo<PmsProductAttribute> pmsPageInfo = new PageInfo<>(attributeList);
        pmsPageInfo.setPageNum(pageHelper.getPageNum());
        pmsPageInfo.setPageSize(pageHelper.getPageSize());
        pmsPageInfo.setPages(pageHelper.getPages());
        pmsPageInfo.setTotal(pageHelper.getTotal());

        return CommonResult.success(new CommonPage<>(pmsPageInfo));
    }

    @Override
    public CommonResult update(Long id, PmsProductAttributeParam productAttributeParam) {

        // 查询名字是否重复
        if(StringUtils.isNotBlank(productAttributeParam.getName())){
            PmsProductAttribute pmsParam = new PmsProductAttribute();
            pmsParam.setName(productAttributeParam.getName());
            List<PmsProductAttribute> findByName=pmsAMapper.selectByInfo(pmsParam);
            if(!findByName.isEmpty()){
                for(PmsProductAttribute attributeItem:findByName){
                    if(!id.equals(attributeItem.getId())){
                        return CommonResult.failed(ResultCode.ATTRIBUTTE_NAME_REPEAT.getMessage());
                    }
                }
            }
            BeanUtils.copyProperties(pmsParam,productAttributeParam);
            pmsAMapper.update(pmsParam);
        }
        return CommonResult.success(null);
    }
}
