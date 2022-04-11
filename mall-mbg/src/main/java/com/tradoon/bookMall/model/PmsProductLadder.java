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
 * desciption:商品折扣信息
 * date:2022/ / /
 */
@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PmsProductLadder extends  PmsProductCommonInfo implements Serializable {
//    private Long id;
//
//    private Long productId;

    @ApiModelProperty(value = "满足的商品数量")
    private Integer count;

    @ApiModelProperty(value = "折扣")
    private BigDecimal discount;

    @ApiModelProperty(value = "折后价格")
    private BigDecimal price;

    private static final long serialVersionUID = 1L;
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PmsProductLadder ladder = (PmsProductLadder) o;
        return count==ladder.getCount()
                &&discount==ladder.getDiscount()
                &&price==ladder.getPrice();
    }

    @Override
    public int hashCode() {
        return Objects.hash(count, discount, price);
    }
}

