package com.tradoon.bookMall.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotEmpty;
import java.util.Date;

/**
 * author:tradoon
 * desciption:
 * date:2022/ / /
 */
@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PublishingHouse {

    @ApiModelProperty("出版社id")
    private Long id ;

    @NotEmpty
    @ApiModelProperty("出版社名字")
    private String publishhouseName;

    @ApiModelProperty("出版社所在城市")
    private String city;

    // 前后端协定type的值,才能得到对应的type
    @ApiModelProperty("出版社类型")
    private Integer type;

    @ApiModelProperty("出版社类型对应的名字")
    private String typeName;


    // todo 加正则表达式
    @NotEmpty
    @ApiModelProperty("出版社编号")
    private  String isbn;

    // todo 加正则表达式
    @NotEmpty
    @ApiModelProperty("统一书号")
    private  String csbn;

    @ApiModelProperty("创建时间")
    private Date  createTime;

    @ApiModelProperty("更改时间")
    private  Date  updateTime;

    @ApiModelProperty("备注")
    private  String remark;

    @ApiModelProperty("状态")
    private Integer status;


}
