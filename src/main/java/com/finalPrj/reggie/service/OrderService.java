package com.finalPrj.reggie.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.finalPrj.reggie.entity.Orders;

public interface OrderService extends IService<Orders> {

    /**
     * 用户下单
     * @param orders
     */
    public void submit(Orders orders);

    /**
     * 后台分页查询订单
     * @param page 页码
     * @param pageSize 每页条数
     * @param number 订单号（可选）
     * @param beginTime 开始时间（可选）
     * @param endTime 结束时间（可选）
     * @return
     */
    Page<Orders> getOrderPage(int page, int pageSize, String number, String beginTime, String endTime);

}
