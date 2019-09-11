package com.changgou.goods.service.impl;

import com.changgou.goods.dao.ParaMapper;
import com.changgou.goods.pojo.Para;
import com.changgou.goods.service.ParaService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class ParaServiceImpl implements ParaService {
    @Autowired(required = false)
    private ParaMapper paraMapper;

    @Override
    public List<Para> findAll() {
        return paraMapper.selectAll();
    }

    @Override
    public Para findById(Integer id) {
        return paraMapper.selectByPrimaryKey(id);
    }

    @Override
    public void add(Para para) {
        paraMapper.insertSelective(para);
    }

    @Override
    public void updateById(Para para) {
        paraMapper.updateByPrimaryKey(para);
    }

    @Override
    public void deleteById(Integer id) {
        paraMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<Para> findList(Para para) {
        Example example = createExample(para);
        List<Para> paras = paraMapper.selectByExample(example);
        return paras;
    }

    @Override
    public PageInfo<Para> findPage(Integer page, Integer size) {
        PageHelper.startPage(page,size);
        return new PageInfo<Para>(paraMapper.selectAll());
    }

    @Override
    public PageInfo<Para> findPage(Para para, Integer page, Integer size) {
        Example example = createExample(para);
        return new PageInfo<Para>(paraMapper.selectByExample(example));
    }

    @Override
    public List<Para> findCategoryIdByPara(Integer id) {

        return paraMapper.findCategoryIdByPara(id);
    }

    private Example createExample(Para para) {
        Example example = new Example(Para.class);
        Example.Criteria criteria = example.createCriteria();
        if (para != null) {
            if (!StringUtils.isEmpty(para.getId())) {
                criteria.andEqualTo("id",para.getId());
            }
            if (!StringUtils.isEmpty(para.getSeq())) {
                criteria.andEqualTo("sep",para.getSeq());
            }
            if (!StringUtils.isEmpty(para.getName())) {
                criteria.andLike("name","%"+para.getName()+"%");
            }
            if (StringUtils.isEmpty(para.getTemplateId())) {
                criteria.andEqualTo("templateId",para.getOptions());
            }
            if (!StringUtils.isEmpty(para.getOptions())) {
                criteria.andLike("options","%"+para.getOptions()+"%");
            }
        }
        return example;
    }
}
