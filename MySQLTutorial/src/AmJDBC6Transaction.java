import com.database.JDBCUtils;
import org.junit.Test;

import java.lang.reflect.Field;
import java.sql.*;

/**
 * @author Jiahao Wu
 * @date 2021/9/10 - 0:11
 */
public class AmJDBC6Transaction {
    /*
    事务：多个操作要么都被提交（commit），要么放弃所有修改并回滚（rollback）到初始状态
    数据一旦提交，就不可回滚
    哪些操作会导致数据的自动提交？
        1.DDL操作
        2.DML操作，默认情况下一旦执行就会自动提交，但可以通过set autocommit = false取消DML操作的自动提交
        3.默认关闭连接时没提交的操作都会自动提交
     */

    public int update(Connection conn,String sql, Object...args){
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql);
            for(int i =0;i<args.length;i++){
                ps.setObject(i+1,args[i]);
            }
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(null,ps); //不关闭网络连接，以防止自动提交
        }
        return 0;
    }

    @Test
    public void testUpdateWithTx(){
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnection();

            System.out.println(conn.getAutoCommit());
            conn.setAutoCommit(false); //取消数据的自动提交

            String sql1 = "update user_table set balance = balance - 100";
            update(conn,sql1,"AA");

            System.out.println(10/0); // 模拟网络异常

            String sql2 = "update user_table set balance = balance + 100";
            update(conn,sql2,"BB");

            conn.commit();
            System.out.println("Transfer successfully");
        } catch (Exception e) {
            e.printStackTrace();
            try {
                conn.rollback();//回滚数据
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } finally {
            try {
                conn.setAutoCommit(true); //恢复该连接的自动提交，不影响再次使用
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            JDBCUtils.closeResource(conn,null);
        }
    }

    /**
     * 使用PreparedStatement实现对不同表的通用查询操作（单条数据），考虑事务
     * @param connection
     * @param clazz
     * @param sql
     * @param args
     * @param <T>
     * @return
     */
    public <T> T  getInstance(Connection connection, Class<T> clazz,String sql, Object... args) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                preparedStatement.setObject(i + 1, args[i]);
            }
            resultSet = preparedStatement.executeQuery();
            ResultSetMetaData metaData = resultSet.getMetaData(); //获取结果集的元数据
            int columnCount = metaData.getColumnCount(); //获取列数

            if (resultSet.next()) {
                T t = clazz.newInstance();
                for (int i = 0; i < columnCount; i++) {
                    //获取每个列的值
                    Object columValue = resultSet.getObject(i + 1);
                    //获取每个列的列名
                    String columnLabel = metaData.getColumnLabel(i + 1);
                    //给customer对象指定的某个属性，赋值为value
                    Field field = clazz.getDeclaredField(columnLabel);
                    field.setAccessible(true);
                    field.set(t, columValue);
                }
                return t;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(null, preparedStatement, resultSet);
        }
        return null;
    }

    @Test
    public void testTransactionSelect() throws Exception {
        Connection conn = JDBCUtils.getConnection();
        System.out.println(conn.getTransactionIsolation()); //查询隔离级别
        conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED); //设置当前连接的隔离级别
        conn.setAutoCommit(false);

        String sql = "select user,password,balance from user_table where user = ?";

//        User user = getInstance(conn,User.class,sql,"CC");
//        System.out.println(user);
    }
}
