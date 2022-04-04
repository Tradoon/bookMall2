package com.tradoon.bookMall.controller;

import com.tradoon.bookMall.api.CommonResult;
import com.tradoon.bookMall.service.PmsProductAttributeCategoryService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * author:tradoon
 * desciption:用户对用户类型的
 * date:2022/ / /
 */
@RestController
@RequestMapping("/productAttribute/category")
public class PmsProductAttributeCategoryController {
    @Autowired
    PmsProductAttributeCategoryService pmsAbCService;

    @ApiOperation("添加商品属性分类")
    @PostMapping(value = "/create")
    @ResponseBody
    public CommonResult create(@RequestParam String name) {
       return  pmsAbCService.create(name);

    }






}
