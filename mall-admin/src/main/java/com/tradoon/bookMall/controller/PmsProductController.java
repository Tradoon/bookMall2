package com.tradoon.bookMall.controller;

import com.tradoon.bookMall.api.CommonPage;
import com.tradoon.bookMall.api.CommonResult;
import com.tradoon.bookMall.dto.PmsProductParam;
import com.tradoon.bookMall.dto.PmsProductQueryParam;
import com.tradoon.bookMall.model.PmsProduct;
import com.tradoon.bookMall.model.PmsProductResult;
import com.tradoon.bookMall.service.PmsProductService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * author:tradoon
 * desciption:商品Controller类
 * date:2022/ / /
 */
@RestController
@RequestMapping("/product")
public class PmsProductController {
    @Autowired
    PmsProductService pmsProductService;

    @ApiOperation("创建商品")
    @PostMapping("/create")
    public CommonResult create(@RequestBody PmsProductParam product){
        return pmsProductService.create(product);

    }

    @ApiOperation("分页查询商品")
    @GetMapping("/list")
    public CommonResult<CommonPage<PmsProduct>> getList(PmsProductQueryParam productQueryParam,
                                                        @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                                        @RequestParam(value = "pageNum", defaultValue = "1")Integer pageNum){
    return  pmsProductService.getPageList(productQueryParam,pageSize,pageNum);
    }

    @ApiOperation("更新商品")
    @PostMapping("/update/{id}")
    public CommonResult update(@PathVariable Long id, @RequestBody PmsProductParam productParam){
        return pmsProductService.update(id,productParam);
    }

    @ApiOperation("根据商品id获取商品编辑信息")
    @GetMapping("/updateInfo/{id}")
    public CommonResult<PmsProductResult>  getUpdateInfo(@PathVariable Long id){
        return pmsProductService.getUpdateInfo(id);
    }

    @ApiOperation("批量上下架商品")
    @PostMapping("/update/publishStatus")
    @ResponseBody
    public CommonResult updatePublishStatus(@RequestParam("ids") List<Long> ids,
                                            @RequestParam("publishStatus") Integer publishStatus) {
      return  pmsProductService.updatePublishStatus(ids, publishStatus);

    }
    @ApiOperation("批量上下架商品")
    @PostMapping("update/recommendStatus")
    @ResponseBody
    public CommonResult updateRecommendStatus(@RequestParam("ids") List<Long> ids,
                                            @RequestParam("recommendStatus") Integer recommendStatus) {
        return  pmsProductService.updateRecommendStatus(ids, recommendStatus);

    }

    @ApiOperation("批量上下架商品")
    @PostMapping("update/newStatus")
    public CommonResult updateNewStatus(@RequestParam("ids") List<Long> ids,
                                              @RequestParam("newStatus") Integer newStatus) {
        return  pmsProductService.updateNewStatus(ids, newStatus);

    }

    @ApiOperation("商品批量进入回收站")
    @PostMapping("update/deleteStatus")
    public CommonResult updateDeleteStatus(@RequestParam("ids") List<Long> ids,
                                        @RequestParam("deleteStatus") Integer deleteStatus) {
        return  pmsProductService.updateDeleteStatus(ids, deleteStatus);

    }


}
