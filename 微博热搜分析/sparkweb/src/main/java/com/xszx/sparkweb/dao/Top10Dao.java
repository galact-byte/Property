package com.xszx.sparkweb.dao;

import com.xszx.sparkweb.entity.Top10Entity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.List;
//Mapper注解是MyBatis框架中定义的数据层接口，用于标记接口Top10Dao为映射接口
@Mapper
public interface Top10Dao {
    //Select注解用于指定查询操作的SQL语句
    @Select("select \"shareCount\",\"weibo_id\",\"commitCount\",\"viewCount\" from \"top10\"")
    List<Top10Entity> getTop10();
}
