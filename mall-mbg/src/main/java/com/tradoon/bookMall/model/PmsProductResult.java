package com.tradoon.bookMall.model;

import com.tradoon.bookMall.dto.PmsProductParam;
import io.swagger.annotations.ApiModelProperty;
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
public class PmsProductResult  extends PmsProductParam {
    @ApiModelProperty("商品所选分类的父id")
    private Long cateParentId;

}
