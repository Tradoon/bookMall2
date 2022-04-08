package com.tradoon.bookMall.service.impl;

import com.tradoon.bookMall.api.CommonResult;
import com.tradoon.bookMall.api.ResultCode;
import com.tradoon.bookMall.dao.PmsProductCategoryAttributeRelationDao;
import com.tradoon.bookMall.dao.PmsProductCategoryMapper;
import com.tradoon.bookMall.dto.PmsProductCategoryParam;
import com.tradoon.bookMall.model.PmsProductCategory;
import com.tradoon.bookMall.model.PmsProductCategoryAttributeRelation;
import com.tradoon.bookMall.service.PmsProductCategoryService;
import com.tradoon.bookMall.utils.SnowflakeConfig;
import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * author:tradoon
 * desciption:
 * date:2022/ / /
 */
@Service
public class PmsProductCategoryServiceImpl implements PmsProductCategoryService {
    @Autowired
    PmsProductCategoryMapper pmsProductCategoryMapper;
    @Autowired
    SnowflakeConfig snowflakeConfig;

    @Autowired
    PmsProductCategoryAttributeRelationDao pmsProductCategoryAttributeRelationDao;
    @Override
    //todo 添加事务
    public CommonResult create(PmsProductCategoryParam param) {
        PmsProductCategory pmsProductCategory = new PmsProductCategory();
        List<Long> productAttributeIdList = param.getProductAttributeIdList();

        BeanUtils.copyProperties(param,pmsProductCategory);
        // 判断名字是否重复
        if(StringUtils.isNotBlank(pmsProductCategory.getName())){
            PmsProductCategory tmpCategory = new PmsProductCategory();
            tmpCategory.setName(pmsProductCategory.getName());
           List<PmsProductCategory> listByName= pmsProductCategoryMapper.findByInfo(tmpCategory);
           if(!listByName.isEmpty()){
               return CommonResult.failed(ResultCode.NameRepeated.getCode(), ResultCode.NameRepeated.getMessage());
           }

            // 不重复直接进行添加
                //计算产品数量
            //todo 常数变为final 修饰的常量
                pmsProductCategory.setProductCount(0);
                //计算产品等级

            if(pmsProductCategory.getParentId()==null){
                pmsProductCategory.setParentId(0L);
            }
            // 生成分类id
            long catId = snowflakeConfig.snowFlackId();
            pmsProductCategory.setId(catId);
            pmsProductCategoryMapper.insertInfo(pmsProductCategory);
            // 添加到pms_product_category_attribute_relation 表
            List<PmsProductCategoryAttributeRelation> ppacList=new ArrayList<>();
            if(!productAttributeIdList.isEmpty()){
                for (Long attributeId : productAttributeIdList) {
                    PmsProductCategoryAttributeRelation tmpACRelation = new PmsProductCategoryAttributeRelation();
                    tmpACRelation.setId(snowflakeConfig.snowFlackId());
                    tmpACRelation.setProductCategoryId(catId);
                    tmpACRelation.setProductAttributeId(attributeId);
                    ppacList.add(tmpACRelation);
                }
                pmsProductCategoryAttributeRelationDao.insertRelation(ppacList);
            }



        }



        return CommonResult.success(null);
    }
}
