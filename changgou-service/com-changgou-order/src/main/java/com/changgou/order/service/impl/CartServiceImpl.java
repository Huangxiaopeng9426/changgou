package com.changgou.order.service.impl;

import com.changgou.entity.Result;
import com.changgou.goods.feign.SkuFeign;
import com.changgou.goods.feign.SpuFeign;
import com.changgou.goods.pojo.Sku;
import com.changgou.goods.pojo.Spu;
import com.changgou.order.pojo.OrderItem;
import com.changgou.order.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    @Autowired(required = false)
    private SkuFeign skuFeign;

    @Autowired(required = false)
    private SpuFeign spuFeign;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void add(Long id, Integer num, String username) {

        if (num<=0) {
            redisTemplate.boundHashOps("Cart_"+username).delete(id);
            return;
        }

        //1.根据sku的id 获取sku的数据
        Result<Sku> skuResult = skuFeign.findById(id);
        Sku data = skuResult.getData();
        if (data != null) {
            //2.根据sku数据的对象 获取该sku对应的spu数据
            Long spuId = data.getSpuId();
            Result<Spu> spuResult = spuFeign.findById(spuId);
            Spu spu = spuResult.getData();
            //3.将数据封装到 购物车的pojo中
            OrderItem orderItem = new OrderItem();
            orderItem.setCategoryId1(spu.getCategory1Id());
            orderItem.setCategoryId2(spu.getCategory2Id());
            orderItem.setCategoryId3(spu.getCategory3Id());
            orderItem.setSpuId(spuId);
            orderItem.setSkuId(id);
            orderItem.setName(data.getName());  //sku的名称
            orderItem.setPrice(data.getPrice()); //sku的单价
            orderItem.setNum(num);
            orderItem.setMoney(orderItem.getNum()*orderItem.getPrice()); //数量*单价
            orderItem.setPayMoney(orderItem.getNum()*orderItem.getPrice());
            orderItem.setImage(data.getImage());  //sku的图片
            //4.将数据存储到Redis中
            redisTemplate.boundHashOps("Cart_"+username).put(id,orderItem);
        }
    }

    /**
     * 根据用户名获取购物车的列表数据
     * @param username
     * @return
     */
    @Override
    public List<OrderItem> list(String username) {
        List<OrderItem> orderItems = redisTemplate.boundHashOps("Cart_" + username).values();
        return orderItems;
    }
}
