package com.learn.spring.order.controller;

import com.learn.spring.cloud.core.JsonResult;
import com.learn.spring.order.entity.TOrder;
import com.learn.spring.order.feign.OrderFeign;
import com.learn.spring.order.service.TOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author bill
 * @version 1.0
 * @date 2020/4/22 16:26
 */
@RestController
@ResponseBody
public class TOrderController implements OrderFeign {

    @Autowired
    TOrderService tOrderService;

    @Override
    public JsonResult addOrder(@RequestBody TOrder order) {
        return tOrderService.addOrder(order);
    }
}
