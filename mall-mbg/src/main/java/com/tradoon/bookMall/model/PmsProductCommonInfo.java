package com.tradoon.bookMall.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * author:tradoon
 * desciption:
 * date:2022/ / /
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PmsProductCommonInfo implements Serializable {
    private Long id;

    private Long productId;

    private static final long serialVersionUID = 1L;

}
