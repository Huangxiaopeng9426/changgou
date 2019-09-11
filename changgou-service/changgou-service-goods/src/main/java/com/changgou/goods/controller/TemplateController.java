package com.changgou.goods.controller;

import com.changgou.entity.Result;
import com.changgou.entity.StatusCode;
import com.changgou.goods.pojo.Template;
import com.changgou.goods.service.TemplateService;
import com.github.pagehelper.PageInfo;
import jdk.nashorn.internal.runtime.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/template")
public class TemplateController {
    @Autowired
    private TemplateService templateService;

    @GetMapping
    public Result<List<Template>> findAll(){
        List<Template> list = templateService.findAll();
        return new Result<>(true, StatusCode.OK,"查询成功",list);
    }

    @GetMapping("/{id}")
    public Result<Template> findById(@PathVariable Integer id){
       Template template = templateService.findById(id);
       return new Result<>(true,StatusCode.OK,"查询成功",template);
    }

    @PostMapping
    public Result add(@RequestBody Template template){
        templateService.add(template);
        return new Result(true,StatusCode.OK,"添加成功");
    }

    @PutMapping("/{id}")
    public Result updateById(@RequestBody Template template,@PathVariable Integer id){
        template.setId(id);
        templateService.updateById(template);
        return new Result(true,StatusCode.OK,"更新成功");
    }

    @DeleteMapping("/{id}")
    public Result deleteById(@PathVariable Integer id){
        templateService.deleteById(id);
        return new Result(true,StatusCode.OK,"删除成功");
    }

    @PostMapping("/search")
    public Result<List<Template>> findList(@RequestBody Template template){
       List<Template> list = templateService.findList(template);
       return new Result<>(true,StatusCode.OK,"查询成功",list);
    }

    @GetMapping("/search/{page}/{size}")
    public Result findPage(@PathVariable Integer page ,@PathVariable Integer size){
        PageInfo<Template> pageInfo = templateService.findPage(page,size);
        return new Result(true,StatusCode.OK,"分页成功",pageInfo);
    }

    @PostMapping("/search/{page}/{size}")
    public Result findPage(@RequestBody Template template,@PathVariable Integer page,@PathVariable Integer size){
        PageInfo<Template> pageInfo = templateService.findPage(template,page,size);
        return new Result(true,StatusCode.OK,"分页成功",pageInfo);
    }
}
