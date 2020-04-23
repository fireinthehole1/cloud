package com.learn.spring.order.mapper;

import com.learn.spring.cloud.core.JsonResult;
import com.learn.spring.order.entity.TOrder;

public interface TOrderMapper {

    int addOrder(TOrder order);
}