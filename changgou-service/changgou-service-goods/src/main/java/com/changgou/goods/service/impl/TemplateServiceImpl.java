package com.changgou.goods.service.impl;

import com.changgou.goods.dao.TemplateMapper;
import com.changgou.goods.pojo.Template;
import com.changgou.goods.service.TemplateService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class TemplateServiceImpl implements TemplateService {

    @Autowired(required = false)
    private TemplateMapper templateMapper;
    @Override
    public List<Template> findAll() {
        return templateMapper.selectAll();
    }

    @Override
    public Template findById(Integer id) {
        return templateMapper.selectByPrimaryKey(id);
    }

    @Override
    public void add(Template template) {
        templateMapper.insert(template);
    }

    @Override
    public void updateById(Template template) {
        templateMapper.insertSelective(template);
    }

    @Override
    public void deleteById(Integer id) {
        templateMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<Template> findList(Template template) {
        Example example = createExample(template);
        List<Template> templates = templateMapper.selectByExample(example);
        return templates;
    }

    @Override
    public PageInfo<Template> findPage(Integer page, Integer size) {
        PageHelper.startPage(page,size);
        return new PageInfo<Template>(templateMapper.selectAll());
    }

    @Override
    public PageInfo<Template> findPage(Template template, Integer page, Integer size) {
        //开始分页
        PageHelper.startPage(page,size);
        Example example = createExample(template);
        return new PageInfo<>(templateMapper.selectByExample(example));
    }

    private Example createExample(Template template) {
        //根据条件查询数据
        //创建条件对象
        Example example = new Example(Template.class);
        Example.Criteria criteria = example.createCriteria();
        if (template !=null) {
            if (!StringUtils.isEmpty(template.getName())) {
                criteria.andLike("name","%"+template.getName()+"%");
            }
            if (!StringUtils.isEmpty(template.getId())) {
                criteria.andEqualTo("id",template.getId());
            }
            if (!StringUtils.isEmpty(template.getParaNum())) {
                criteria.andEqualTo("paraNum",template.getParaNum());
            }
            if (StringUtils.isEmpty(template.getSpecNum())) {
                criteria.andEqualTo("specNum",template.getSpecNum());
            }
        }
        return example;
    }


}
