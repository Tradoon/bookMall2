package com.tradoon.bookMall.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tradoon.bookMall.api.CommonPage;
import com.tradoon.bookMall.api.CommonResult;
import com.tradoon.bookMall.api.ResultCode;
import com.tradoon.bookMall.dao.PmsProductCategoryAttributeRelationDao;
import com.tradoon.bookMall.dao.PmsProductCategoryMapper;
import com.tradoon.bookMall.dto.PmsProductCategoryParam;
import com.tradoon.bookMall.model.PmsProductAttributeCategory;
import com.tradoon.bookMall.model.PmsProductCategory;
import com.tradoon.bookMall.model.PmsProductCategoryAttributeRelation;
import com.tradoon.bookMall.model.PmsProductCategoryWithChildrenItem;
import com.tradoon.bookMall.service.PmsProductCategoryService;
import com.tradoon.bookMall.utils.SnowflakeConfig;
import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    @Override
    public CommonResult<CommonPage<PmsProductCategory>> getList(Long parentId, Integer pageSize, Integer pageNum) {
        Page<Object> pageHelper = PageHelper.startPage(pageNum, pageSize);
        PmsProductCategory pmsPC=new PmsProductCategory();
        if(Objects.isNull(parentId)){
            pmsPC.setParentId(0L);
        }else{
            pmsPC.setParentId(parentId);
        }
        List<PmsProductCategory> byInfo = pmsProductCategoryMapper.findByInfo(pmsPC);
        PageInfo<PmsProductCategory> pmsPageInfo = new PageInfo<>(byInfo);
        pmsPageInfo.setPageNum(pageHelper.getPageNum());
        pmsPageInfo.setPageSize(pageHelper.getPageSize());
        pmsPageInfo.setPages(pageHelper.getPages());
        pmsPageInfo.setTotal(pageHelper.getTotal());

        return CommonResult.success(new CommonPage<>(pmsPageInfo));
    }

    @Override
    public CommonResult updateShowStatus(List<Long> ids, Integer showStatus) {
        if(ids.isEmpty()||Objects.isNull(showStatus)){
            return CommonResult.failed(ResultCode.ATTRIBUTTE_NAME_INPUT_NULL.getCode(), ResultCode.ATTRIBUTTE_NAME_INPUT_NULL.getMessage());
        }
        PmsProductCategory pmsProductCategory = new PmsProductCategory();
        pmsProductCategory.setShowStatus(showStatus);
        pmsProductCategoryMapper.updateByInfo(ids,pmsProductCategory);

        return CommonResult.success(null);
    }

    @Override
    public CommonResult updateNavStatus(List<Long> ids, Integer navStatus) {
        if(ids.isEmpty()||navStatus==null){
            return CommonResult.failed(ResultCode.ATTRIBUTTE_NAME_INPUT_NULL.getCode(), ResultCode.ATTRIBUTTE_NAME_INPUT_NULL.getMessage());
        }
        PmsProductCategory pmsProductCategory = new PmsProductCategory();
        pmsProductCategory.setNavStatus(navStatus);
        pmsProductCategoryMapper.updateByInfo(ids,pmsProductCategory);
        return CommonResult.success(null);
    }

    @Override
    public CommonResult update(Long id, PmsProductCategoryParam productCategoryParam) {
        PmsProductCategory ppc = new PmsProductCategory();
        BeanUtils.copyProperties(productCategoryParam,ppc);
        if(StringUtils.isNotBlank(ppc.getName())){
            PmsProductCategory tmpPPC = new PmsProductCategory();
            tmpPPC.setName(ppc.getName());
            List<PmsProductCategory> infoByName = pmsProductCategoryMapper.findByInfo(tmpPPC);
            for (PmsProductCategory pmsProductCategory : infoByName) {
                if(pmsProductCategory.getId()!=id) {
                    return CommonResult.failed(ResultCode.ATTRIBUTTE_NAME_REPEAT.getMessage());
                }
            }
        }
        ArrayList<Long> ids = new ArrayList<>();
        ids.add(id);
        pmsProductCategoryMapper.updateByInfo(ids,ppc);
        return  CommonResult.success(null);
    }

    @Override
    public CommonResult delete(Long id) {
        if(Objects.isNull(id)){
            return CommonResult.failed(ResultCode.ATTRIBUTTE_NAME_INPUT_NULL.getMessage());
        }
        pmsProductCategoryMapper.delete(id);
        return CommonResult.success(null);
    }

    @Override
    public  CommonResult<List<PmsProductCategoryWithChildrenItem>> listWithChildren() {
        //

        Long parentId=0L;

        PmsProductCategory parentCandition = new PmsProductCategory();
        parentCandition.setId(parentId);
        List<PmsProductCategoryWithChildrenItem> res = pmsProductCategoryMapper.listWithChildren(parentId);
        return CommonResult.success(res);
    }
}
