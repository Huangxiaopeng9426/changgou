package com.changgou.goods.service;

import com.changgou.goods.pojo.Category;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface CategoryService {
    /**
     * 查询所有数据
     * @return
     */
    List<Category> findAll();

    /**
     * 根据id查询数据
     * @param id
     * @return
     */
    Category findById(Integer id);

    /**
     * 添加数据
     * @param category
     */
    void add(Category category);

    /**
     * 更新数据
     * @param category
     */
    void updateById(Category category);

    /**
     * 根据id删除数据
     * @param id
     */
    void deleteById(Integer id);

    /**
     * 根据父节点查询数据
     * @param pid
     * @return
     */
    List<Category> findByParentId(Integer pid);

    /**
     * 条件查询
     * @param category
     * @return
     */
    List<Category> findList(Category category);

    /**
     * 分页查询
     * @param page
     * @param size
     * @return
     */
    PageInfo<Category> findPage(Integer page, Integer size);

    /**
     * 条件分页查询
     * @param category
     * @param page
     * @param size
     * @return
     */
    PageInfo<Category> findPage(Category category, Integer page, Integer size);
}
