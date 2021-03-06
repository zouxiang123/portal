/**   
 * @Title: DBUtil.java 
 * @Package com.xtt.platform
 * Copyright: Copyright (c) 2015
 * @author: bruce   
 * @date: 2016年6月22日 上午11:24:50 
 *
 */
package com.xtt.platform.util;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidPooledConnection;
import com.xtt.platform.util.config.SpringUtil;

public class DBUtil {
    private static DruidDataSource dds;
    static {
        dds = (DruidDataSource) SpringUtil.getBean("dataSource");
    }

    /**
     * 返回单个结果值，如count\min\max等
     * 
     * @param sql
     *            sql语句
     * @return 结果
     * @throws SQLException
     */
    public static Object getSingle(String sql) throws SQLException {
        Object result = null;
        DruidPooledConnection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = dds.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            if (rs.next()) {
                result = rs.getObject(1);
            }
            return result;
        } catch (SQLException e) {
            throw e;
        } finally {
            free(conn, stmt, rs);
        }
    }

    /**
     * 返回单个结果值，如count\min\max等
     * 
     * @param sql
     *            sql语句
     * @param paramters
     *            参数列表
     * @return 结果
     * @throws SQLException
     */
    public static Object getSingle(String sql, Object[] paramters) throws SQLException {
        Object result = null;
        DruidPooledConnection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = dds.getConnection();
            stmt = conn.prepareStatement(sql);
            if (paramters != null) {
                for (int i = 0; i < paramters.length; i++) {
                    stmt.setObject(i + 1, paramters[i]);
                }
            }
            rs = stmt.executeQuery();
            if (rs.next()) {
                result = rs.getObject(1);
            }
            return result;
        } catch (SQLException e) {
            throw e;
        } finally {
            free(conn, stmt, rs);
        }
    }

    /**
     * 用于查询，返回结果集
     * 
     * @param sql
     *            sql语句
     * @return 结果集
     * @throws SQLException
     */
    public static List<Map<String, Object>> query(String sql) throws SQLException {
        DruidPooledConnection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = dds.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            return ResultToListMap(rs);
        } catch (SQLException e) {
            throw e;
        } finally {
            free(conn, stmt, rs);
        }
    }

    /**
     * 用于带参数的查询，返回结果集
     * 
     * @param sql
     *            sql语句
     * @param paramters
     *            参数集合
     * @return 结果集
     * @throws SQLException
     */
    public static List<Map<String, Object>> query(String sql, Object[] paramters) throws SQLException {
        DruidPooledConnection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = dds.getConnection();
            stmt = conn.prepareStatement(sql);
            if (paramters != null) {
                for (int i = 0; i < paramters.length; i++) {
                    stmt.setObject(i + 1, paramters[i]);
                }
            }
            rs = stmt.executeQuery();
            return ResultToListMap(rs);
        } catch (SQLException e) {
            throw new SQLException(e);
        } finally {
            free(conn, stmt, rs);
        }
    }

    /**
     * 用于增删改
     * 
     * @param sql
     *            sql语句
     * @return 影响行数
     * @throws SQLException
     */
    public static int update(String sql) throws SQLException {
        DruidPooledConnection conn = null;
        Statement stmt = null;
        try {
            conn = dds.getConnection();
            stmt = conn.createStatement();
            return stmt.executeUpdate(sql);
        } catch (SQLException e) {
            throw e;
        } finally {
            free(conn, stmt, null);
        }
    }

    /**
     * 用于增删改（带参数）
     * 
     * @param sql
     *            sql语句
     * @param paramters
     *            sql语句
     * @return 影响行数
     * @throws SQLException
     */
    public static int update(String sql, Object[] paramters) throws SQLException {
        DruidPooledConnection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = dds.getConnection();
            stmt = conn.prepareStatement(sql);
            if (paramters != null) {
                for (int i = 0; i < paramters.length; i++) {
                    stmt.setObject(i + 1, paramters[i]);
                }
            }
            return stmt.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException(e);
        } finally {
            free(conn, stmt, null);
        }
    }

    /**
     * 插入值后返回主键值
     * 
     * @param sql
     *            插入sql语句
     * @return 返回结果
     * @throws Exception
     */
    public static Object insertWithReturnPrimeKey(String sql) throws SQLException {
        DruidPooledConnection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        Object result = null;
        try {
            conn = dds.getConnection();
            stmt = conn.createStatement();
            stmt.execute(sql, Statement.RETURN_GENERATED_KEYS);
            rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                result = rs.getObject(1);
            }
            return result;
        } catch (SQLException e) {
            throw new SQLException(e);
        } finally {
            free(conn, stmt, rs);
        }
    }

    /**
     * 插入值后返回主键值
     * 
     * @param sql
     *            插入sql语句
     * @param paramters
     *            参数列表
     * @return 返回结果
     * @throws SQLException
     */
    public static Object insertWithReturnPrimeKey(String sql, Object[] paramters) throws SQLException {
        DruidPooledConnection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Object result = null;
        try {
            conn = dds.getConnection();
            stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            if (paramters != null) {
                for (int i = 0; i < paramters.length; i++) {
                    stmt.setObject(i + 1, paramters[i]);
                }
            }
            stmt.execute();
            rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                result = rs.getObject(1);
            }
            return result;
        } catch (SQLException e) {
            throw new SQLException(e);
        } finally {
            free(conn, stmt, rs);
        }
    }

    /**
     * 调用存储过程执行查询
     * 
     * @param procedureSql
     *            存储过程
     * @return
     * @throws SQLException
     */
    public static List<Map<String, Object>> callableQuery(String procedureSql) throws SQLException {
        return callableQuery(procedureSql, null);
    }

    /**
     * 调用存储过程（带参数）,执行查询
     * 
     * @param procedureSql
     *            存储过程
     * @param paramters
     *            参数表
     * @return
     * @throws SQLException
     */
    public static List<Map<String, Object>> callableQuery(String procedureSql, Object[] paramters) throws SQLException {
        DruidPooledConnection conn = null;
        CallableStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = dds.getConnection();
            stmt = conn.prepareCall(procedureSql);
            if (paramters != null) {
                for (int i = 0; i < paramters.length; i++) {
                    stmt.setObject(i + 1, paramters[i]);
                }
            }
            rs = stmt.executeQuery();
            return ResultToListMap(rs);
        } catch (SQLException e) {
            throw new SQLException(e);
        } finally {
            free(conn, stmt, rs);
        }
    }

    /**
     * 调用存储过程，查询单个值
     * 
     * @param procedureSql
     * @return
     * @throws SQLException
     */
    public static Object callableGetSingle(String procedureSql) throws SQLException {
        return callableGetSingle(procedureSql, null);
    }

    /**
     * 调用存储过程(带参数)，查询单个值
     * 
     * @param procedureSql
     * @param parameters
     * @return
     * @throws SQLException
     */
    public static Object callableGetSingle(String procedureSql, Object[] paramters) throws SQLException {
        DruidPooledConnection conn = null;
        CallableStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = dds.getConnection();
            stmt = conn.prepareCall(procedureSql);
            if (paramters != null) {
                for (int i = 0; i < paramters.length; i++) {
                    stmt.setObject(i + 1, paramters[i]);
                }
            }
            rs = stmt.executeQuery();
            return ResultToListMap(rs);
        } catch (SQLException e) {
            throw new SQLException(e);
        } finally {
            free(conn, stmt, rs);
        }
    }

    /**
     * 调用存储过程，执行增删改
     * 
     * @param procedureSql
     *            存储过程
     * @return 影响行数
     * @throws SQLException
     */
    public static int callableUpdate(String procedureSql) throws SQLException {
        return callableUpdate(procedureSql, null);
    }

    /**
     * 调用存储过程（带参数），执行增删改
     * 
     * @param procedureSql
     *            存储过程
     * @param parameters
     * @return 影响行数
     * @throws SQLException
     */
    public static int callableUpdate(String procedureSql, Object[] parameters) throws SQLException {
        DruidPooledConnection conn = null;
        CallableStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = dds.getConnection();
            stmt = conn.prepareCall(procedureSql);
            if (parameters != null) {
                for (int i = 0; i < parameters.length; i++) {
                    stmt.setObject(i + 1, parameters[i]);
                }
            }
            return stmt.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException(e);
        } finally {
            free(conn, stmt, rs);
        }
    }

    /** 将结果集转成map */
    private static List<Map<String, Object>> ResultToListMap(ResultSet rs) throws SQLException {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        while (rs.next()) {
            ResultSetMetaData md = rs.getMetaData();
            Map<String, Object> map = new HashMap<String, Object>();
            for (int i = 1; i <= md.getColumnCount(); i++) {
                map.put(md.getColumnLabel(i), rs.getObject(i));
            }
            list.add(map);
        }
        return list;
    }

    /**
     * 释放资源
     * 
     * @param conn
     * @param statement
     * @param rs
     */
    public static void free(DruidPooledConnection conn, Statement statement, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
