package com.tradoon.bookMall.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * author:tradoon
 * desciption:满减信息
 * date:2022/ / /
 */
@Component
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PmsProductFullReduction extends PmsProductCommonInfo implements Serializable {
//    private Long id;
//
//    private Long productId;

    private BigDecimal fullPrice;

    private BigDecimal reducePrice;

    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PmsProductFullReduction reduction = (PmsProductFullReduction) o;
        return  fullPrice==reduction.getFullPrice()
                &&reducePrice==reduction.getReducePrice();
    }

    @Override
    public int hashCode() {
        return Objects.hash(fullPrice,reducePrice);
    }

}
