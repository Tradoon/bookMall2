package com.tradoon.bookMall.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * author:tradoon
 * desciption:pms_product_category_attribute_relation表 关系封装
 * date:2022/ / /
 */
@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PmsProductCategoryAttributeRelation implements Serializable {
    private Long id;

    private Long productCategoryId;

    private Long productAttributeId;

    private static final long serialVersionUID = 1L;
}