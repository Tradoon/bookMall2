package com.tradoon.bookMall.model;

import com.tradoon.bookMall.dto.PmsProductCategoryParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * author:tradoon
 * desciption:
 * date:2022/ / /
 */
@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PmsProductCategory extends PmsProductCategoryParam implements Serializable {
    @ApiModelProperty("分类id")
    private Long id;

    @ApiModelProperty("产品数量")
    private Integer productCount;

    @ApiModelProperty("等级")
    private Integer level;

    private static final long serialVersionUID = 1L;


}
