package com.tradoon.bookMall.api;

import com.github.pagehelper.PageInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * author:tradoon
 * desciption:分页相关类的封装
 * date:2022/02/09
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonPage<T> {
    /**
     * 当前页码
     */
    private Integer pageNum;
    /**
     * 每页数量
     */
    private Integer pageSize;
    /**
     * 总页数
     */
    private Integer totalPage;
    /**
     * 总条数
     */
    private Long total;
    /**
     * 分页数据
     */
    private List<T> list;


    public CommonPage(PageInfo<T> pageInfo){
        pageNum=pageInfo.getPageNum();
        list=pageInfo.getList();
        total=pageInfo.getTotal();
        totalPage=pageInfo.getPages();
        pageSize=pageInfo.getPageSize();

    }
    //直接使用pagehelper进行分页，然后分页的数据进行返回
    //将排好序的数据进行分页

}
