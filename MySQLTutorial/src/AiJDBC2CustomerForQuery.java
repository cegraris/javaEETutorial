import com.bean.Customer;
import com.database.JDBCUtils;
import org.junit.Test;

import java.lang.reflect.Field;
import java.sql.*;

/**
 * @author Jiahao Wu
 * @date 2021/9/9 - 2:00
 */
public class AiJDBC2CustomerForQuery {

    @Test
    public void testQuery1() {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        try {
            conn = JDBCUtils.getConnection();
            String sql = "select id, name, email, birth from customers where id = ?";
            ps = conn.prepareStatement(sql);
            ps.setObject(1, 1);
            //执行，并返回结果集
            resultSet = ps.executeQuery();
            //处理结果集
            if (resultSet.next()) { //判断结果集的下一条是否有数据，如果有，返回true并指针下移，否则返回false，指针不动
                //获取当前这条数据的各个字段值
                int id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                String email = resultSet.getString(3);
                Date birth = resultSet.getDate(4);

                Customer customer = new Customer(id, name, email, birth);
                System.out.println(customer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭资源
            JDBCUtils.closeResource(conn, ps, resultSet);
        }
    }

    /**
     * 针对customer表的通用查询
     */
    public Customer queryForCustomers(String sql, Object... args){
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

            if(resultSet.next()){
                Customer customer = new Customer();
                for(int i = 0;i<columnCount;i++){
                    //获取每个列的值
                    Object columValue = resultSet.getObject(i+1);
                    //获取每个列的列名
                    String columnName = metaData.getColumnName(i + 1);
                    //给customer对象指定的某个属性，赋值为value
                    Field field = Customer.class.getDeclaredField(columnName);
                    field.setAccessible(true);
                    field.set(customer,columValue);
                }
                return customer;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(connection,preparedStatement,resultSet);
        }
        return null;
    }

    @Test
    public void testQueryForCustomers(){
        String sql = "select id,name,birth,email from customers where id = ?";
        Customer customer = queryForCustomers(sql,13);
        System.out.println(customer);
    }


}
