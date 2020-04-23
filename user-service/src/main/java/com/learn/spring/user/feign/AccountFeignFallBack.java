package com.learn.spring.user.feign;

import com.learn.spring.cloud.core.JsonResult;
import com.learn.spring.user.entity.TAccount;
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
public class AccountFeignFallBack implements FallbackFactory<AccountFeign> {
    @Override
    public AccountFeign create(Throwable throwable) {
        return new AccountFeign() {
            @Override
            public JsonResult deduct(TAccount account) {
                return JsonResult.failed(throwable.getMessage());
            }
        };
    }
}
