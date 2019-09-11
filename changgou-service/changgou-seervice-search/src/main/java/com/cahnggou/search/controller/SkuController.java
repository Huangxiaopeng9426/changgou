package com.cahnggou.search.controller;

import com.cahnggou.search.service.SkuService;
import com.changgou.entity.Result;
import com.changgou.entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/search")
@CrossOrigin
public class SkuController {

    @Autowired
    private SkuService skuService;

    @GetMapping("/import")
    public Result search(){
        skuService.importSku();
        return new Result(true, StatusCode.OK,"导入数据到索引库中成功");
    }

    @GetMapping
    public Map search(@RequestParam(required = false) Map searchMap){
        Object pageNum = searchMap.get("pageNum");
        if(pageNum==null){
            searchMap.put("pageNum","1");
        }
        if(pageNum instanceof Integer){
            searchMap.put("pageNum",pageNum.toString());
        }
       return skuService.search(searchMap);
    }


}
