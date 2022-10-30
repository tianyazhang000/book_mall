package com.dao.impl;

import com.utils.JDBCUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class BaseDao {
    private QueryRunner queryRunner = new QueryRunner();

    public int update(String sql,Object...args){
        Connection conn = JDBCUtils.getConnection();

        try {
            return queryRunner.update(conn,sql,args);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> T queryForOne(Class<T> type, String sql,Object...args){
        Connection conn = JDBCUtils.getConnection();
        try {
            return queryRunner.query(conn,sql,new BeanHandler<T>(type),args);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> List <T> queryForList(Class<T> type,String sql,Object...args){
        Connection conn = JDBCUtils.getConnection();
        try {
            return queryRunner.query(conn,sql,new BeanListHandler<T>(type),args);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Object queryForSingleValue(String sql,Object...args){
        Connection conn = JDBCUtils.getConnection();

        try {
            return queryRunner.query(conn,sql,new ScalarHandler(),args);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
