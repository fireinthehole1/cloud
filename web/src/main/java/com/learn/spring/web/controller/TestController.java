package com.learn.spring.web.controller;

import com.codingapi.tx.annotation.TxTransaction;
import com.learn.spring.cloud.core.JsonResult;
import com.learn.spring.order.entity.TOrder;
import com.learn.spring.order.feign.OrderFeign;
import com.learn.spring.user.entity.TAccount;
import com.learn.spring.user.feign.AccountFeign;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author bill
 * @version 1.0
 * @date 2020/4/22 17:53
 */
@RequestMapping("/webapi/test")
@RestController
public class TestController {
    @Resource
    OrderFeign orderFeign;
    @Resource
    AccountFeign accountFeign;



    @RequestMapping("/buy")
    @TxTransaction(isStart = true,rollbackFor = Exception.class)
    public JsonResult handle(){

        TOrder order=new TOrder();
        order.setName("test");
        order.setPrice(111.0);
        order.setCreateTime(new Date());
        order.setUpdateTime(new Date());
        orderFeign.addOrder(order).getDataWithEx();

        TAccount account=new TAccount();
        account.setMoney("111");
        account.setName("2222");
        account.setCreateTime(new Date());
        account.setUpdateTime(new Date());
        JsonResult deduct = accountFeign.deduct(account);
        deduct.getDataWithEx();
        return JsonResult.success().setMsg("操作成功");
    }

}
