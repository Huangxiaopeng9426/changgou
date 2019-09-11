package com.changgou.order.service;

import com.changgou.order.pojo.OrderItem;

import java.util.List;

public interface CartService {
    /**
     * 添加购物车
     * @param id  sku的id
     * @param num 购买的数量
     * @param username 购买的用户名
     */
    void add(Long id, Integer num, String username);

    /**
     * 根据登录的用户获取该用户的购物车列表
     * @param username
     * @return
     */
    List<OrderItem> list(String username);
}
