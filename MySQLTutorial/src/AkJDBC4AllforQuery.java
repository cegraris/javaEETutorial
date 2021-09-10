import com.bean.Customer;
import com.database.JDBCUtils;
import org.junit.Test;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

/**
 * 使用PreparedStatement实现对于不同表的通用的查询操作(返回一条记录)
 *
 * @author Jiahao Wu
 * @date 2021/9/9 - 14:33
 */
public class AkJDBC4AllforQuery {

    @Test
    public void testGetInstance(){
        String sql = "select id,name,email from customers where id = ?";
        Customer customer = getInstance(Customer.class,sql,12);
        System.out.println(customer);
    }

    public <T> T  getInstance(Class<T> clazz,String sql, Object... args) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = JDBCUtils.getConnection();
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
            JDBCUtils.closeResource(connection, preparedStatement, resultSet);
        }
        return null;
    }

    /**
     * 使用PreparedStatement实现对不同表的通用查询操作（多条数据）
     * @param clazz
     * @param sql
     * @param args
     * @param <T>
     * @return
     */
    public <T> List<T> getForList(Class<T> clazz, String sql, Object... args) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = JDBCUtils.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                preparedStatement.setObject(i + 1, args[i]);
            }
            resultSet = preparedStatement.executeQuery();
            ResultSetMetaData metaData = resultSet.getMetaData(); //获取结果集的元数据
            int columnCount = metaData.getColumnCount(); //获取列数

            ArrayList<T> list = new ArrayList<>();
            while (resultSet.next()) {
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
                list.add(t);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(connection, preparedStatement, resultSet);
        }
        return null;
    }

    @Test
    public void testGetForList(){
        String sql = "select id,name,email from customers where id < ?";
        List<Customer> list = getForList(Customer.class,sql,12);
        list.forEach(System.out::println);
    }
}
