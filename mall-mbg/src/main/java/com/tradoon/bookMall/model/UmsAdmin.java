package com.tradoon.bookMall.model;

/**
 * author:tradoon
 * desciption:
 * date:2022/ / /
 */

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Date;

/**
 * author:tradoon
 * desciption: 用户实体类
 * date:2022/02/09
 */
@Component
@Data
public class UmsAdmin implements Serializable {
    @ApiModelProperty(value = "id")
    private long id;

    @NotEmpty
    @ApiModelProperty(value = "用户名",required = true)
    private String username;

    @NotEmpty
    @ApiModelProperty(value = "加密后密码",required = true)
    private String password;

    @ApiModelProperty(value = "头像")
    private String icon;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "昵称")
    private String nickName;

    @ApiModelProperty(value = "备注信息")
    private String note;

    @ApiModelProperty(value = "用户注册实践")
    private Date createTime;

    @ApiModelProperty(value = "上一次登录实践")
    private Date loginTime;

    private static final long serialVersionUID=1L;
    @Override
    public String toString(){
        StringBuilder data = new StringBuilder();
        data.append(getClass().getSimpleName());
        data.append(":[");
        data.append("id=").append(id);
        data.append(",username=").append(username);
        data.append(",password=").append(password);
        data.append(",icon=").append(icon);
        data.append(",emmail").append(email);
        data.append(", nickName=").append(nickName);
        data.append(", note=").append(note);
        data.append(", createTime=").append(createTime);
        data.append(", loginTime=").append(loginTime);
        data.append(", serialVersionUID=").append(serialVersionUID);
        data.append("]");
        return data.toString();
        //todo 是否可以使用valueof

    }
}