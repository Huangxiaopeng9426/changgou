package com.changgou.goods.controller;

import com.changgou.entity.Result;
import com.changgou.entity.StatusCode;
import com.changgou.goods.pojo.Spec;
import com.changgou.goods.service.SpecService;
import com.github.pagehelper.PageInfo;
import jdk.net.SocketFlow;
import org.apache.ibatis.javassist.bytecode.LineNumberAttribute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/spec")
public class SpecController {
    @Autowired
    private SpecService specService;

    @GetMapping
    public Result<List<Spec>> findAll(){
        List<Spec> list = specService.findAll();
        return new Result<>(true, StatusCode.OK,"查询成功",list);
    }

    @GetMapping("/{id}")
    public Result findById(@PathVariable Integer id){
        Spec spec = specService.findById(id);
        return new Result(true,StatusCode.OK,"查询成功",spec);
    }
    @PostMapping
    public Result add(@RequestBody Spec spec){
        specService.add(spec);
        return new Result(true,StatusCode.OK,"添加成功");
    }
    @PutMapping("/{id}")
    public Result<Spec> update(@RequestBody Spec spec,@PathVariable Integer id){
        spec.setId(id);
        specService.updateById(spec);
        return new Result<>(true,StatusCode.OK,"更新成功");
    }
    @DeleteMapping("/{id}")
    public Result deleteById(@PathVariable Integer id){
        specService.deleteById(id);
        return new Result(true,StatusCode.OK,"删除成功");
    }

    @PostMapping("/search")
    public Result<List<Spec>> findList(@RequestBody Spec spec){
        List<Spec> list = specService.findList(spec);
        return new Result<>(true,StatusCode.OK,"条件查询成功",list);
    }
    @GetMapping("/search/{page}/{size}")
    public Result findPage(@PathVariable Integer page, @PathVariable Integer size){
        PageInfo<Spec> pageInfo = specService.findPage(page,size);
        return new Result(true,StatusCode.OK,"分页成功",pageInfo);
    }

    @PostMapping("/search/{page}/{size}")
    public Result findPage(@RequestBody Spec spec,@PathVariable Integer page,@PathVariable Integer size){
        PageInfo<Spec> pageInfo = specService.findPage(spec,page,size);
        return new Result(true,StatusCode.OK,"分页查询成功",pageInfo);
    }

    @GetMapping("/category/{id}")
    public Result<List<Spec>> findByCategoryId(@PathVariable Integer id){
       List<Spec> specList = specService.findByCategoryId(id);
       return new Result<>(true,StatusCode.OK,"查询参数成功",specList);
    }
}
