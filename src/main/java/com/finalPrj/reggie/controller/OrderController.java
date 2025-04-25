package com.finalPrj.reggie.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.finalPrj.reggie.common.R;
import com.finalPrj.reggie.entity.Orders;
import com.finalPrj.reggie.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 订单
 */
@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 用户下单
     * @param orders
     * @return
     */
    @PostMapping("/submit")
    public R<String> submit(@RequestBody Orders orders){
        log.info("订单数据：{}",orders);
        orderService.submit(orders);
        return R.success("下单成功");
    }

    /**
     * 后台分页查询订单
     * @param page 页码
     * @param pageSize 每页条数
     * @param number 订单号（可选）
     * @param beginTime 开始时间（可选）
     * @param endTime 结束时间（可选）
     * @return
     */
    @GetMapping("/page")
    public R<Page<Orders>> getPage(int page, int pageSize, String number, String beginTime, String endTime) {
        Page<Orders> result = orderService.getOrderPage(page, pageSize, number, beginTime, endTime);
        return R.success(result);
    }

    /**
     * 后台订单分页查询
     * @param page 当前页
     * @param pageSize 每页条数
     * @param number 订单号（可选）
     * @param beginTime 开始时间（可选）
     * @param endTime 结束时间（可选）
     * @return 分页订单数据
     */
    @GetMapping("/list")
    public R<Page<Orders>> list(int page, int pageSize, String number, String beginTime, String endTime) {
        log.info("订单分页查询：page={}, pageSize={}, number={}, beginTime={}, endTime={}", page, pageSize, number, beginTime, endTime);

        Page<Orders> pageInfo = orderService.getOrderPage(page, pageSize, number, beginTime, endTime);

        return R.success(pageInfo);
    }

}