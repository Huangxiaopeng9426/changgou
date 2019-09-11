package com.changgou.goods.service.impl;

import com.changgou.goods.dao.AlbumMapper;
import com.changgou.goods.pojo.Album;
import com.changgou.goods.service.AlbumService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class AlbumServiceImpl implements AlbumService {

    @Autowired(required = false)
    private AlbumMapper albumMapper;
    @Override
    public List<Album> findAll() {
        return albumMapper.selectAll();
    }

    @Override
    public void add(Album album) {
        albumMapper.insert(album);
    }

    @Override
    public Album findById(Long id) {
        return albumMapper.selectByPrimaryKey(id);
    }

    @Override
    public void updateById(Album album) {
        albumMapper.updateByPrimaryKey(album);
    }

    @Override
    public void deleteById(Long id) {
        albumMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<Album> findList(Album album) {
        Example example = createExample(album);
        List<Album> albums = albumMapper.selectByExample(example);
        return albums;
    }

    @Override
    public PageInfo<Album> findPage(Integer page, Integer size) {
        //进行分页  没有条件分页
        PageHelper.startPage(page,size);
        return new PageInfo<Album>(albumMapper.selectAll());
    }

    @Override
    public PageInfo<Album> findPage(Album album, Integer page, Integer size) {
        PageHelper.startPage(page,size);
        Example example = createExample(album);
        return new PageInfo<Album>(albumMapper.selectByExample(example));
    }

    private Example createExample(Album album) {
        //创建查询的对象
        Example example = new Example(Album.class);
        Example.Criteria criteria = example.createCriteria();
        //判读album是否为空
        if (album != null) {
            if (!StringUtils.isEmpty(album.getImage())) {
                criteria.andEqualTo("image",album.getImage());
            }
            if (!StringUtils.isEmpty(album.getId())) {
                criteria.andEqualTo("id",album.getId());
            }
            if (!StringUtils.isEmpty(album.getTitle())) {
                criteria.andLike("title","%"+album.getTitle()+"%");
            }
            if (!StringUtils.isEmpty(album.getImageItems())) {
                criteria.andEqualTo("imageItem",album.getImageItems());
            }
        }
        return example;
    }

}
