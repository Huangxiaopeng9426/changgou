package com.changgou.goods.dao;

import com.changgou.goods.pojo.Para;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface ParaMapper extends Mapper<Para> {
    @Select("SELECT tbp.* FROM tb_category tb,tb_para tbp WHERE tb.`id`=tbp.`template_id` AND tbp.`template_id`=#{id}")
    List<Para> findCategoryIdByPara(Integer id);
}
