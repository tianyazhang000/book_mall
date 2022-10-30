package com.utils;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class JDBCUtils {

    private static DataSource dataSource;
    private static ThreadLocal<Connection> conns = new ThreadLocal<>();

    static {
        try {
            Properties properties = new Properties();
            //读取配置参数
            InputStream resourceAsStream = JDBCUtils.class.getClassLoader().getResourceAsStream("druid.properties");
            properties.load(resourceAsStream);
            //创建数据库连接池
            dataSource = DruidDataSourceFactory.createDataSource(properties);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection(){

        Connection conn = conns.get();
        if(conn == null){
            try{
                conn = dataSource.getConnection();//从数据库连接池中获取链接
                conns.set(conn); //保存到ThreadLocal对象中
                conn.setAutoCommit(false); //手动管理事务
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return conn;
    }
    //提交事务
    public static void commitAndClose(){
        Connection connection = conns.get();
        if(connection != null){
            try {
                connection.commit();//提交事务
                connection.close();//关闭连接，
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            conns.remove();
        }
    }
    //回滚事务，并释放连接
    public static void rollbackAndClose(){
        Connection connection = conns.get();
        if(connection != null){
            try {
                connection.rollback();//回滚事务
                connection.close();//关闭连接，
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            conns.remove();
        }
    }

    public static void closeResourse(Connection conn){
        if(conn != null){
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
