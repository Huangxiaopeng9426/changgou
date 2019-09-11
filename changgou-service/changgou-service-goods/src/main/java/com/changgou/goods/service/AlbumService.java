package com.changgou.goods.service;

import com.changgou.goods.pojo.Album;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface AlbumService {
    /**
     * 查询所有数据
     * @return
     */
    List<Album> findAll();

    /**
     * 添加数据
     * @param album
     */
    void add(Album album);

    /**
     * 根据id查询数据
     * @param id
     * @return
     */
    Album findById(Long id);

    /**
     * 根据id更新数据
     * @param album
     */
    void updateById(Album album);

    /**
     * 根据id删除数据
     * @param id
     */
    void deleteById(Long id);

    /**
     * 根据条件查询
     * @param album
     * @return
     */
    List<Album> findList(Album album);

    /**
     * 无条件查询分页
     * @param page
     * @param size
     * @return
     */
    PageInfo<Album> findPage(Integer page, Integer size);

    /**
     * 条件查询分页
     * @param album
     * @param page
     * @param size
     * @return
     */
    PageInfo<Album> findPage(Album album, Integer page, Integer size);
}
