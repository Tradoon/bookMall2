package com.tradoon.bookMall.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tradoon.bookMall.api.CommonPage;
import com.tradoon.bookMall.api.CommonResult;
import com.tradoon.bookMall.dao.*;
import com.tradoon.bookMall.dto.PmsProductParam;
import com.tradoon.bookMall.dto.PmsProductQueryParam;
import com.tradoon.bookMall.model.*;
import com.tradoon.bookMall.service.PmsProductService;
import com.tradoon.bookMall.utils.SnowflakeConfig;
import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * author:tradoon
 * desciption:
 * date:2022/ / /
 */
@Service
public class PmsProductServiceImpl implements PmsProductService {
    @Autowired
    PmsMemberPriceDao pmsMemberPriceDao;

    @Autowired
    PmsProductMapper pmsProductMapper;

    @Autowired
    PmsProductLadderDao pmsProductLadderDao;

    @Autowired
    PmsSkuStockDao pmsSkuStockDao;

    @Autowired
    PmsProductFullReductionDao pmsProductFullReductionDao;

    @Autowired
    PmsProductAttributeValueDao pmsProductAttributeValueDao;

    @Autowired
    CmsSubjectProductRelationDao cmsSubjectProductRelationDao;

    @Autowired
    CmsPrefrenceAreaProductRelationDao cmsPrefrenceAreaProductRelationDao;
    @Autowired
    SnowflakeConfig snowflakeConfig;


    @Override
    public CommonResult create(PmsProductParam param) {
        // 提取所有属性
        PmsProduct pmsProduct=(PmsProduct) param;
        Long productId=snowflakeConfig.snowFlackId();
        pmsProduct.setBrandId(productId);
        // 插入商品
        pmsProduct.setCreateTime(new Date());
        pmsProduct.setUpdateTime(new Date());
        pmsProductMapper.insertSelective(pmsProduct);
        //插入关联信息
            // 会员价格
        List<PmsMemberPrice> memberPrice = param.getMemberPriceList();
        if(!memberPrice.isEmpty()){
            List<PmsMemberPrice> listWithId = (List<PmsMemberPrice>)getListWithId(memberPrice,productId);
            pmsMemberPriceDao.insertList(listWithId);
        }
        // 阶梯价格
        List<PmsProductLadder> ladderList = param.getProductLadderList();
        if(!ladderList.isEmpty()){
            List<PmsProductLadder> listWithId = (List<PmsProductLadder>) getListWithId(ladderList, productId);
            pmsProductLadderDao.insertList(listWithId);
        }
        // 满减价格
        List<PmsProductFullReduction> fullRed = param.getProductFullReductionList();
        if(!fullRed.isEmpty()){
            List<PmsProductFullReduction> listWithId = (List<PmsProductFullReduction>) getListWithId(fullRed, productId);
            pmsProductFullReductionDao.insertList(listWithId);
        }
        //sku 库存信息
        List<PmsSkuStock> skuList = param.getSkuStockList();
        if(!skuList.isEmpty()){
            // 处理sku编码信息
            skuList=handleSkuCode(skuList,productId);
            //增加sku编码信息
            List<PmsSkuStock> listWithId = (List<PmsSkuStock>) getListWithId(skuList, productId);
            pmsSkuStockDao.insertList(listWithId);
        }
        // 自定义商品规格
        List<PmsProductAttributeValue> attriList = param.getProductAttributeValueList();
        if(!attriList.isEmpty()){
            List<PmsProductAttributeValue> listWithId = (List<PmsProductAttributeValue>) getListWithId(attriList, productId);
           //todo  考虑要不要将逗号分割的完全分割成字符数组
            pmsProductAttributeValueDao.insertList(listWithId);
        }
        // 关联专题
        List<CmsSubjectProductRelation> sbjectRList = param.getSubjectProductRelationList();
        if(!sbjectRList.isEmpty()){
            List<CmsSubjectProductRelation> listWithId = (List<CmsSubjectProductRelation>) getListWithId(sbjectRList, productId);
            cmsSubjectProductRelationDao.insertList(listWithId);
        }
        // 关联优选

        List<CmsPrefrenceAreaProductRelation> preAreaList = param.getPrefrenceAreaProductRelationList();
        if(!preAreaList.isEmpty()){
            List<CmsPrefrenceAreaProductRelation> listWithId = (List<CmsPrefrenceAreaProductRelation>) getListWithId(preAreaList, productId);
            cmsPrefrenceAreaProductRelationDao.insertList(listWithId);

        }

        return CommonResult.success(null);
    }

    @Override
    public CommonResult<CommonPage<PmsProduct>> getPageList(PmsProductQueryParam productQueryParam, Integer pageSize, Integer pageNum) {
        Page<Object> pageHelper = PageHelper.startPage(pageNum, pageSize);
        PmsProduct pmsProduct = new PmsProduct();
        BeanUtils.copyProperties(productQueryParam,pmsProduct);

       List<PmsProduct> productList=
               pmsProductMapper.findBySelective(pmsProduct);


        PageInfo<PmsProduct> pmsPageInfo = new PageInfo<>(productList);
        pmsPageInfo.setPageNum(pageHelper.getPageNum());
        pmsPageInfo.setPageSize(pageHelper.getPageSize());
        pmsPageInfo.setPages(pageHelper.getPages());
        pmsPageInfo.setTotal(pageHelper.getTotal());

        return CommonResult.success(new CommonPage<>(pmsPageInfo));
    }

    private List<PmsSkuStock> handleSkuCode(List<PmsSkuStock> skuList,Long productId) {
        for (int i = 0; i < skuList.size(); i++) {
            PmsSkuStock pmsSkuStock = skuList.get(i);

            StringBuilder sb=new StringBuilder();
                SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
                // 当前时间
                sb.append(sdf.format(new Date()));
                // 保留productId 末尾四位数
                String skuByPid = String.valueOf(productId);
                skuByPid=skuByPid.length()>4?skuByPid.substring(skuByPid.length()-4):skuByPid;
                sb.append(skuByPid);
                // 当前索引
                //todo 当前前端不传入输入的skucode ，之后更改为添加上前端传入的skucode
                sb.append(String.format("%03d",i+1));
                pmsSkuStock.setSkuCode(String.valueOf(sb));
            }

        return skuList;
    }


    public List<? extends PmsProductCommonInfo > getListWithId(List<? extends  PmsProductCommonInfo> tmpList, Long productId){
        for (int i = 0; i < tmpList.size(); i++) {
            PmsProductCommonInfo   pmsProductCommonInfo = tmpList.get(i);
            pmsProductCommonInfo.setProductId(productId);
            pmsProductCommonInfo.setId(snowflakeConfig.snowFlackId());
        }
        return tmpList;
    }
}
