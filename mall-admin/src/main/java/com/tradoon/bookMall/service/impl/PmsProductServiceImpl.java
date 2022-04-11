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

    @Override
    public CommonResult update(Long prodcutId, PmsProductParam productParam) {
        // 不需要筛除重复项
        PmsProduct pmsProduct =productParam;

        if(Objects.isNull(pmsProduct.getId())){
            pmsProduct.setId(prodcutId);
        }
        pmsProduct.setUpdateTime(new Date());
        // 更改产品信息
        pmsProductMapper.updateByPrimaryKeySelective(pmsProduct);
       //todo 更改为逻辑删除，同时筛选出未更改的做处理
        //更改关联项
        // 删除已有关联项
        //添加新增关联项
//        List<PmsSkuStock> skuStockList = productParam.getSkuStockList();
        // 修改商品参数

        updateAttributeValue(productParam);
        // 修改库存信息
        updateStock(productParam);
        // 修改ladder信息
        updateLadder(productParam);
        //修改满减信息
        updateFullRedution(productParam);
        //修改关联专题
        updateSubject(productParam);
        //修改关联优选
        updatePrefrenceAreaProductRelation(productParam);


        return CommonResult.success(null);
    }

    private void updatePrefrenceAreaProductRelation(PmsProductParam productParam) {
        List<CmsPrefrenceAreaProductRelation> papr = productParam.getPrefrenceAreaProductRelationList();
        List<CmsPrefrenceAreaProductRelation> dbPapr= cmsPrefrenceAreaProductRelationDao.findByInfo(productParam.getId());
        List<CmsPrefrenceAreaProductRelation> multiInsertList=new ArrayList<>();
        List<Long> multiDel=new ArrayList<>();
        for (CmsPrefrenceAreaProductRelation paprItem : papr) {
        if (!(dbPapr.contains(paprItem))){
            paprItem.setId(snowflakeConfig.snowFlackId());
            paprItem.setProductId(productParam.getId());
            multiInsertList.add(paprItem);
        }

        }
        for (CmsPrefrenceAreaProductRelation dbPaprItem : dbPapr) {
            if (!papr.contains(dbPaprItem)){
                multiDel.add(dbPaprItem.getId());
            }
        }
        if(!multiDel.isEmpty())
        cmsPrefrenceAreaProductRelationDao.multiDel(multiDel);

    }

    private void updateSubject(PmsProductParam productParam) {
        List<CmsSubjectProductRelation> subjectList = productParam.getSubjectProductRelationList();
        List<CmsSubjectProductRelation> dbsbSubjectList=cmsSubjectProductRelationDao.findByInfo(productParam.getId());
        List<CmsSubjectProductRelation> multiInsertList=new ArrayList<>();
        for (CmsSubjectProductRelation subItem : subjectList) {
            if(!dbsbSubjectList.contains(subItem)){
                subItem.setId(snowflakeConfig.snowFlackId());
                subItem.setProductId(productParam.getId());
                multiInsertList.add(subItem);
            }

        }
        List<Long> multiDelList=new ArrayList<>();
        if(!multiInsertList.isEmpty())
        cmsSubjectProductRelationDao.insertList(multiInsertList);
        for (CmsSubjectProductRelation dbSubjectItem : dbsbSubjectList) {
            if(!subjectList.contains(dbSubjectItem)){
                multiDelList.add(dbSubjectItem.getId());
            }

        }
        if(!multiDelList.isEmpty())
        cmsSubjectProductRelationDao.multiDel(multiDelList);

    }

    private void updateAttributeValue(PmsProductParam param) {
        List<PmsProductAttributeValue> attributeList = param.getProductAttributeValueList();
        List<PmsProductAttributeValue> dbAVList= pmsProductAttributeValueDao.findByInfo(param.getId());
        List<PmsProductAttributeValue> multiAddLst=new ArrayList<>();
        for (PmsProductAttributeValue avItem : attributeList) {
            if(!dbAVList.contains(avItem)){
                avItem.setId(snowflakeConfig.snowFlackId());
                avItem.setProductId(param.getId());
                multiAddLst.add(avItem);
            }
        }
        ArrayList<Long> multiDel = new ArrayList<>();
        if(!multiAddLst.isEmpty())
        pmsProductAttributeValueDao.insertList(multiAddLst);
        for (PmsProductAttributeValue dbAVItem : dbAVList) {
            if(!attributeList.contains(dbAVItem)){
                multiDel.add(dbAVItem.getId());
            }
        }
        if(!multiDel.isEmpty())
        pmsProductAttributeValueDao.delMulti(multiDel);


    }

    public void  updateLadder(PmsProductParam productParam)  {
        List<PmsProductLadder> productLadderList = productParam.getProductLadderList();
        List<PmsProductLadder> ladderList= pmsProductLadderDao.findByInfo(productParam.getId());
        // 批量加入新增的ladder
        ArrayList<PmsProductLadder> insertList = new ArrayList<>();
        for(PmsProductLadder ladderItem: productLadderList){
            if(!ladderList.contains(ladderItem)){
                ladderItem.setId(snowflakeConfig.snowFlackId());
                ladderItem.setProductId(productParam.getId());
                insertList.add(ladderItem);
            }
        }
        if(!insertList.isEmpty())
        pmsProductLadderDao.insertList(insertList);
        // 批量删除修改后不存在的ladder
        ArrayList<Long> delListProdctId = new ArrayList<>();
        for (PmsProductLadder dbLadderItem : ladderList) {
            if(!productLadderList.contains(dbLadderItem)){
                delListProdctId.add( dbLadderItem.getId());
            }
        }
        if(!delListProdctId.isEmpty())
        pmsProductLadderDao.delMulti(delListProdctId);
    }

    public void  updateStock(PmsProductParam productParam)  {
        List<PmsSkuStock> skuStockList = productParam.getSkuStockList();

        List<PmsSkuStock> dbStockSkuList= pmsSkuStockDao.findByInfo(productParam.getId());
        // 批量加入新增的stock
        ArrayList<PmsSkuStock> insertList = new ArrayList<>();
        for(PmsSkuStock skuStockItem: skuStockList){
            if(!dbStockSkuList.contains(skuStockItem)){
                skuStockItem.setId(snowflakeConfig.snowFlackId());
                skuStockItem.setProductId(productParam.getId());
                insertList.add(skuStockItem);
            }
        }
        if(!insertList.isEmpty()) {
            List<PmsSkuStock> trueInsertSku = handleSkuCode(insertList, productParam.getId());
            pmsSkuStockDao.insertList(trueInsertSku);

        }// 批量删除修改后不存在的stock
        ArrayList<Long> delListProdctId = new ArrayList<>();
        for (PmsSkuStock dbStockSkuItem : dbStockSkuList) {
            if(!skuStockList.contains(dbStockSkuItem)){
                delListProdctId.add( dbStockSkuItem.getId());
            }
        }
        if(!delListProdctId.isEmpty())
        pmsSkuStockDao.delMulti(delListProdctId);
    }


    public void updateFullRedution(PmsProductParam productParam){
        List<PmsProductFullReduction> fullReductionList = productParam.getProductFullReductionList();

        List<PmsProductFullReduction> dbFullReductionList= pmsProductFullReductionDao.findByInfo(productParam.getId());
        // 批量加入新增的fullreduction
        ArrayList<PmsProductFullReduction> insertList = new ArrayList<>();
        for(PmsProductFullReduction fullreductionItem: fullReductionList){
            if(!dbFullReductionList.contains(fullreductionItem)){
                fullreductionItem.setId(snowflakeConfig.snowFlackId());
                fullreductionItem.setProductId(productParam.getId());
                insertList.add(fullreductionItem);
            }
        }
        if(!insertList.isEmpty())
        pmsProductFullReductionDao.insertList(insertList);
        // 批量删除修改后不存在的fullreduction
        ArrayList<Long> delListProdctId = new ArrayList<>();
        for (PmsProductFullReduction dbStockSkuItem : dbFullReductionList) {
            if(!fullReductionList.contains(dbStockSkuItem)){
                delListProdctId.add( dbStockSkuItem.getId());
            }
        }
        if(!delListProdctId.isEmpty())
        pmsProductFullReductionDao.delMulti(delListProdctId);
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
