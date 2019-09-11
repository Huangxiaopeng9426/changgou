package com.changgou.goods.service;

import com.changgou.goods.pojo.Brand;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface BrandService {
    /**
     * 查询所有的品牌
     * @return
     */
    List<Brand> findAll();

    /**
     * 根据id查询
     * @param id
     * @return
     */
    Brand findById(Integer id);

    /**
     * 添加品牌数据
     * @param brand
     */
    void add(Brand brand);

    /**
     * 根据Id更新数据
     * @param brand
     */
    void update(Brand brand);

    /**
     * 根据Id删除数据
     * @param id
     */
    void deleteById(Integer id);

    /**
     * 没有条件的分页查询
     * @param page
     * @param size
     * @return
     */
    PageInfo<Brand> findPage(Integer page, Integer size);


    /**
     * 条件查询数据
     * @param brand
     * @return
     */
    List<Brand> findList(Brand brand);

    /**
     * 条件分页查询
     * @param brand
     * @param page
     * @param size
     * @return
     */
    PageInfo<Brand> findPage(Brand brand, Integer page, Integer size);

    /**
     * 根据分类的id查询对象的品牌
     * @param id
     * @return
     */
    List<Brand> findCategoryIdByBrand(Integer id);
}
