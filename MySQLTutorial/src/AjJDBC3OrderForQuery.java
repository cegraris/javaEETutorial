import com.bean.Order;
import com.database.JDBCUtils;
import org.junit.Test;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

/**
 * @author Jiahao Wu
 * @date 2021/9/9 - 12:51
 */
public class AjJDBC3OrderForQuery {

    /*
    当表的字段名与类的属性名不相同的情况：
    1.必须在声明SQL时，使用类的属性名来命名字段的别名
    2.使用ResultSetMetaData时，需要使用getColumnLabel()来替换getColumnName()来获取列的别名
    说明：如果sql中没有给字段取别名，getColumnLabel()取得的就是列名
     */
    @Test
    public void testOrderForQuery() {
        String sql = "select order_id orderId,order_name orderName,order_date orderDate from `order` where order_id = ?";
        Order order = orderForQuery(sql, 1);
        System.out.println(order);
    }

    public Order orderForQuery(String sql, Object... args) {
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
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            if (resultSet.next()) {
                Order order = new Order();
                for (int i = 0; i < columnCount; i++) {
                    Object columnValue = resultSet.getObject(i + 1);
                    //获取列的列名：getColumnName() <-不推荐使用
                    //获取列的别名：getColumnLabel()
                    String columnLabel = metaData.getColumnLabel(i + 1);
                    Field field = Order.class.getDeclaredField(columnLabel);
                    field.setAccessible(true);
                    field.set(order, columnValue);
                }

                return order;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(connection, preparedStatement, resultSet);
        }
        return null;
    }
}
