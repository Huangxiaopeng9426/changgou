package com.changgou.goods.controller;

import com.changgou.goods.pojo.Brand;
import com.changgou.goods.service.BrandService;
import com.changgou.entity.Result;
import com.changgou.entity.StatusCode;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/brand")
@CrossOrigin
public class BrandController {
    @Autowired
    private BrandService brandService;

    @GetMapping
    public Result<Brand> findAll(){
        List<Brand> brands = brandService.findAll();
        return new Result<Brand>(true, StatusCode.OK,"查询成功",brands);
    }

    @GetMapping("/{id}")
    public Result<Brand> findById(@PathVariable Integer id){
        Brand brand = brandService.findById(id);
        return new Result<Brand>(true,StatusCode.OK,"查询成功",brand);
    }

    @PostMapping
    public Result addBrand(@RequestBody Brand brand){
        brandService.add(brand);
        return new Result(true, StatusCode.OK,"添加成功");
    }

    @PutMapping("/{id}")
    public Result updateById(@RequestBody Brand brand ,@PathVariable Integer id){
        //设置Id
        brand.setId(id);
        brandService.update(brand);
        return new Result(true,StatusCode.OK,"更新成功");
    }

    @DeleteMapping("/{id}")
    public Result deleteById(@PathVariable Integer id){
        brandService.deleteById(id);
        return new Result(true,StatusCode.OK,"删除成功");
    }

    @PostMapping("/search")
    public Result<List<Brand>> findList(@RequestBody Brand brand){
        List<Brand> brandList =brandService.findList(brand);
        return new Result<List<Brand>>(true,StatusCode.OK,"条件查询成功",brandList);
    }

    @GetMapping("/search/{page}/{size}")
    public Result<PageInfo> findPage(@PathVariable Integer page,@PathVariable Integer size ){
        PageInfo<Brand>info = brandService.findPage(page,size);
        return new Result<>(true,StatusCode.OK,"分页查询成功",info);
    }

    @PostMapping("/search/{page}/{size}")
    public Result<PageInfo> findPage(@RequestBody Brand brand,@PathVariable Integer page,@PathVariable Integer size){
        PageInfo<Brand> pageInfo = brandService.findPage(brand,page,size);
        return new Result<>(true,StatusCode.OK,"条件查询分页成功",pageInfo);
    }

    /**
     * 根据分类的id获取品牌所对应的品牌数据信息
     * @param id
     * @return
     */
    @GetMapping("/category/{id}")
    public Result<List<Brand>> findCategoryIdByBrand(@PathVariable("id") Integer id){
        List<Brand> brandList = brandService.findCategoryIdByBrand(id);
        return new Result<>(true,StatusCode.OK,"查询成功",brandList);
    }
}
