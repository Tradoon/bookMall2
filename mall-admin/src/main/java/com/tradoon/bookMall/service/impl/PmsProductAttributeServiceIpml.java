package com.tradoon.bookMall.service.impl;

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
}
