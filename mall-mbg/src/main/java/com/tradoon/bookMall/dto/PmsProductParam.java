package com.tradoon.bookMall.dto;

import com.tradoon.bookMall.model.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * author:tradoon
 * desciption:商品信息
 * date:2022/ / /
 */
@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PmsProductParam extends PmsProduct {
    @ApiModelProperty("商品阶梯价格设置")
    private List<PmsProductLadder> productLadderList;

    @ApiModelProperty("商品满减价格设置")
    private List<PmsProductFullReduction> productFullReductionList;

    @ApiModelProperty("商品会员价格设置")
    private List<PmsMemberPrice> memberPriceList;
    @ApiModelProperty("商品的sku库存信息")
    private List<PmsSkuStock> skuStockList;
    @ApiModelProperty("商品参数及自定义规格属性")
    private List<PmsProductAttributeValue> productAttributeValueList;
    @ApiModelProperty("专题和商品关系")
    private List<CmsSubjectProductRelation> subjectProductRelationList;
    @ApiModelProperty("优选专区和商品的关系")
    private List<CmsPrefrenceAreaProductRelation> prefrenceAreaProductRelationList;


}
