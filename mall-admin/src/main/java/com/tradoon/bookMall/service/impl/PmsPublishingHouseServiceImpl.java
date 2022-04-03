package com.tradoon.bookMall.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tradoon.bookMall.api.CommonPage;
import com.tradoon.bookMall.api.CommonResult;
import com.tradoon.bookMall.api.Enum.PublishingHouseType;
import com.tradoon.bookMall.dao.PmsPublishingHouseMapper;
import com.tradoon.bookMall.model.PublishingHouse;
import com.tradoon.bookMall.service.PmsPublishingHouseService;
import com.tradoon.bookMall.utils.SnowflakeConfig;
import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
        long uid = snowflakeConfig.snowFlackId();
        publishingHouse.setId(uid);
        publishingHouse.setCreateTime(new Date());
        publishingHouse.setUpdateTime(new Date());
         pmsPublishingHouseMapper.insertPublishingHouse(publishingHouse);
         return CommonResult.success(null);
    }
}
