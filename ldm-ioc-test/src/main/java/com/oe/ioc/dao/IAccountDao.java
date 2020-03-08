package com.oe.ioc.dao;

import java.math.BigDecimal;

public interface IAccountDao {

    /**
     * 转出
     * @param accountno
     * @param money
     */
    void transferOut(String accountno, BigDecimal money);

    /**
     * 转入
     * @param accountno
     * @param money
     */
    void transferIn(String accountno, BigDecimal money);
}
