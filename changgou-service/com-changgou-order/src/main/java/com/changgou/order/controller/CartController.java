package com.changgou.order.controller;

import com.changgou.entity.Result;
import com.changgou.entity.StatusCode;
import com.changgou.order.config.TokenDecode;
import com.changgou.order.pojo.OrderItem;
import com.changgou.order.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/cart")
@CrossOrigin
public class CartController {

    @Autowired
    private TokenDecode tokenDecode;

    @Autowired
    private CartService cartService;

    @RequestMapping("/add")
    public Result add(Long id,Integer num){
        //String username = "szitheima";
        Map<String, String> userInfo = tokenDecode.getUserInfo();
        String username = userInfo.get("username");
        System.out.println("哇塞::::::用户名"+username);
        cartService.add(id,num,username);
        return new Result(true, StatusCode.OK,"添加购物车成功");
    }

    @RequestMapping("/list")
    public Result<OrderItem> list(){
        //String username = "szitheima";
        Map<String, String> userInfo = tokenDecode.getUserInfo();
        String username = userInfo.get("username");
        List<OrderItem> orderItemList = cartService.list(username);
        return new Result<>(true,StatusCode.OK,"获取购物车列表成功",orderItemList);
    }
}
