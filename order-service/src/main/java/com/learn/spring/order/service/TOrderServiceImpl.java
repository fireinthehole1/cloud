package com.learn.spring.order.service;

import com.codingapi.tx.annotation.TxTransaction;
import com.learn.spring.cloud.core.GlobalException;
import com.learn.spring.cloud.core.JsonResult;
import com.learn.spring.order.entity.TOrder;
import com.learn.spring.order.mapper.TOrderMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author bill
 * @version 1.0
 * @date 2020/4/22 16:27
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TOrderServiceImpl implements TOrderService {

    @Resource
    TOrderMapper tOrderMapper;
    @Override
    @TxTransaction(rollbackFor = Exception.class)
    public JsonResult addOrder(TOrder order) {
        int i = tOrderMapper.addOrder(order);
        if (i==1){
            return JsonResult.success();
        }
        throw new GlobalException("操作失败");
    }
}
