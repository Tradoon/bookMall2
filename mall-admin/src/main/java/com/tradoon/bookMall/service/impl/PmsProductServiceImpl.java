package com.tradoon.bookMall.service.impl;

import com.tradoon.bookMall.api.CommonResult;
import com.tradoon.bookMall.dao.*;
import com.tradoon.bookMall.dto.PmsProductParam;
import com.tradoon.bookMall.model.*;
import com.tradoon.bookMall.service.PmsProductService;
import com.tradoon.bookMall.utils.SnowflakeConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * author:tradoon
 * desciption:
 * date:2022/ / /
 */
@Service
public class PmsProductServiceImpl implements PmsProductService {
    @Autowired
    PmsMemberPriceDao pmsMemberPriceDao;

    @Autowired
    PmsProductMapper pmsProductMapper;

    @Autowired
    PmsProductLadderDao pmsProductLadderDao;

    @Autowired
    PmsSkuStockDao pmsSkuStockDao;
    @Autowired
    PmsProductFullReductionDao pmsProductFullReductionDao;

    @Autowired
    SnowflakeConfig snowflakeConfig;


    @Override
    public CommonResult create(PmsProductParam param) {
        // 提取所有属性
        PmsProduct pmsProduct=(PmsProduct) param;
        Long productId=snowflakeConfig.snowFlackId();
        pmsProduct.setBrandId(productId);
        // 插入商品
        pmsProductMapper.insertSelective(pmsProduct);
        //插入关联信息
            // 会员价格
        List<PmsMemberPrice> memberPrice = param.getMemberPriceList();
        if(!memberPrice.isEmpty()){
            List<PmsMemberPrice> listWithId = (List<PmsMemberPrice>)getListWithId(memberPrice,productId);
            pmsMemberPriceDao.insertList(listWithId);
        }
        // 阶梯价格
        List<PmsProductLadder> ladderList = param.getProductLadderList();
        if(!ladderList.isEmpty()){
            List<PmsProductLadder> listWithId = (List<PmsProductLadder>) getListWithId(ladderList, productId);
            pmsProductLadderDao.insertList(listWithId);
        }
        // 满减价格
        List<PmsProductFullReduction> fullRed = param.getProductFullReductionList();
        if(!fullRed.isEmpty()){
            List<PmsProductFullReduction> listWithId = (List<PmsProductFullReduction>) getListWithId(fullRed, productId);
            pmsProductFullReductionDao.insertList(listWithId);
        }
        //sku 库存信息
        List<PmsSkuStock> skuList = param.getSkuStockList();
        if(!skuList.isEmpty()){

        }
        // todo 特惠促销



        return null;
    }


    public List<? extends PmsProductCommonInfo > getListWithId(List<? extends  PmsProductCommonInfo> tmpList, Long productId){
        for (int i = 0; i < tmpList.size(); i++) {
            PmsProductCommonInfo   pmsProductCommonInfo = tmpList.get(i);
            pmsProductCommonInfo.setProductId(productId);
            pmsProductCommonInfo.setId(snowflakeConfig.snowFlackId());
        }
        return tmpList;
    }
}
