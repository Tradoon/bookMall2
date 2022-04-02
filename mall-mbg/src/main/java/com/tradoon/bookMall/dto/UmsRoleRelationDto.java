package com.tradoon.bookMall.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * author:tradoon
 * desciption:
 * date:2022/ / /
 */
@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UmsRoleRelationDto {

    @ApiModelProperty(" id")
    private Long id;

    @ApiModelProperty("用户id")
    private Long adminId;


    @ApiModelProperty("角色id")
    private Long roleId;
}
