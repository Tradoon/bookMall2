package com.tradoon.bookMall.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tradoon.bookMall.api.CommonPage;
import com.tradoon.bookMall.api.CommonResult;
import com.tradoon.bookMall.api.Enum.PublishingHouseType;
import com.tradoon.bookMall.api.ResultCode;
import com.tradoon.bookMall.dao.PmsPublishingHouseMapper;
import com.tradoon.bookMall.model.PublishingHouse;
import com.tradoon.bookMall.service.PmsPublishingHouseService;
import com.tradoon.bookMall.utils.SnowflakeConfig;
import io.swagger.annotations.ApiModelProperty;
import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * author:tradoon
 * desciption:
 * date:2022/ / /
 */
@Service
public class PmsPublishingHouseServiceImpl implements PmsPublishingHouseService {
    @Autowired
    PmsPublishingHouseMapper pmsPublishingHouseMapper;
    @Autowired
    SnowflakeConfig snowflakeConfig;
    @Override
    public CommonResult<CommonPage<PublishingHouse>> houseList(String keyword, int pageNum, int pageSize) {
        // 分页
        Page<Object> pageHelper = PageHelper.startPage(pageNum, pageSize);
        //查询
         List<PublishingHouse> housesList=pmsPublishingHouseMapper.houseList(keyword);

        // 结果封装
        List<PublishingHouse> houses=new ArrayList<>();
        for(PublishingHouse houseItem:housesList){
            Integer type=houseItem.getType();
            if(type!=null){
                String typeNameByType = PublishingHouseType.getTypeNameByType(type);
                if(StringUtils.isNotBlank(typeNameByType))
                houseItem.setTypeName(typeNameByType);
            }
            houses.add(houseItem);
        }
        PageInfo<PublishingHouse> houseInfo = new PageInfo<>(houses);
        houseInfo.setPageNum(pageHelper.getPageNum());
        houseInfo.setPageSize(pageHelper.getPageSize());
        houseInfo.setTotal(pageHelper.getTotal());
        houseInfo.setPages(pageHelper.getPages());
        return CommonResult.success(new CommonPage<>(houseInfo));
    }


    @Override
    public CommonResult insertPublishingHouse(PublishingHouse publishingHouse) {
        PublishingHouse tmphouse = new PublishingHouse();
//todo 前端设置isbn，csbn和名字为必填项
        if(StringUtils.isNotBlank(publishingHouse.getPublishhouseName())) {
            tmphouse.setPublishhouseName(publishingHouse.getPublishhouseName());
        }
        if(StringUtils.isNotBlank(publishingHouse.getCsbn())){
            tmphouse.setCsbn(publishingHouse.getCsbn());
        }
        if(StringUtils.isNotBlank(publishingHouse.getIsbn())){
            tmphouse.setIsbn(publishingHouse.getIsbn());
        }
            List<PublishingHouse> info=pmsPublishingHouseMapper.mutiFind(tmphouse);
            if(!info.isEmpty()){
                return CommonResult.failed(ResultCode.PUBLISHINGHOUSE_REPEAT.getCode(),ResultCode.PUBLISHINGHOUSE_REPEAT.getMessage());
            }


            long uid = snowflakeConfig.snowFlackId();
            publishingHouse.setId(uid);
            publishingHouse.setCreateTime(new Date());
            publishingHouse.setUpdateTime(new Date());
            pmsPublishingHouseMapper.insertPublishingHouse(publishingHouse);

         return CommonResult.success(null);
    }

    @Override
    public CommonResult deletePublishHouse(Long id) {
        // 如果出版社还有图书，那么删除操作不成功or将出版社下的所有图书全部都删除
        if(id==null)
            return CommonResult.success(null);
        Date updateTime=new Date();
        // 删除出版社下的所有图书
        pmsPublishingHouseMapper.updateByHouse(id,updateTime,1);
//      将出版社状态设置为0,逻辑删除出版社
        // TOD0  将状态设置为常量
        PublishingHouse house = new PublishingHouse();
        house.setUpdateTime(updateTime);
        house.setStatus(0);
        house.setId(id);
        pmsPublishingHouseMapper.updateHouse(house);
        return CommonResult.success(null);
    }

    @Override
    public CommonResult updatePublishHouse(Long id, PublishingHouse publishingHouse) {

        // 更改名字查看名字是否重复
        //更改csbn查看csbn重复
        //更改isbn查看isbn是否重复
        PublishingHouse house = new PublishingHouse();
        house.setIsbn(publishingHouse.getIsbn());
        house.setCsbn(publishingHouse.getCsbn());
        house.setPublishhouseName(publishingHouse.getPublishhouseName());
        List<PublishingHouse> publishingHouses = pmsPublishingHouseMapper.mutiFind(house);
        for(PublishingHouse houseItem:publishingHouses){
            if(!id.equals(houseItem.getId())){
                return CommonResult.failed(ResultCode.PUBLISHINGHOUSE_REPEAT.getCode(),ResultCode.PUBLISHINGHOUSE_REPEAT.getMessage());
            }
        }
        // 更新
        pmsPublishingHouseMapper.updateHouse(publishingHouse);
        return CommonResult.success(null);
    }

}
