package com.learn.spring.order.feign;

import com.learn.spring.cloud.core.JsonResult;
import com.learn.spring.order.entity.TOrder;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author bill
 * @version 1.0
 * @date 2020/4/22 16:06
 */

@Component
@Slf4j
public class OrderFeignFallBack implements FallbackFactory<OrderFeign> {
    @Override
    public OrderFeign create(Throwable throwable) {
        log.error("服务降级,throwable:{}",throwable.getCause());
        return new OrderFeign() {
            @Override
            public JsonResult addOrder(TOrder order) {
                return JsonResult.failed(throwable.getMessage());
            }
        };
    }
}
