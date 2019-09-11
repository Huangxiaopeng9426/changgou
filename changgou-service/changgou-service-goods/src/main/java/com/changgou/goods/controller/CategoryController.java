package com.changgou.goods.controller;


import com.changgou.entity.Result;
import com.changgou.entity.StatusCode;
import com.changgou.goods.pojo.Category;
import com.changgou.goods.service.CategoryService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public Result<List<Category>> findAll(){
        List<Category> categoryList = categoryService.findAll();
        return new Result<>(true, StatusCode.OK,"查询成功",categoryList);
    }
    @GetMapping("/{id}")
    public Result<Category> findById(@PathVariable Integer id){
       Category category = categoryService.findById(id);
       return new Result(true,StatusCode.OK,"查询成功",category);
    }

    @PostMapping
    public Result add(@RequestBody Category category){
        categoryService.add(category);
        return new Result(true,StatusCode.OK,"添加数据成功");
    }

    @PutMapping("/{id}")
    public Result updateById(@RequestBody Category category,@PathVariable Integer id ){
        category.setId(id);
        categoryService.updateById(category);
        return new Result(true,StatusCode.OK,"更新数据成功");
    }
    @DeleteMapping("/{id}")
    public Result deleteById(@PathVariable Integer id){
        categoryService.deleteById(id);
        return new Result(true,StatusCode.OK,"删除成功");
    }

    /**
     * 根据父节点的id查询数据
     * @param pid
     * @return
     */
    @GetMapping("/list/{pid}")
    public Result<Category> findByParentId(@PathVariable Integer pid){
       List<Category> categoryList = categoryService.findByParentId(pid);
       return new Result<>(true,StatusCode.OK,"查询成功",categoryList);
    }
    @PostMapping("/search")
    public Result<List<Category>> findList(@RequestBody Category category){
        List<Category> categoryList = categoryService.findList(category);
        return new Result<>(true,StatusCode.OK,"查询成功",categoryList);
    }
    @GetMapping("/search/{page}/{size}")
    public Result findPage(@PathVariable Integer page,@PathVariable Integer size){
       PageInfo<Category> pageInfo = categoryService.findPage(page,size);
       return new Result(true,StatusCode.OK,"分页查询成功",pageInfo);
    }
    @PostMapping("/search/{page}/{size}")
    public Result findPage( @RequestBody Category category,@PathVariable Integer page,@PathVariable Integer size){
        PageInfo<Category> pageInfo = categoryService.findPage(category,page,size);
        return new Result(true,StatusCode.OK,"分页查询成功",pageInfo);
    }
}
