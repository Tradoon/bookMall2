package com.tradoon.bookMall.model;

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
@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class UmsResource implements Serializable {
    @ApiModelProperty("用户id")
    Long adminId;

    @ApiModelProperty("用户姓名")
    String username;

    @ApiModelProperty("角色id")
    Long roleId;

    @ApiModelProperty("角色名字")
    String roleName;

    @ApiModelProperty("资源id")
    Long resourceId;

    @ApiModelProperty("资源名字")
    String resourceName;

    @ApiModelProperty("权限标志符")
    String value;

    @ApiModelProperty("分类id")
    Long categoryId;

    @ApiModelProperty("访问路径")
    String url;

}
