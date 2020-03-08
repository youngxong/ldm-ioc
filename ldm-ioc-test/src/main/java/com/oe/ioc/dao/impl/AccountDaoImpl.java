package com.oe.ioc.dao.impl;

import com.oe.ioc.annotation.LdmComponent;
import com.oe.ioc.dao.IAccountDao;
import com.oe.ioc.transaction.Transaction;
import com.oe.ioc.transaction.TransactionManager;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;

@LdmComponent("accountDao")
public class AccountDaoImpl implements IAccountDao {

    public void transferOut(String accountno, BigDecimal money) {
        try{
            Transaction transaction = TransactionManager.getInstance().getTransaction();
            Connection connection = transaction.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("update account set money=money-"+money+" where accountno="+accountno);
            preparedStatement.executeUpdate();
        }catch (Exception e){
            throw new RuntimeException("数据库操作异常!");
        }

    }

    public void transferIn(String accountno, BigDecimal money) {
        try{
            Transaction transaction = TransactionManager.getInstance().getTransaction();
            Connection connection = transaction.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("update account  set money=money+"+money+" where accountno="+accountno);
            preparedStatement.executeUpdate();
        }catch (Exception e){
            throw new RuntimeException("数据库操作异常!");
        }
    }
}
