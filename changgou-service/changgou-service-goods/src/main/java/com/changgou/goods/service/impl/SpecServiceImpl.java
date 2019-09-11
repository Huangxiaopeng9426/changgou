package com.changgou.goods.service.impl;

import com.changgou.goods.dao.SpecMapper;
import com.changgou.goods.pojo.Spec;
import com.changgou.goods.service.SpecService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class SpecServiceImpl implements SpecService {

    @Autowired(required = false)
    private SpecMapper specMapper;

    @Override
    public List<Spec> findAll() {
        return specMapper.selectAll();
    }

    @Override
    public Spec findById(Integer id) {
        return specMapper.selectByPrimaryKey(id);
    }

    @Override
    public void add(Spec spec) {
        specMapper.insertSelective(spec);
    }

    @Override
    public void updateById(Spec spec) {
        specMapper.updateByPrimaryKey(spec);
    }

    @Override
    public void deleteById(Integer id) {
        specMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<Spec> findList(Spec spec) {
        Example example = createExample(spec);
        List<Spec> specs = specMapper.selectByExample(example);
        return specs;
    }

    @Override
    public PageInfo<Spec> findPage(Integer page, Integer size) {
        PageHelper.startPage(page,size);
        return new PageInfo<Spec>(specMapper.selectAll());
    }

    @Override
    public PageInfo<Spec> findPage(Spec spec, Integer page, Integer size) {
        Example example = createExample(spec);
        return new PageInfo<>(specMapper.selectByExample(example));
    }

    @Override
    public List<Spec> findByCategoryId(Integer id) {
        return specMapper.findByCategoryId(id);
    }

    private Example createExample(Spec spec) {
        Example example = new Example(Spec.class);
        Example.Criteria criteria = example.createCriteria();
        if (spec!=null) {
            if (!StringUtils.isEmpty(spec.getId())) {
                criteria.andEqualTo("id",spec.getId());
            }
            if (!StringUtils.isEmpty(spec.getName())) {
                criteria.andLike("name","%"+spec.getName()+"%");
            }
            if (!StringUtils.isEmpty(spec.getOptions())) {
                criteria.andLike("options","%"+spec.getOptions()+"%");
            }
            if (!StringUtils.isEmpty(spec.getSeq())) {
                criteria.andEqualTo("seq",spec.getSeq());
            }
            if (StringUtils.isEmpty(spec.getTemplateId())) {
                criteria.andEqualTo("templateId",spec.getTemplateId());
            }
        }
        return example;
    }
}
