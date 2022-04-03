package com.tradoon.bookMall.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

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
public class PulishingHouse {

    @ApiModelProperty("出版社id")
    private Long id ;

    @ApiModelProperty("出版社名字")
    private String publishhouseName;

    @ApiModelProperty("出版社所在城市")
    private String city;

    @ApiModelProperty("出版社类型")
    private Integer type;

    @ApiModelProperty("出版社类型对应的名字")
    private String typeName;


    // todo 加正则表达式
    @ApiModelProperty("出版社编号")
    private  String isbn;

    // todo 加正则表达式
    @ApiModelProperty("统一书号")
    private  String csbn;

    @ApiModelProperty("")
    private Date  createTime;

    @ApiModelProperty("更改时间")
    private  Date  updateTime;

    @ApiModelProperty("备注")
    private  String remark;

    @ApiModelProperty("状态")
    private Integer status;


}
