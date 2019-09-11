package com.changgou.goods.service;

import com.changgou.goods.pojo.Template;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface TemplateService {
    /**
     * 查询所有
     * @return
     */
    List<Template> findAll();

    /**
     * 根据id查询数据
     * @param id
     * @return
     */
    Template findById(Integer id);

    /**
     * 添加数据
     * @param template
     */
    void add(Template template);


    /**
     * 根据id更相信数据
     * @param template
     */
    void updateById(Template template);

    /**
     * 根据id删除数据
     * @param id
     */
    void deleteById(Integer id);

    /**
     * 条件查询数据
     * @param template
     * @return
     */
    List<Template> findList(Template template);

    /**
     * 查询分页
     * @param page
     * @param size
     * @return
     */
    PageInfo<Template> findPage(Integer page, Integer size);

    /**
     * 条件分页查询
     * @param template
     * @param page
     * @param size
     * @return
     */
    PageInfo<Template> findPage(Template template, Integer page, Integer size);
}
