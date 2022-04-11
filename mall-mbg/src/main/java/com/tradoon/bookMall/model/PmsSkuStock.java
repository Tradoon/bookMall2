package com.tradoon.bookMall.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * author:tradoon
 * desciption: 商品库存信息
 * date:2022/ / /
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class PmsSkuStock extends PmsProductCommonInfo implements Serializable {
//    private Long id;
//
//    private Long productId;

    @ApiModelProperty(value = "sku编码")
    private String skuCode;

    private BigDecimal price;

    @ApiModelProperty(value = "库存")
    private Integer stock;

    @ApiModelProperty(value = "预警库存")
    private Integer lowStock;

    @ApiModelProperty(value = "展示图片")
    private String pic;

    @ApiModelProperty(value = "销量")
    private Integer sale;

    @ApiModelProperty(value = "单品促销价格")
    private BigDecimal promotionPrice;

    @ApiModelProperty(value = "锁定库存")
    private Integer lockStock;

    @ApiModelProperty(value = "商品销售属性，json格式")
    private String spData;

    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PmsSkuStock skuStockO = (PmsSkuStock) o;
        return stock==skuStockO.getStock()
                &&skuCode==skuStockO.getSkuCode()
                &&price==skuStockO.getPrice()
                &&lowStock== skuStockO.getLowStock()
                &&pic==skuStockO.getPic()
                &&sale== skuStockO.getSale()
                &&promotionPrice==skuStockO.getPromotionPrice()
                &&lockStock==skuStockO.getLockStock()
                &&spData==skuStockO.getSpData();
    }

    @Override
    public int hashCode() {
        return Objects.hash(stock,skuCode,price,lockStock,pic,sale,promotionPrice,lockStock,spData);
    }



}
