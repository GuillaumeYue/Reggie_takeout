package com.finalPrj.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.finalPrj.reggie.entity.OrderDetail;
import com.finalPrj.reggie.mapper.OrderDetailMapper;
import com.finalPrj.reggie.service.OrderDetailService;
import org.springframework.stereotype.Service;

@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements OrderDetailService {

}