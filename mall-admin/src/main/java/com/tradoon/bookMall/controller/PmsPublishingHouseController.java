package com.tradoon.bookMall.controller;

import com.tradoon.bookMall.api.CommonPage;
import com.tradoon.bookMall.api.CommonResult;
import com.tradoon.bookMall.model.PulishingHouse;
import com.tradoon.bookMall.service.PmsPublishingHouseService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * author:tradoon
 * desciption: 二手书出版社
 * date:2022/ / /
 */
@RestController
@RequestMapping("/brand")
public class PmsPublishingHouseController {
    @Autowired
    PmsPublishingHouseService pmsPublishingHouseService;
    @ApiOperation(value = " 出版社信息查询",httpMethod = "Get")
    @GetMapping("/list")
    public CommonResult<CommonPage<PulishingHouse>> playingHouseInfo(@RequestParam("keyword") String keyword,
                                                                     @RequestParam("pageNum") int pageNum,@RequestParam("pageSize") int pageSize){
        return pmsPublishingHouseService.houseList(keyword,pageNum,pageSize);

    }

}
