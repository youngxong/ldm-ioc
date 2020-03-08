package com.oe.ioc.transaction;

import com.oe.ioc.transaction.Transaction;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * 事务管理器
 */
public class TransactionManager {

    private  DataSource dataSource;

    private TransactionManager() {
    }

    public static final TransactionManager manager = new TransactionManager();

    public static TransactionManager getInstance(){
        return manager;
    }

    public  ThreadLocal<Transaction> threadLocal = new ThreadLocal<>();

    public  Transaction  start() throws SQLException {
        System.out.println("开启一个事务");
        Transaction transaction=null;
        if(threadLocal.get()==null){
            transaction = new Transaction(dataSource.getConnection());
            threadLocal.set(transaction);
        }
        return transaction;

    };

    public Transaction getTransaction() throws SQLException {
        return  threadLocal.get();
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
