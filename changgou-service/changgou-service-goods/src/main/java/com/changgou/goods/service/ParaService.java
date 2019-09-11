package com.changgou.goods.service;

import com.changgou.goods.pojo.Para;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface ParaService {
    /**
     * 查询所有数据
     * @return
     */
    List<Para> findAll();

    /**
     * 根据id查询数据
     * @param id
     * @return
     */
    Para findById(Integer id);

    /**
     * 添加数据
     * @param para
     */
    void add(Para para);

    /**
     * 更新成功
     * @param para
     */
    void updateById(Para para);

    /**
     * 根据id删除数据
     * @param id
     */
    void deleteById(Integer id);

    /**
     * 条件查询
     * @param para
     * @return
     */
    List<Para> findList(Para para);

    /**
     * 分页查询
     * @param page
     * @param size
     * @return
     */
    PageInfo<Para> findPage(Integer page, Integer size);

    /**
     * 条件分页查询
     * @param para
     * @param page
     * @param size
     * @return
     */
    PageInfo<Para> findPage(Para para, Integer page, Integer size);

    /**
     * 根据模板的id查询对应品牌的参数
     * @param id
     * @return
     */
    List<Para> findCategoryIdByPara(Integer id);
}
