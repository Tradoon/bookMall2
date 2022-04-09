package com.tradoon.bookMall.service.impl;

import com.tradoon.bookMall.api.CommonResult;
import com.tradoon.bookMall.dao.PmsMemberPriceDao;
import com.tradoon.bookMall.dao.PmsProductFullReductionDao;
import com.tradoon.bookMall.dao.PmsProductLadderDao;
import com.tradoon.bookMall.dao.PmsProductMapper;
import com.tradoon.bookMall.dto.PmsProductParam;
import com.tradoon.bookMall.model.PmsMemberPrice;
import com.tradoon.bookMall.model.PmsProduct;
import com.tradoon.bookMall.model.PmsProductFullReduction;
import com.tradoon.bookMall.model.PmsProductLadder;
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
            insertMemberPrice(memberPrice,productId);
        }
        // 阶梯价格
        List<PmsProductLadder> ladderList = param.getProductLadderList();
        if(!ladderList.isEmpty()){
            insertLadder(ladderList,productId);
        }
        // 满减价格
        List<PmsProductFullReduction> fullRed = param.getProductFullReductionList();
        if(!fullRed.isEmpty()){
            insertFullReduction(fullRed,productId);
        }

        // todo 特惠促销



        return null;
    }

    private void insertFullReduction(List<PmsProductFullReduction> fullRed,Long productId) {
        for(int i=0;i<fullRed.size();i++){
            PmsProductFullReduction pmsFull = fullRed.get(i);
            pmsFull.setId(snowflakeConfig.snowFlackId());
            pmsFull.setProductId(productId);
        }
        pmsProductFullReductionDao.insertList(fullRed);

    }

    private void insertLadder(List<PmsProductLadder> ladderList,Long productId) {
        for (int i = 0; i < ladderList.size(); i++) {
            PmsProductLadder pmsProductLadder = ladderList.get(i);
            pmsProductLadder.setId(snowflakeConfig.snowFlackId());
            pmsProductLadder.setProductId(productId);
        }
        pmsProductLadderDao.insertList(ladderList);
    }

    public void insertMemberPrice(List<PmsMemberPrice> memberPrice,Long productId){
        for (int i = 0; i < memberPrice.size(); i++) {
            PmsMemberPrice pmsMemberPrice = memberPrice.get(i);
            pmsMemberPrice.setId(snowflakeConfig.snowFlackId());
            pmsMemberPrice.setProductId(productId);
        }
        pmsMemberPriceDao.insertList(memberPrice);


    }
}
