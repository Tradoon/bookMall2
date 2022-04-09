package com.tradoon.bookMall.controller;

import com.tradoon.bookMall.api.CommonPage;
import com.tradoon.bookMall.api.CommonResult;
import com.tradoon.bookMall.dto.PmsProductCategoryParam;
import com.tradoon.bookMall.model.PmsProductAttributeCategory;
import com.tradoon.bookMall.model.PmsProductCategory;
import com.tradoon.bookMall.service.PmsProductCategoryService;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @ApiOperation("分页查询商品分类")
    @GetMapping("/list/{parentId}")
    public CommonResult<CommonPage<PmsProductCategory>>  getList(@PathVariable("parentId") Long parentId,
                                                                 @RequestParam(value = "pageSize",defaultValue = "5") Integer pageSize,
                                                                 @RequestParam(value = "pageNum",defaultValue = "1")Integer pageNum){
        return pmsProductCategoryService.getList(parentId,pageSize,pageNum);

    }
    @ApiOperation("修改显示状态")
    @PostMapping( "/update/showStatus")
    public CommonResult updateShowStatus(@RequestParam("ids") List<Long> ids, @RequestParam("showStatus") Integer showStatus) {
       return pmsProductCategoryService.updateShowStatus(ids, showStatus);

    }

    @ApiOperation("修改导航栏状态")
    @PostMapping( "/update/navStatus")
    public CommonResult updateNavStatus(@RequestParam("ids") List<Long> ids, @RequestParam("navStatus") Integer navStatus) {
        return pmsProductCategoryService.updateNavStatus(ids, navStatus);

    }

    @ApiOperation("删除商品分类")
    @PostMapping( "/delete/{id}")
    public CommonResult delete(@PathVariable Long id) {
        return pmsProductCategoryService.delete(id);
    }

    @ApiOperation("修改商品分类")
    @PostMapping(value = "/update/{id}")
    public CommonResult update(@PathVariable Long id,
                               @Validated
                               @RequestBody PmsProductCategoryParam productCategoryParam) {
        return  pmsProductCategoryService.update(id, productCategoryParam);

    }

}
