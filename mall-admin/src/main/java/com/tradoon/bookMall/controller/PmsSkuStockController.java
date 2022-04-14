package com.tradoon.bookMall.controller;

import com.tradoon.bookMall.api.CommonResult;
import com.tradoon.bookMall.model.PmsSkuStock;
import com.tradoon.bookMall.service.PmsSkuStockService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * author:tradoon
 * desciption:
 * date:2022/ / /
 */
@RestController
@RequestMapping("/sku")
public class PmsSkuStockController {
    @Autowired
    PmsSkuStockService pmsSkuStockService;

    @ApiOperation("根据商品ID及sku编码模糊搜索sku库存")
    @RequestMapping(value = "/{pid}", method = RequestMethod.GET)
    public CommonResult<List<PmsSkuStock>> getList(@PathVariable Long pid, @RequestParam(value = "keyword",required = false) String keyword) {
       return  pmsSkuStockService.getList(pid, keyword);

    }
    @ApiOperation("批量更新sku库存信息")
    @RequestMapping(value ="/update/{pid}",method = RequestMethod.POST)

    public CommonResult update(@PathVariable Long pid,@RequestBody List<PmsSkuStock> skuStockList){
      return  pmsSkuStockService.update(pid,skuStockList);

    }

}
