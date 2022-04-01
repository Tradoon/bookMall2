package com.tradoon.bookMall.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotEmpty;

/**
 * author:tradoon
 * desciption:更改密码所需的参数
 * date:2022/ / /
 */
//todo 将所需的参数改的username改成id
@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateAdminPasswordParam {
    @NotEmpty
    @ApiModelProperty(value = "用户名",required = true)
    private  String username;

    @NotEmpty
    @ApiModelProperty(value = "旧密码",required = true)
    private String oldPassword;

    @NotEmpty
    @ApiModelProperty(value = "新密码",required = true)
    private String newPassword;
}
