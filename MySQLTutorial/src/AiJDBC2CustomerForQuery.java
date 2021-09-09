import com.bean.Customer;
import com.database.JDBCUtils;
import org.junit.Test;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Jiahao Wu
 * @date 2021/9/9 - 2:00
 */
public class AiJDBC2CustomerForQuery {
    @Test
    public void testQuery1(){
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        try {
            conn = JDBCUtils.getConnection();
            String sql = "select id, name, email, birth from customers where id = ?";
            ps = conn.prepareStatement(sql);
            ps.setObject(1,1);
            //执行，并返回结果集
            resultSet = ps.executeQuery();
            //处理结果集
            if(resultSet.next()){ //判断结果集的下一条是否有数据，如果有，返回true并指针下移，否则返回false，指针不动
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
            JDBCUtils.closeResource(conn,ps,resultSet);
        }
    }
}
