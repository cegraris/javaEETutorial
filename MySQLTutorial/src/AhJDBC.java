import com.bean.Customer;
import com.database.JDBCUtils;
import com.mysql.cj.jdbc.JdbcConnection;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Properties;

/**
 * @author Jiahao Wu
 * @date 2021/9/6 - 14:41
 */
public class AhJDBC {
    @Test
    public void testConnection(){
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            InputStream resourceAsStream = AhJDBC.class.getClassLoader().getResourceAsStream("jdbc.properties");
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
            conn = DriverManager.getConnection(url, user, password);

            //预编译sql语句，返回PreparedStatement实例，实现对数据表的增删改查操作
            String sql = "insert into customers(name,email,birth) values(?,?,?)"; //?是占位符
            ps = conn.prepareStatement(sql);
            //填充占位符
            ps.setString(1,"哪吒");//此处索引从1开始
            ps.setString(2,"nezha@gmail.com");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date date = sdf.parse("1000-01-01");
            ps.setDate(3,new Date(date.getTime()));
            //执行操作
            ps.execute();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
            //资源关闭
            try {
                if(ps != null){
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

    //修改customer表的一条记录
    @Test
    public void testUpdate() throws Exception{
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            //1.获取数据库的连接
            conn = JDBCUtils.getConnection();
            //2.预编译sql语句，返回PreparedStatement的实例
            String sql = "update customers set name = ? where id = ?";
            ps = conn.prepareStatement(sql);
            //3.填充占位符
            ps.setObject(1,"莫扎特");
            ps.setObject(2,18);
            //4.执行
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //5.资源的关闭
            JDBCUtils.closeResource(conn,ps);
        }
    }

    //通用的增删改操作
    public void update(String sql,Object ...args){ //sql中的占位符和可变形参的长度是一致的
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            //1.获取数据库的连接
            conn = JDBCUtils.getConnection();
            //2.预编译sql语句，返回PreparedStatement的实例
            ps = conn.prepareStatement(sql);
            //3.填充占位符
            for(int i = 0;i<args.length;i++){
                ps.setObject(i+1,args[i]); //注意参数声明中不同的index
            }
            //4.执行
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //5.资源的关闭
            JDBCUtils.closeResource(conn,ps);
        }
    }

    @Test
    public void testCommonUpdate(){
        String sql = "delete from customers where id = ?";
        update(sql,3);
    }

}