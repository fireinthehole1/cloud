package com.learn.spring.user.controller;

import com.learn.spring.cloud.core.JsonResult;
import com.learn.spring.user.entity.TAccount;
import com.learn.spring.user.feign.AccountFeign;
import com.learn.spring.user.service.TAccountService;
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
public class TAccountController implements AccountFeign {

    @Autowired
    TAccountService tAccountService;

    @Override
    public JsonResult deduct(@RequestBody TAccount account) {

        return tAccountService.deduct(account);
    }
}
