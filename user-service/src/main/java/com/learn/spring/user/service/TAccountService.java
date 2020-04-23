package com.learn.spring.user.service;

import com.learn.spring.cloud.core.JsonResult;
import com.learn.spring.user.entity.TAccount;

/**
 * @author bill
 * @version 1.0
 * @date 2020/4/22 16:27
 */
public interface TAccountService {


    JsonResult deduct(TAccount account);
}
