package com.macro.mall.portal.service.impl;

import com.github.pagehelper.PageHelper;
import com.macro.mall.mapper.PmsProductConsultMapper;
import com.macro.mall.model.PmsProductConsult;
import com.macro.mall.portal.service.PmsProductConsultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 商品品牌Service
 * https://gitee.com/zscat-platform/mall on 2018/4/26.
 */
@Service
public class PmsProductConsultServiceImpl implements PmsProductConsultService {

    @Autowired
    private PmsProductConsultMapper productConsultMapper;
    @Override
    public  int create(PmsProductConsult record){
        return productConsultMapper.insert(record);
    }
    @Override
    @Transactional
    public int update(Long id, PmsProductConsult record){
        record.setId(id);
        return productConsultMapper.updateByPrimaryKeySelective(record);
    }
    @Override
    public int delete(Long id){
        return productConsultMapper.deleteByPrimaryKey(id);
    }
    @Override
    public int delete(List<Long> ids){
        return 0;
    }

    @Override
    public List<PmsProductConsult> list(PmsProductConsult record, int pageNum, int pageSize){
        PageHelper.startPage(pageNum, pageSize);
        return productConsultMapper.list(record);
    }

    @Override
    public PmsProductConsult get(Long id){
        return productConsultMapper.selectByPrimaryKey(id);
    }


}
