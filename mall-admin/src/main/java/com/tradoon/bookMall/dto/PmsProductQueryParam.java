package com.tradoon.bookMall.dto;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * author:tradoon
 * desciption:
 * date:2022/ / /
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PmsProductQueryParam {
    @ApiModelProperty("上架状态  上架状态：0->下架；1->上架 ")
    private Integer publishStatus;

    @ApiModelProperty("审核状态：0->未审核；1->审核通过")
    private Integer verifyStatus;
    @ApiModelProperty("商品名称模糊关键字")
    //todo 把前端传递的关键字名字从keyword 变为keywords
    private String keywords;
    @ApiModelProperty("商品货号")
    private String productSn;
    @ApiModelProperty("商品分类编号")
    private Long productCategoryId;
    @ApiModelProperty("商品品牌编号")
    private Long brandId;
    @ApiModelProperty("出版社编号")
    private Long publishHouseId;
}
