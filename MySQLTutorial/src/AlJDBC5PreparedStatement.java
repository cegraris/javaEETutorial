import com.bean.Customer;
import com.database.JDBCUtils;
import org.junit.Test;

import java.io.*;
import java.sql.*;

/**
 * @author Jiahao Wu
 * @date 2021/9/9 - 19:59
 */

public class AlJDBC5PreparedStatement {

    /*
    PreparedStatement 替换 Statement，因为有如下好处：
    1.解决了Statement的拼串问题
    2.解决了SQL注入问题
    3.可以操作Blob数据（可以方便使用数据库操作二进制文件（图片等））
        MySQL中：
        Blob类型      最大字节
        TinyBlob     255bytes
        Blob         65K
        MediumBlob   16M
        LongBlob     4G
        如需传大文件还需设置mysql中最大包大小：@@global.max_allowed_packet(8.0默认是4M)
    4.可以实现更高效的批量操作（SQL语句仅需校验一次）
     */

    //向数据表customers中插入Blob类型的字段
    @Test
    public void testInsert() throws Exception{
        Connection conn = JDBCUtils.getConnection();
        String sql = "insert into customers(name,email,birth,photo) values (?,?,?,?) ";

        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setObject(1,"猫");
        ps.setObject(2,"miaomiao@gmail.com");
        ps.setObject(3,"1992-09-08");
        FileInputStream is = new FileInputStream(new File("src\\cat.jpg"));
        ps.setBlob(4,is);

        ps.execute();

        JDBCUtils.closeResource(conn,ps);
    }

    //查询数据表Customers中的Blob类型的字段
    @Test
    public void testQuery(){
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        InputStream binaryStream = null;
        FileOutputStream fos = null;

        try {
            conn = JDBCUtils.getConnection();
            String sql = "select id,name,email,birth,photo from customers where id = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1,19);

            rs = ps.executeQuery();
            if(rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                Date birth = rs.getDate("birth");

                Customer cust = new Customer(id,name,email,birth);
                System.out.println(cust);

                Blob photo = rs.getBlob("photo");
                binaryStream = photo.getBinaryStream();
                fos = new FileOutputStream("cat_download.jpg");
                byte[] buffer = new byte[1024];
                int len;
                while((len = binaryStream.read(buffer))!=-1){
                    fos.write(buffer,0,len);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                binaryStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            JDBCUtils.closeResource(conn,ps,rs);
        }
    }

    /*使用PreparedStatement实现批量数据的操作
    update,delete本身就具有批量操作的效果
    此时的批量操作，主要是指的是批量插入。使用PreparedStatement如何实现更高效的批量插入？
     */
    @Test
    public void testInsert1(){
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = JDBCUtils.getConnection();
            String sql = "insert into goods (name) values (?)";
            ps = conn.prepareStatement(sql);
            for(int i =1 ;i<=20000;i++){
                ps.setObject(1,"name_"+i);
                ps.execute();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn,ps);
        }
    }
    //更好的批量操作方法
    //mysql服务器是默认关闭批处理的，需要通过?rewriteBatchedStatements=true加在url后
    @Test
    public void testInsert2(){
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = JDBCUtils.getConnection();

            //设置不允许自动提交数据
            conn.setAutoCommit(false);

            String sql = "insert into goods (name) values (?)";
            ps = conn.prepareStatement(sql);
            for(int i =1 ;i<=20000;i++){
                ps.setObject(1,"name_"+i);
                //1.”攒“sql
                ps.addBatch();
                if(i%500==0){
                    //2.执行batch
                    ps.executeBatch();
                    //3.清空batch
                    ps.clearBatch();
                }
            }
            //提交数据
            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn,ps);
        }
    }
}
