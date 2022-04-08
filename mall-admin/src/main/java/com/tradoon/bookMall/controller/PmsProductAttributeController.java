package com.tradoon.bookMall.controller;

import com.tradoon.bookMall.api.CommonPage;
import com.tradoon.bookMall.api.CommonResult;
import com.tradoon.bookMall.dto.PmsProductAttributeParam;
import com.tradoon.bookMall.model.PmsProductAttribute;
import com.tradoon.bookMall.service.PmsProductAttributeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * author:tradoon
 * desciption:商品属性controller
 * date:2022/
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

    @ApiOperation("根据分类查询属性列表或参数列表")
    @GetMapping(value = "/list/{cid}")
    public CommonResult<CommonPage<PmsProductAttribute>> getList(@PathVariable("cid")Long id,
                                                                 @RequestParam(value ="pageSize",defaultValue = "5")Integer pageSize
                                                            ,@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                                 @RequestParam("type") Integer type) {
        return pmsAS.getList(id,pageSize,pageNum,type);
    }

    @ApiOperation("修改商品属性信息")
   @PostMapping(value = "/update/{id}")
    public CommonResult update(@PathVariable Long id, @RequestBody PmsProductAttributeParam productAttributeParam) {
        return pmsAS.update(id, productAttributeParam);
    }

    @ApiOperation("批量删除商品属性")
    @PostMapping(value = "/delete")
    public CommonResult delete(@RequestParam List<Long> ids) {
        return  pmsAS.delete(ids);

    }


}
