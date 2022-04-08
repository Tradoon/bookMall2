package com.tradoon.bookMall.controller;

import com.tradoon.bookMall.api.CommonResult;
import com.tradoon.bookMall.dto.PmsProductCategoryParam;
import com.tradoon.bookMall.service.PmsProductCategoryService;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * author:tradoon
 * desciption: 商品分类相关接口
 * date:2022/ / /
 */
@RestController
@RequestMapping("/productCategory")
public class PmsProductCategoryController {
    @Autowired
    PmsProductCategoryService pmsProductCategoryService;

    @ApiOperation("添加商品分类")
    @PostMapping ("/create")
    public CommonResult create(@Validated @RequestBody PmsProductCategoryParam param) {
        return  pmsProductCategoryService.create(param);

    }
}
