package com.changgou.user.feign;

import com.changgou.entity.Result;
import com.changgou.user.pojo.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "user")
@RequestMapping("/user")
public interface UserFeign {
    /**
     * 根据用户名查询用户信息
     * @param id
     * @return
     */
    @GetMapping("/load/{id}")
   Result<User> findByUsername(@PathVariable(value = "id") String id);

    /**
     * 给用户增加积分
     * @param points
     * @param username
     * @return
     */
    @GetMapping("/points/add")
    Result addPoints(@RequestParam(value = "points") Integer points, @RequestParam("username")String username);
}
