package com.tradoon.bookMall.controller;

import com.tradoon.bookMall.api.CommonResult;
import com.tradoon.bookMall.dto.PmsProductAttributeParam;
import com.tradoon.bookMall.service.PmsProductAttributeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * author:tradoon
 * desciption:
 * date:2022/ / /
 */
@RestController
@Api("PmsProductAttributeController")
@RequestMapping("/productAttribute")
public class PmsProductAttributeController {
    @Autowired
    PmsProductAttributeService pmsAS;

    @ApiOperation("添加商品属性信息")
    @PostMapping("/create")
    public CommonResult create(@RequestBody PmsProductAttributeParam productAttributeParam) {
     return  pmsAS.creat(productAttributeParam);
    }

}
