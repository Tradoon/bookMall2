package com.tradoon.bookMall.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * author:tradoon
 * desciption:
 * date:2022/ / /
 */
@Component
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PmsMemberPrice extends PmsProductCommonInfo implements Serializable {
//    private Long id;
//
//    private Long productId;

    private Long memberLevelId;

    @ApiModelProperty(value = "会员价格")
    private BigDecimal memberPrice;

    private String memberLevelName;

    private static final long serialVersionUID = 1L;

}
