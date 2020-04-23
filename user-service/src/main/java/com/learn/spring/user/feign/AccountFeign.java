package com.learn.spring.user.feign;

import com.learn.spring.cloud.core.JsonResult;
import com.learn.spring.user.entity.TAccount;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author bill
 * @version 1.0
 * @date 2020/4/22 16:05
 */
@FeignClient(value = "user-server", url = "${feign.user.url}", fallbackFactory = AccountFeignFallBack.class)
public interface AccountFeign {

    @RequestMapping("/account-service/add")
    public  JsonResult deduct(@RequestBody TAccount account);
}
