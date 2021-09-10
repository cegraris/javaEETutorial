package com.database;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.apache.commons.dbutils.DbUtils;

import javax.sql.DataSource;
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

    /**
     * 使用Druid数据库连接池技术
     */
    private static DataSource source1;
    static{
        try {
            Properties pros = new Properties();

            InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("druid.properties");

            pros.load(is);

            source1 = DruidDataSourceFactory.createDataSource(pros);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static Connection getConnection3() throws SQLException{

        Connection conn = source1.getConnection();
        return conn;
    }

    /**
     *
     * @Description 使用dbutils.jar中提供的DbUtils工具类，实现资源的关闭
     * @author shkstart
     * @date 下午4:53:09
     * @param conn
     * @param ps
     * @param rs
     */
    public static void closeResource1(Connection conn,Statement ps,ResultSet rs){
//		try {
//			DbUtils.close(conn);
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		try {
//			DbUtils.close(ps);
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		try {
//			DbUtils.close(rs);
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}

        DbUtils.closeQuietly(conn);
        DbUtils.closeQuietly(ps);
        DbUtils.closeQuietly(rs);
    }

}
