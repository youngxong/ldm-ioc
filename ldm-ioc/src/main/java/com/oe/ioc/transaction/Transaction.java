package com.oe.ioc.transaction;

import java.sql.Connection;
import java.sql.SQLException;

public class Transaction {

    Connection connection;

    public Transaction(Connection connection) {
        this.connection = connection;
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Transaction() {

    }

    public void commit() throws SQLException {
        connection.commit();
        System.out.println("提交事务");
    }

    public Connection getConnection() {
        return connection;
    }

    public void close() throws SQLException {
        connection.close();
        System.out.println("关闭事务");
    }

    public void rollback() throws SQLException {
        connection.rollback();
        System.out.println("回滚");
    }

}
