package com.finalPrj.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.finalPrj.reggie.entity.Orders;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper extends BaseMapper<Orders> {

}