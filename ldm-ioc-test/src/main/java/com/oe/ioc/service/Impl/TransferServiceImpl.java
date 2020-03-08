package com.oe.ioc.service.Impl;

import com.oe.ioc.annotation.Autowired;
import com.oe.ioc.annotation.LdmComponent;
import com.oe.ioc.annotation.Transactional;
import com.oe.ioc.dao.IAccountDao;
import com.oe.ioc.service.ITransferService;

import java.math.BigDecimal;

@LdmComponent("transferService")
public class TransferServiceImpl implements ITransferService {

    @Autowired(name = "accountDao")
    IAccountDao accountDao;

    @Transactional
    public void transfer(String accountno1, String accountno2, BigDecimal money) {
        accountDao.transferOut(accountno1,money);
        /**
         * 演示异常,回滚事务
         */
        System.out.println(1/0);
        accountDao.transferIn(accountno2,money);
    }
}
