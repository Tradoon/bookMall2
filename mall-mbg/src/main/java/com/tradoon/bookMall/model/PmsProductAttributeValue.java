package com.tradoon.bookMall.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

/**
 * author:tradoon
 * desciption:
 * date:2022/ / /
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PmsProductAttributeValue  extends PmsProductCommonInfo implements Serializable {
//    private Long id;
//
//    private Long productId;

    private Long productAttributeId;

    @ApiModelProperty(value = "手动添加规格或参数的值，参数单值，规格有多个时以逗号隔开")
    private String value;

    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PmsProductAttributeValue av = (PmsProductAttributeValue) o;
        return  productAttributeId==av.getProductAttributeId()
                &&value==av.getValue();
    }

    @Override
    public int hashCode() {
        return Objects.hash(productAttributeId,value);
    }
}
