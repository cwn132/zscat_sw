package com.macro.mall.portal.service.impl;


import com.github.pagehelper.PageHelper;
import com.macro.mall.dto.PmsProductAndGroup;
import com.macro.mall.dto.PmsProductParam;
import com.macro.mall.dto.PmsProductQueryParam;
import com.macro.mall.dto.PmsProductResult;
import com.macro.mall.mapper.*;
import com.macro.mall.model.*;
import com.macro.mall.portal.service.PmsProductService;
import com.macro.mall.portal.util.DateUtils;
import com.macro.mall.sms.mapper.GroupMapper;
import com.macro.mall.sms.mapper.GroupMemberMapper;
import com.macro.mall.sms.model.SmsGroup;
import com.macro.mall.sms.model.SmsGroupMember;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 商品管理Service实现类
 * https://gitee.com/zscat-platform/mall on 2018/4/26.
 */
@Service
public class PmsProductServiceImpl implements PmsProductService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PmsProductServiceImpl.class);
    @Resource
    private GroupMapper groupMapper;
    @Resource
    private GroupMemberMapper groupMemberMapper;
    @Resource
    private PmsProductMapper productMapper;
    @Resource
    private PmsMemberPriceDao memberPriceDao;
    @Resource
    private PmsMemberPriceMapper memberPriceMapper;
    @Resource
    private PmsProductLadderDao productLadderDao;
    @Resource
    private PmsProductLadderMapper productLadderMapper;
    @Resource
    private PmsProductFullReductionDao productFullReductionDao;
    @Resource
    private PmsProductFullReductionMapper productFullReductionMapper;
    @Resource
    private PmsSkuStockDao skuStockDao;
    @Resource
    private PmsSkuStockMapper skuStockMapper;
    @Resource
    private PmsProductAttributeValueDao productAttributeValueDao;
    @Resource
    private PmsProductAttributeValueMapper productAttributeValueMapper;
    @Resource
    private CmsSubjectProductRelationDao subjectProductRelationDao;
    @Resource
    private CmsSubjectProductRelationMapper subjectProductRelationMapper;
    @Resource
    private CmsPrefrenceAreaProductRelationDao prefrenceAreaProductRelationDao;
    @Resource
    private CmsPrefrenceAreaProductRelationMapper prefrenceAreaProductRelationMapper;
    @Resource
    private PmsProductDao productDao;
    @Resource
    private PmsProductVertifyRecordDao productVertifyRecordDao;

    @Override
    public int create(PmsProductParam productParam) {
        int count;
        //创建商品
        PmsProduct product = productParam;
        product.setId(null);
        productMapper.insertSelective(product);
        //根据促销类型设置价格：、阶梯价格、满减价格
        Long productId = product.getId();
        //会员价格
        relateAndInsertList(memberPriceDao, productParam.getMemberPriceList(), productId);
        //阶梯价格
        relateAndInsertList(productLadderDao, productParam.getProductLadderList(), productId);
        //满减价格
        relateAndInsertList(productFullReductionDao, productParam.getProductFullReductionList(), productId);
        //处理sku的编码
        handleSkuStockCode(productParam.getSkuStockList(), productId);
        //添加sku库存信息
        relateAndInsertList(skuStockDao, productParam.getSkuStockList(), productId);
        //添加商品参数,添加自定义商品规格
        relateAndInsertList(productAttributeValueDao, productParam.getProductAttributeValueList(), productId);
        //关联专题
        relateAndInsertList(subjectProductRelationDao, productParam.getSubjectProductRelationList(), productId);
        //关联优选
        relateAndInsertList(prefrenceAreaProductRelationDao, productParam.getPrefrenceAreaProductRelationList(), productId);
        count = 1;
        return count;
    }

    private void handleSkuStockCode(List<PmsSkuStock> skuStockList, Long productId) {
        if (CollectionUtils.isEmpty(skuStockList)) return;
        for (int i = 0; i < skuStockList.size(); i++) {
            PmsSkuStock skuStock = skuStockList.get(i);
            if (StringUtils.isEmpty(skuStock.getSkuCode())) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                StringBuilder sb = new StringBuilder();
                //日期
                sb.append(sdf.format(new Date()));
                //四位商品id
                sb.append(String.format("%04d", productId));
                //三位索引id
                sb.append(String.format("%03d", i + 1));
                skuStock.setSkuCode(sb.toString());
            }
        }
    }

    @Override
    public PmsProductResult getUpdateInfo(Long id) {
        return productDao.getUpdateInfo(id);
    }

    @Override
    public int update(Long id, PmsProductParam productParam) {
        int count;
        //更新商品信息
        PmsProduct product = productParam;
        product.setId(id);
        productMapper.updateByPrimaryKeySelective(product);
        //会员价格
        PmsMemberPriceExample pmsMemberPriceExample = new PmsMemberPriceExample();
        pmsMemberPriceExample.createCriteria().andProductIdEqualTo(id);
        memberPriceMapper.deleteByExample(pmsMemberPriceExample);
        relateAndInsertList(memberPriceDao, productParam.getMemberPriceList(), id);
        //阶梯价格
        PmsProductLadderExample ladderExample = new PmsProductLadderExample();
        ladderExample.createCriteria().andProductIdEqualTo(id);
        productLadderMapper.deleteByExample(ladderExample);
        relateAndInsertList(productLadderDao, productParam.getProductLadderList(), id);
        //满减价格
        PmsProductFullReductionExample fullReductionExample = new PmsProductFullReductionExample();
        fullReductionExample.createCriteria().andProductIdEqualTo(id);
        productFullReductionMapper.deleteByExample(fullReductionExample);
        relateAndInsertList(productFullReductionDao, productParam.getProductFullReductionList(), id);
        //修改sku库存信息
        PmsSkuStockExample skuStockExample = new PmsSkuStockExample();
        skuStockExample.createCriteria().andProductIdEqualTo(id);
        skuStockMapper.deleteByExample(skuStockExample);
        handleSkuStockCode(productParam.getSkuStockList(), id);
        relateAndInsertList(skuStockDao, productParam.getSkuStockList(), id);
        //修改商品参数,添加自定义商品规格
        PmsProductAttributeValueExample productAttributeValueExample = new PmsProductAttributeValueExample();
        productAttributeValueExample.createCriteria().andProductIdEqualTo(id);
        productAttributeValueMapper.deleteByExample(productAttributeValueExample);
        relateAndInsertList(productAttributeValueDao, productParam.getProductAttributeValueList(), id);
        //关联专题
        CmsSubjectProductRelationExample subjectProductRelationExample = new CmsSubjectProductRelationExample();
        subjectProductRelationExample.createCriteria().andProductIdEqualTo(id);
        subjectProductRelationMapper.deleteByExample(subjectProductRelationExample);
        relateAndInsertList(subjectProductRelationDao, productParam.getSubjectProductRelationList(), id);
        //关联优选
        CmsPrefrenceAreaProductRelationExample prefrenceAreaExample = new CmsPrefrenceAreaProductRelationExample();
        prefrenceAreaExample.createCriteria().andProductIdEqualTo(id);
        prefrenceAreaProductRelationMapper.deleteByExample(prefrenceAreaExample);
        relateAndInsertList(prefrenceAreaProductRelationDao, productParam.getPrefrenceAreaProductRelationList(), id);
        count = 1;
        return count;
    }

    @Override
    public List<PmsProduct> list(PmsProductQueryParam productQueryParam) {
        PageHelper.startPage(productQueryParam.getPageNum(), productQueryParam.getPageSize());
        PmsProductExample productExample = new PmsProductExample();
        PmsProductExample.Criteria criteria = productExample.createCriteria();
        criteria.andDeleteStatusEqualTo(0);
        if (productQueryParam.getPublishStatus() != null) {
            criteria.andPublishStatusEqualTo(productQueryParam.getPublishStatus());
        }
        if (productQueryParam.getVerifyStatus() != null) {
            criteria.andVerifyStatusEqualTo(productQueryParam.getVerifyStatus());
        }
        if (!StringUtils.isEmpty(productQueryParam.getKeyword())) {
            criteria.andNameLike("%" + productQueryParam.getKeyword() + "%");
        }
        if (!StringUtils.isEmpty(productQueryParam.getProductSn())) {
            criteria.andProductSnEqualTo(productQueryParam.getProductSn());
        }
        if (productQueryParam.getBrandId() != null) {
            criteria.andBrandIdEqualTo(productQueryParam.getBrandId());
        }
        if (productQueryParam.getProductCategoryId() != null) {
            criteria.andProductCategoryIdEqualTo(productQueryParam.getProductCategoryId());
        }
        if (productQueryParam.getProductAttributeCategoryId() != null) {
            criteria.andProductAttributeCategoryIdEqualTo(productQueryParam.getProductAttributeCategoryId());
        }
        if (productQueryParam.getSort() != null) {
            productExample.setOrderByClause(productQueryParam.getSort());
        }

        return productMapper.selectByExample(productExample);
    }

    @Override
    public int updateVerifyStatus(List<Long> ids, Integer verifyStatus, String detail) {
        PmsProduct product = new PmsProduct();
        product.setVerifyStatus(verifyStatus);
        PmsProductExample example = new PmsProductExample();
        example.createCriteria().andIdIn(ids);
        List<PmsProductVertifyRecord> list = new ArrayList<>();
        int count = productMapper.updateByExampleSelective(product, example);
        //修改完审核状态后插入审核记录
        for (Long id : ids) {
            PmsProductVertifyRecord record = new PmsProductVertifyRecord();
            record.setProductId(id);
            record.setCreateTime(new Date());
            record.setDetail(detail);
            record.setStatus(verifyStatus);
            record.setVertifyMan("test");
            list.add(record);
        }
        productVertifyRecordDao.insertList(list);
        return count;
    }

    @Override
    public int updatePublishStatus(List<Long> ids, Integer publishStatus) {
        PmsProduct record = new PmsProduct();
        record.setPublishStatus(publishStatus);
        PmsProductExample example = new PmsProductExample();
        example.createCriteria().andIdIn(ids);
        return productMapper.updateByExampleSelective(record, example);
    }

    @Override
    public int updateRecommendStatus(List<Long> ids, Integer recommendStatus) {
        PmsProduct record = new PmsProduct();
        record.setRecommandStatus(recommendStatus);
        PmsProductExample example = new PmsProductExample();
        example.createCriteria().andIdIn(ids);
        return productMapper.updateByExampleSelective(record, example);
    }

    @Override
    public int updateNewStatus(List<Long> ids, Integer newStatus) {
        PmsProduct record = new PmsProduct();
        record.setNewStatus(newStatus);
        PmsProductExample example = new PmsProductExample();
        example.createCriteria().andIdIn(ids);
        return productMapper.updateByExampleSelective(record, example);
    }

    @Override
    public int updateDeleteStatus(List<Long> ids, Integer deleteStatus) {
        PmsProduct record = new PmsProduct();
        record.setDeleteStatus(deleteStatus);
        PmsProductExample example = new PmsProductExample();
        example.createCriteria().andIdIn(ids);
        return productMapper.updateByExampleSelective(record, example);
    }

    @Override
    public List<PmsProduct> list(String keyword) {
        PmsProductExample productExample = new PmsProductExample();
        PmsProductExample.Criteria criteria = productExample.createCriteria();
        criteria.andDeleteStatusEqualTo(0);
        if (!StringUtils.isEmpty(keyword)) {
            criteria.andNameLike("%" + keyword + "%");
            productExample.or().andDeleteStatusEqualTo(0).andProductSnLike("%" + keyword + "%");
        }
        return productMapper.selectByExample(productExample);
    }

    /**
     * @deprecated 旧版创建
     */
    public int createOld(PmsProductParam productParam) {
        int count;
        //创建商品
        PmsProduct product = productParam;
        product.setId(null);
        productMapper.insertSelective(product);
        //根据促销类型设置价格：、阶梯价格、满减价格
        Long productId = product.getId();
        //会员价格
        List<PmsMemberPrice> memberPriceList = productParam.getMemberPriceList();
        if (!CollectionUtils.isEmpty(memberPriceList)) {
            for (PmsMemberPrice pmsMemberPrice : memberPriceList) {
                pmsMemberPrice.setId(null);
                pmsMemberPrice.setProductId(productId);
            }
            memberPriceDao.insertList(memberPriceList);
        }
        //阶梯价格
        List<PmsProductLadder> productLadderList = productParam.getProductLadderList();
        if (!CollectionUtils.isEmpty(productLadderList)) {
            for (PmsProductLadder productLadder : productLadderList) {
                productLadder.setId(null);
                productLadder.setProductId(productId);
            }
            productLadderDao.insertList(productLadderList);
        }
        //满减价格
        List<PmsProductFullReduction> productFullReductionList = productParam.getProductFullReductionList();
        if (!CollectionUtils.isEmpty(productFullReductionList)) {
            for (PmsProductFullReduction productFullReduction : productFullReductionList) {
                productFullReduction.setId(null);
                productFullReduction.setProductId(productId);
            }
            productFullReductionDao.insertList(productFullReductionList);
        }
        //添加sku库存信息
        List<PmsSkuStock> skuStockList = productParam.getSkuStockList();
        if (!CollectionUtils.isEmpty(skuStockList)) {
            for (PmsSkuStock skuStock : skuStockList) {
                skuStock.setId(null);
                skuStock.setProductId(productId);
            }
            skuStockDao.insertList(skuStockList);
        }
        //添加商品参数,添加自定义商品规格
        List<PmsProductAttributeValue> productAttributeValueList = productParam.getProductAttributeValueList();
        if (!CollectionUtils.isEmpty(productAttributeValueList)) {
            for (PmsProductAttributeValue productAttributeValue : productAttributeValueList) {
                productAttributeValue.setId(null);
                productAttributeValue.setProductId(productId);
            }
            productAttributeValueDao.insertList(productAttributeValueList);
        }
        //关联专题
        relateAndInsertList(subjectProductRelationDao, productParam.getSubjectProductRelationList(), productId);
        //关联优选
        relateAndInsertList(prefrenceAreaProductRelationDao, productParam.getPrefrenceAreaProductRelationList(), productId);
        count = 1;
        return count;
    }

    /**
     * 建立和插入关系表操作
     *
     * @param dao       可以操作的dao
     * @param dataList  要插入的数据
     * @param productId 建立关系的id
     */
    private void relateAndInsertList(Object dao, List dataList, Long productId) {
        try {
            if (CollectionUtils.isEmpty(dataList)) return;
            for (Object item : dataList) {
                Method setId = item.getClass().getMethod("setId", Long.class);
                setId.invoke(item, (Long) null);
                Method setProductId = item.getClass().getMethod("setProductId", Long.class);
                setProductId.invoke(item, productId);
            }
            Method insertList = dao.getClass().getMethod("insertList", List.class);
            insertList.invoke(dao, dataList);
        } catch (Exception e) {
            LOGGER.warn("创建产品出错:{}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public PmsProductAndGroup get(Long id) {
        PmsProduct goods = productMapper.selectByPrimaryKey(id);
        PmsProductAndGroup vo = new PmsProductAndGroup();
        try {
            BeanUtils.copyProperties(goods, vo);

            SmsGroup group = groupMapper.getByGoodsId(id);
            SmsGroupMember newG = new SmsGroupMember();
            newG.setGoodsId(id);
            List<SmsGroupMember> list = groupMemberMapper.list(newG);
            if (group != null) {
                Map<String, List<SmsGroupMember>> map = groupMemberByMainId(list, group);
                vo.setMap(map);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return vo;


    }

    @Override
    public List<PmsProduct> list(PmsProduct product, Integer pageSize, Integer pageNum) {
        return null;
    }

    /**
     * 按照异常批次号对已开单数据进行分组
     *
     * @param billingList
     * @return
     * @throws Exception
     */
    private Map<String, List<SmsGroupMember>> groupMemberByMainId(List<SmsGroupMember> billingList, SmsGroup group) throws Exception {
        Map<String, List<SmsGroupMember>> resultMap = new HashMap<String, List<SmsGroupMember>>();
        Map<String, List<SmsGroupMember>> map = new HashMap<String, List<SmsGroupMember>>();
        try {
            List<Long> ids = new ArrayList<>();
            for (SmsGroupMember tmExcpNew : billingList) {
                if (tmExcpNew.getMemberId().equals(tmExcpNew.getMainId())) {
                    Date cr = tmExcpNew.getCreateTime();
                    Long nowT = System.currentTimeMillis();
                    Date endTime = DateUtils.convertStringToDate(DateUtils.addHours(cr, group.getHours()), "yyyy-MM-dd HH:mm:ss");
                    if (nowT <= endTime.getTime()) {
                        ids.add(tmExcpNew.getMainId());
                    }
                }
                if (resultMap.containsKey(tmExcpNew.getMainId()+"")) {//map中异常批次已存在，将该数据存放到同一个key（key存放的是异常批次）的map中
                    resultMap.get(tmExcpNew.getMainId()+"").add(tmExcpNew);
                } else {//map中不存在，新建key，用来存放数据
                    List<SmsGroupMember> tmpList = new ArrayList<SmsGroupMember>();
                    tmpList.add(tmExcpNew);
                    resultMap.put(tmExcpNew.getMainId() + "", tmpList);
                }
            }
            for (Long id : ids) {
                if (resultMap.get(id + "") != null) {
                    map.put(id + "", resultMap.get(id + ""));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("按照异常批次号对已开单数据进行分组时出现异常", e);
        }

        return map;
    }


}
