package com.tradoon.bookMall.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * author:tradoon
 * desciption:
 * date:2022/ / /
 */
@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PmsProductCategoryParam {
    @ApiModelProperty("父分类的编号")
    private Long parentId;
    @NotEmpty
    @ApiModelProperty(value = "商品分类名称",required = true)
    private String name;
    @ApiModelProperty("分类单位")
    private String productUnit;
// 自定义valide 逻辑
    @ApiModelProperty("是否在导航栏显示")
    private Integer navStatus;

//todo 自定义valide逻辑
    @ApiModelProperty("是否进行显示")
    private Integer showStatus;
    @Min(value = 0)
    @ApiModelProperty("排序")
    private Integer sort;
    @ApiModelProperty("图标")
    private String icon;
    @ApiModelProperty("关键字")
    private String keywords;
    @ApiModelProperty("描述")
    private String description;
    @ApiModelProperty("产品相关筛选属性集合")
    private List<Long> productAttributeIdList;
}
