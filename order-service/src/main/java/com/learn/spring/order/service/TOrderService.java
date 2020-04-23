package com.learn.spring.order.service;

import com.learn.spring.cloud.core.JsonResult;
import com.learn.spring.order.entity.TOrder;

/**
 * @author bill
 * @version 1.0
 * @date 2020/4/22 16:27
 */
public interface TOrderService {

    JsonResult addOrder(TOrder order);

}
