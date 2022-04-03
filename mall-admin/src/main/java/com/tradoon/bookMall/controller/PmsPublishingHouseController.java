package com.tradoon.bookMall.controller;

import com.tradoon.bookMall.api.CommonPage;
import com.tradoon.bookMall.api.CommonResult;
import com.tradoon.bookMall.model.PublishingHouse;
import com.tradoon.bookMall.service.PmsPublishingHouseService;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
    public CommonResult create(@Valid  @RequestBody PublishingHouse publishingHouse){
        return pmsPublishingHouseService.insertPublishingHouse(publishingHouse);

    }

    @ApiModelProperty("删除出版社")
    @GetMapping("/delete/{id}")
    public CommonResult delete(@PathVariable("id")Long id){
        return pmsPublishingHouseService.deletePublishHouse(id);

    }

    @ApiOperation(value = "更新出版社信息",httpMethod = "POST")
    @PostMapping("/update/{id}")
    public CommonResult  update (@PathVariable("id")Long id,@RequestBody PublishingHouse publishingHouse){
        return pmsPublishingHouseService.updatePublishHouse(id,publishingHouse);
    }

}
