package com.tradoon.bookMall.service.impl;

import com.tradoon.bookMall.api.CommonResult;
import com.tradoon.bookMall.dao.PmsSkuStockMapper;
import com.tradoon.bookMall.model.PmsSkuStock;
import com.tradoon.bookMall.service.PmsSkuStockService;
import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * author:tradoon
 * desciption:
 * date:2022/ / /
 */
@Service
public class PmsSkuStockServiceImpl implements PmsSkuStockService {
    @Autowired
    PmsSkuStockMapper pmsSkuStockMapper;
    @Override
    public CommonResult<List<PmsSkuStock>> getList(Long pid, String keyword) {
        PmsSkuStock pmsSkuStock = new PmsSkuStock();
        if(pid!=null)
        pmsSkuStock.setProductId(pid);
        if(StringUtils.isNotBlank(keyword)){
            pmsSkuStock.setSkuCode(keyword);
        }
        List<PmsSkuStock> pmsSkuStockList=  pmsSkuStockMapper.findByInfo(pmsSkuStock);
        return CommonResult.success(pmsSkuStockList);
    }

    @Override
    public CommonResult update(Long pid, List<PmsSkuStock> skuStockList) {
        pmsSkuStockMapper.update(skuStockList);
       return CommonResult.success(null);
    }
}
