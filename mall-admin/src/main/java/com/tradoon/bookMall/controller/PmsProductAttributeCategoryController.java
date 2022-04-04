package com.tradoon.bookMall.controller;

import com.tradoon.bookMall.api.CommonPage;
import com.tradoon.bookMall.api.CommonResult;
import com.tradoon.bookMall.model.PmsProductAttributeCategory;
import com.tradoon.bookMall.service.PmsProductAttributeCategoryService;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @ApiOperation("分页获取所有商品属性分类")
    @GetMapping("/list")
    public CommonResult<CommonPage<PmsProductAttributeCategory>> getList(@RequestParam(value = "pageSize",defaultValue = "5")Integer pageSize,
                                                                         @RequestParam(value = "pageNum",defaultValue ="1")Integer pageNum){
        return  pmsAbCService.selectList(pageNum,pageSize);
    }







}
