package com.tradoon.bookMall.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotEmpty;

/**
 * author:tradoon
 * desciption:商品属性
 * date:2022/ / /
 */
@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
//todo 增加自定义校验
// 就比PmsProductAttribute类少一个id的参数
public class PmsProductAttributeParam {
    @NotEmpty
    @ApiModelProperty("属性分类ID")
    private Long productAttributeCategoryId;
    @NotEmpty
    @ApiModelProperty("属性名称")
    private String name;

    @ApiModelProperty("属性选择类型：0->唯一；1->单选；2->多选")
    private Integer selectType;

    @ApiModelProperty("属性录入方式：0->手工录入；1->从列表中选取")
    private Integer inputType;
    @ApiModelProperty("可选值列表，以逗号隔开")
    private String inputList;
    private Integer sort;
    @ApiModelProperty("分类筛选样式：0->普通；1->颜色")

    private Integer filterType;
    @ApiModelProperty("检索类型；0->不需要进行检索；1->关键字检索；2->范围检索")

    private Integer searchType;
    @ApiModelProperty("相同属性产品是否关联；0->不关联；1->关联")

    private Integer relatedStatus;
    @ApiModelProperty("是否支持手动新增；0->不支持；1->支持")

    private Integer handAddStatus;
    @ApiModelProperty("属性的类型；0->规格；1->参数")

    private Integer type;
}

