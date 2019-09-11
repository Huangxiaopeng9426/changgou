package com.changgou.goods.controller;

import com.changgou.entity.Page;
import com.changgou.entity.Result;
import com.changgou.entity.StatusCode;
import com.changgou.goods.pojo.Album;
import com.changgou.goods.service.AlbumService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/album")
public class AlbumController {

    @Autowired
    private AlbumService albumService;

    @GetMapping
    public Result<Album> findAll(){
       List<Album> list = albumService.findAll();
       return new Result<Album>(true, StatusCode.OK,"查询成功",list);
    }

    @PostMapping
    public Result add(@RequestBody Album album){
        albumService.add(album);
        return new Result(true,StatusCode.OK,"添加成功");
    }
    @GetMapping("/{id}")
    public Result<Album> findById(@PathVariable("id") Long id){
         Album album = albumService.findById(id);
         return new Result<Album>(true,StatusCode.OK,"查询成功",album);
    }
    @PutMapping("/{id}")
    public Result updateById(@RequestBody Album album,@PathVariable Long id){
          album.setId(id);
         albumService.updateById(album);
        return new Result(true,StatusCode.OK,"更新成功");
    }

    @DeleteMapping("/{id}")
    public Result deleteById(@PathVariable("id") Long id){
        albumService.deleteById(id);
        return new Result(true,StatusCode.OK,"删除成功");
    }
    @PostMapping("/search")
    public Result<List<Album>>findList(@RequestBody Album album){
        List<Album> list = albumService.findList(album);
        return new Result<List<Album>>(true,StatusCode.OK,"条件查询成功",list);
    }

    @GetMapping("/search/{page}/{size}")
    public Result findPage(@PathVariable Integer page,@PathVariable Integer size){
        PageInfo<Album> pageInfo =albumService.findPage(page,size);
        return new Result(true,StatusCode.OK,"查询分页成功",pageInfo);
    }

    @PostMapping("/search/{page}/{size}")
    public Result findPage(@RequestBody Album album,@PathVariable Integer page,@PathVariable Integer size){
        PageInfo<Album> pageInfo = albumService.findPage(album,page,size);
        return new Result(true,StatusCode.OK,"条件分页查询成功");
    }

}
