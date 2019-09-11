package com.changgou.goods.service;

import com.changgou.goods.pojo.Spec;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface SpecService {
    /**
     * 查询所有
     * @return
     */
    List<Spec> findAll();

    /**
     * 根据id查询数据
     * @param id
     * @return
     */
    Spec findById(Integer id);

    /**
     * 添加数据
     * @param spec
     */
    void add(Spec spec);

    /**
     * 根据id更新数据
     * @param spec
     */
    void updateById(Spec spec);

    /**
     * 根据id删除数据
     * @param id
     */
    void deleteById(Integer id);

    /**
     * 条件查询
     * @param spec
     * @return
     */
    List<Spec> findList(Spec spec);

    /**
     * 无条件查询分页
     * @param page
     * @param size
     * @return
     */
    PageInfo<Spec> findPage(Integer page, Integer size);

    /**
     * 条件查询分页
     * @param spec
     * @param page
     * @param size
     * @return
     */
    PageInfo<Spec> findPage(Spec spec, Integer page, Integer size);

    List<Spec> findByCategoryId(Integer id);
}
