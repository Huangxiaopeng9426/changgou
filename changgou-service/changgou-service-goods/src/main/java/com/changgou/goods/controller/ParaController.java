package com.changgou.goods.controller;

import com.changgou.entity.Result;
import com.changgou.entity.StatusCode;
import com.changgou.goods.pojo.Para;
import com.changgou.goods.service.ParaService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(("/para"))
public class ParaController {
    @Autowired
    private ParaService paraService;

    @GetMapping
    public Result<List<Para>> findAll(){
      List<Para> paraList = paraService.findAll();
      return new Result<>(true, StatusCode.OK,"查询成功",paraList);
    }
    @GetMapping("/{id}")
    public Result findById(@PathVariable Integer id){
         Para para = paraService.findById(id);
         return new Result(true,StatusCode.OK,"查询成功",para);
    }

    @PostMapping
    public Result add(@RequestBody Para para){
        paraService.add(para);
        return new Result(true,StatusCode.OK,"添加成功");
    }
    @PutMapping("/{id}")
    public Result updateById(@RequestBody Para para,@PathVariable Integer id){
        para.setId(id);
        paraService.updateById(para);
        return new Result(true,StatusCode.OK,"更新成功");
    }
    @DeleteMapping("/id")
    public Result deleteById(@PathVariable Integer id){
        paraService.deleteById(id);
        return new Result(true,StatusCode.OK,"删除成功");
    }

    @PostMapping("/search")
    public Result<List<Para>> findList(@RequestBody Para para){
        List<Para>  paraList =paraService.findList(para);
        return new Result<>(true,StatusCode.OK,"查询成功",paraList);
    }

    @GetMapping("/search/{page}/{size}")
    public Result findPage(@PathVariable Integer page,@PathVariable Integer size){
      PageInfo<Para> pageInfo = paraService.findPage(page,size);
      return new Result(true,StatusCode.OK,"分页成功",pageInfo);
    }

    @PostMapping("/search/{page}/{size}")
    public Result findPage(@RequestBody Para para,@PathVariable Integer page,@PathVariable Integer size){
        PageInfo<Para> pageInfo = paraService.findPage(para,page,size);
        return new Result(true,StatusCode.OK,"条件分页成功",pageInfo);
    }

    @GetMapping("/category/{id}")
    public Result<List<Para>> findCategoryIdByPara(@PathVariable("id") Integer id){
        List<Para> paraList = paraService.findCategoryIdByPara(id);
        return new Result<>(true,StatusCode.OK,"查询商品数据成功",paraList);
    }
}
