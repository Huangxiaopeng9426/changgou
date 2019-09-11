package com.changgou.goods.dao;

import com.changgou.goods.pojo.Spec;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface SpecMapper extends Mapper<Spec> {
    @Select("SELECT tbs.* FROM tb_category tb,tb_spec tbs WHERE tb.`id`=tbs.`template_id` AND tbs.`template_id`=#{id}")
    List<Spec> findByCategoryId(Integer id);
}
