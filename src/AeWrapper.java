import com.sun.org.apache.xpath.internal.operations.Bool;

/**
 * @author Jiahao Wu
 * @date 2021/8/15 - 11:28
 */
public class AeWrapper {
    /*
    包装类Wrapper的使用
     */
    // Number的子类
    // byte -> Byte
    // short -> Short
    // int -> Integer
    // long -> Long
    // float -> Float
    // double -> Double

    // 单独类
    // boolean -> Boolean
    // char -> Character
    public static void main(String[] args) {
        // 基本数据类←→包装类
        //→
        int num1 = 10;
        Integer int1 = new Integer(num1);
        Integer int2 = new Integer("123");
        Integer int3 = num1; //自动装箱 （JDK5.0+）
        Boolean b1 = new Boolean(true);
        Boolean b2 = new Boolean("true"); //可忽略大小写的ture,其余一律视为false
        Boolean b3 = new Boolean("true123");//false
        //←
        num1 = int1.intValue();
        num1 = int2; //自动拆箱 （JDK5.0+）
        boolean bo1 = b1.booleanValue();
        //基本数据类型和包装类←→String
        //→
        String str1 = num1 + ""; //方式一
        String str2 = String.valueOf(num1); //方式二
        //←
        int1 = Integer.parseInt(str1);
        bo1 = Boolean.parseBoolean("true");
    }

}
