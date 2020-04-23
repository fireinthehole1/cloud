package com.learn.spring.order.feign;

import com.learn.spring.cloud.core.JsonResult;
import com.learn.spring.order.entity.TOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author bill
 * @version 1.0
 * @date 2020/4/22 16:05
 */
@FeignClient(value = "order-server", url = "${feign.order.url}", fallbackFactory = OrderFeignFallBack.class)
public interface OrderFeign {

    @RequestMapping("/order-service/add")
    public JsonResult addOrder(@RequestBody TOrder order);
}
