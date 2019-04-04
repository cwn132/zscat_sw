package com.macro.mall.cms.service.impl;

import com.github.pagehelper.PageHelper;
import com.macro.mall.cms.service.HelpService;
import com.macro.mall.mapper.CmsHelpMapper;
import com.macro.mall.model.CmsHelp;
import com.macro.mall.model.CmsHelpExample;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 商品品牌Service实现类
 * Created by macro on 2018/4/26.
 */
@Service
public class HelpServiceImpl implements HelpService {
    @Resource
    private CmsHelpMapper helpMapper;
    


    @Override
    public int createHelp(CmsHelp help) {
        return helpMapper.insert(help);
    }

    @Override
    public int updateHelp(Long id, CmsHelp help) {
        help.setId(id);
        return helpMapper.updateByPrimaryKeySelective(help);
    }

    @Override
    public int deleteHelp(Long id) {
        return helpMapper.deleteByPrimaryKey(id);
    }


    @Override
    public List<CmsHelp> listHelp(CmsHelp help, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return helpMapper.selectByExample(new CmsHelpExample());
    }

    @Override
    public CmsHelp getHelp(Long id) {
        return helpMapper.selectByPrimaryKey(id);
    }

   
}
