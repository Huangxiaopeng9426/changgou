package com.changgou.goods.service.impl;

import com.changgou.goods.dao.CategoryMapper;
import com.changgou.goods.pojo.Category;
import com.changgou.goods.service.CategoryService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired(required = false)
    private CategoryMapper categoryMapper;

    @Override
    public List<Category> findAll() {
        return categoryMapper.selectAll();
    }

    @Override
    public Category findById(Integer id) {
        return categoryMapper.selectByPrimaryKey(id);
    }

    @Override
    public void add(Category category) {
        categoryMapper.insertSelective(category);
    }

    @Override
    public void updateById(Category category) {
        categoryMapper.updateByPrimaryKey(category);
    }

    @Override
    public void deleteById(Integer id) {
        categoryMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<Category> findByParentId(Integer pid) {
        Category category = new Category();
        category.setParentId(pid);//根据条件查询
        return categoryMapper.select(category);
    }

    @Override
    public List<Category> findList(Category category) {
        Example example = createExample(category);
        List<Category> categories = categoryMapper.selectByExample(example);
        return categories;
    }

    @Override
    public PageInfo<Category> findPage(Integer page, Integer size) {
        PageHelper.startPage(page,size);
        return new PageInfo<>(categoryMapper.selectAll());
    }

    @Override
    public PageInfo<Category> findPage(Category category, Integer page, Integer size) {
        Example example = createExample(category);
        return new PageInfo<>(categoryMapper.selectByExample(example));
    }

    private Example createExample(Category category) {
        Example example = new Example(Category.class);
        Example.Criteria criteria = example.createCriteria();
        if (category!=null) {
            if (!StringUtils.isEmpty(category.getGoodsNum())) {
                criteria.andEqualTo("goodsNum",category.getGoodsNum());
            }
            if (!StringUtils.isEmpty(category.getId())) {
                criteria.andEqualTo("id",category.getId());
            }
            if (!StringUtils.isEmpty(category.getIsMenu())) {
                criteria.andLike("isMenu","%"+category.getIsMenu()+"%");
            }
            if (!StringUtils.isEmpty(category.getIsShow())) {
                criteria.andLike("isShow","%"+category.getIsShow()+"%");
            }
            if (!StringUtils.isEmpty(category.getName())) {
                criteria.andLike("name","%"+category.getName()+"%");
            }
            if (!StringUtils.isEmpty(category.getParentId())) {
                criteria.andEqualTo("parentId",category.getParentId());
            }
            if (!StringUtils.isEmpty(category.getSeq())) {
                criteria.andEqualTo("sep",category.getSeq());
            }
            if (!StringUtils.isEmpty(category.getTemplateId())) {
                criteria.andEqualTo("templateId",category.getTemplateId());
            }
        }
        return example;
    }

}
