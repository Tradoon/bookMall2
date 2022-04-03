package com.tradoon.bookMall.controller;

import com.tradoon.bookMall.api.CommonPage;
import com.tradoon.bookMall.api.CommonResult;
import com.tradoon.bookMall.model.PublishingHouse;
import com.tradoon.bookMall.service.PmsPublishingHouseService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public CommonResult<CommonPage<PublishingHouse>> playingHouseInfo(@RequestParam("keyword") String keyword,
                                                                      @RequestParam("pageNum") int pageNum, @RequestParam("pageSize") int pageSize){
        return pmsPublishingHouseService.houseList(keyword,pageNum,pageSize);
    }
    @ApiOperation(value="添加出版社名字",httpMethod = "POST")
    @PostMapping("/create")
    public CommonResult create(@RequestBody PublishingHouse publishingHouse){
        return pmsPublishingHouseService.insertPublishingHouse(publishingHouse);

    }

}
