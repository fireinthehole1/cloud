package com.learn.spring.user.service;

import com.codingapi.tx.annotation.TxTransaction;
import com.learn.spring.cloud.core.GlobalException;
import com.learn.spring.cloud.core.JsonResult;
import com.learn.spring.user.entity.TAccount;
import com.learn.spring.user.mapper.TAccountMapper;
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
public class TAccountServiceImpl implements TAccountService {

    @Resource
    TAccountMapper tAccountMapper;
    @Override
    @TxTransaction(rollbackFor = Exception.class)
    public JsonResult deduct(TAccount account) {
        int deduct = tAccountMapper.deduct(account);
        /*if (deduct==1){
            return JsonResult.success();
        }*/
        throw new GlobalException("操作失败");
    }
}
