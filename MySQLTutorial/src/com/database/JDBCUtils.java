package com.database;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * @author Jiahao Wu
 * @date 2021/9/8 - 10:19
 */
public class JDBCUtils {

    /**
     * 获取数据库的连接
     *
     * @return
     * @throws Exception
     */
    public static Connection getConnection() throws Exception {
        InputStream resourceAsStream = ClassLoader.getSystemClassLoader().getResourceAsStream("jdbc.properties");
        Properties pros = new Properties();
        pros.load(resourceAsStream);
        //资源地址URL
        String url = pros.getProperty("url");
        //用户名和密码
        String user = pros.getProperty("user");
        String password = pros.getProperty("password");
        //驱动类名
        String driverClass = pros.getProperty("driverClass");

        //获取Driver接口mysql实现类的实例(加载驱动)
        Class.forName(driverClass);

        //在mysql的Driver实现类中，有静态代码块会自动注册驱动（随着类的加载）
//        Driver driver = (Driver) aClass.newInstance();
//        //注册驱动
//        DriverManager.registerDriver(driver);

        //获取连接
        Connection conn = DriverManager.getConnection(url, user, password);
        return conn;
    }

    /**
     * 关闭连接和Statement
     *
     * @param conn
     * @param ps
     */
    public static void closeResource(Connection conn, Statement ps) {
        try {
            if (ps != null) {
                ps.close();
            }
        } catch (SQLException throwables) {
        }
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException throwables) {
        }
    }

    /**
     * 关闭资源操作
     *
     * @param conn
     * @param ps
     * @param rs
     */
    public static void closeResource(Connection conn, Statement ps, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException throwables) {
        }
        try {
            if (ps != null) {
                ps.close();
            }
        } catch (SQLException throwables) {
        }
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException throwables) {
        }
    }

}
