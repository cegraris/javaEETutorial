import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Locale;

/**
 * @author Jiahao Wu
 * @date 2021/8/16 - 20:46
 */
public class AjAPI {
    public static void main(String[] args) {
        /* String 字符串，使用一对“”表示
        1.String声明为final的，不可被继承
        2.String实现了Serializable接口：表示字符串是支持序列化的（可通过流传输）
        3.String实现了Comparable接口：表示String可以比较大小
        4.String内部定义了final char[] value 用于存储字符串数据，代表了不可变的字符序列
         */
        //定义方式一：字面量定义->指向方法区中字符串常量池
        String s1 = "abc"; //字面量的定义方式（明明是应用类，但是却可以不用new，直接用=）
        //区别于new，字面量定义s1存在方法区中字符串常量池，常量池中是不会存储相同内容的字符串的
        String s2 = "abc"; //，s2此时指向与s1相同类（因为字符串内容相同）
        System.out.println(s1 == s2);//true
        s2 = "Hello"; //在字符串常量池中新建一个value，s2指向新地址
        String s3 = "abc"; //一开始指向s1对应的地址
        s3 += "def"; //在字符串常量池中新建一个value，s3指向新地址
        String s4 = "abc";
        String s5 = s4.replace('a', 'm'); //执行完后，s4="abc",s5="mbc"

        //定义方式二：new定义->指向堆
        String s6 = new String(); //本质上this.value = new char[0]
        // 下面这种方式，s7指向堆内一个地址，这个地址对应的对象中的value再指向字符串常量池中对应value
        String s7 = new String("hello"); //this.value = original.value
        String s8 = new String("hello");
        System.out.println(s7==s8);//false,但他们的values指向字符串常量池中相同地址
//        String s9 = new String(char[] a);//this.value = Arrays.copyOf(value,value.length)
//        String s10 = new String(char[] a,int startIndex,int count)

        //字符串不同拼接方式
        String s11 = "javaEE";
        String s12 = "hadoop";

        String s13 = "javaEEhadoop";
        String s14 = "javaEE" + "hadoop"; //s13与s14相同（指向常量池中相同地址）
        String s15 = s11 + "hadoop"; //有变量参与连接运算，s15在堆空间开辟新空间地址
        String s16 = "javaEE" + s12; //有变量参与连接运算，s16在堆空间开辟新空间地址
        String s17 = s1 + s2; //有变量参与连接运算，s17在堆空间开辟新空间地址

        final String s011 = "javaEE";
        String s015 = s011 + "hadoop";
        System.out.println(s13 == s015); //true, 此处的s011是常量（因为final），不是变量

        String s18 = s15.intern(); //返回value指向的常量池地址
        System.out.println(s13 == s18); //true

        // String的常用方法
        s1 = "HelloWorld";
        System.out.println(s1.length()); //返回字符串长度
        System.out.println(s1.charAt(0)); //返回某索引处字符
        System.out.println(s1.isEmpty()); //字符串是否为空
        System.out.println(s1.toLowerCase()); //字符串小写化（s1本身不变，返回小写）
        System.out.println(s1.toUpperCase()); //字符串大写化
        s3 = "    h e ll o     world    ";
        System.out.println(s3.trim()); //去除首尾空格（s3本身不变）
        System.out.println(s1.equals(s3)); //比较字符串内容是否相等
        System.out.println(s1.equalsIgnoreCase(s3)); //忽视大小写，比较字符串内容是否相等
        System.out.println(s1.concat(s3)); //连接俩字符串
        System.out.println(s1.compareTo(s3)); //比较俩字符串大小，对位相减，负数说明s1更小
        System.out.println(s1.substring(3)); //从某处开始子串
        System.out.println(s1.substring(3,5)); //含3不含5的子串

        System.out.println(s1.endsWith("ld")); //是否以某字符串结尾
        System.out.println(s1.startsWith("He")); //是否以某字符串开始
        System.out.println(s1.startsWith("ll",2)); //从某个索引开始，是否以某字符串开始
        System.out.println(s1.contains("ll")); //判断一个字符串中是否包含某字符串
        System.out.println(s1.indexOf("lo")); //返回某子串第一次出现位置（没找到返回-1）
        System.out.println(s1.indexOf("lo",6)); //从某索引开始，返回某子串第一次出现位置（没找到返回-1）
        System.out.println(s1.lastIndexOf("lo"));//返回某子串最后一次出现位置（没找到返回-1）
        System.out.println(s1.lastIndexOf("lo",5)); // //截止到某索引，返回某子串最后一次出现位置（没找到返回-1）

        System.out.println(s1.replace('o','i')); // 替换所有指定字符
        System.out.println(s1.replace("or","ro")); // 替换所有指定子字符串
        System.out.println(s1.replaceAll("\\d+",",")); //正则表达式替换（全部）
        System.out.println(s1.replaceFirst("\\d+",",")); //正则表达式替换（首个）
        System.out.println(s1.matches("\\d+")); //正则匹配
        System.out.println(s1.split("\\|")); //正则切割成String[]
        System.out.println(s1.split("\\|",30)); //正则切割成String[],最多n个，如超过，剩下全放在最后一个

        // String 与基本数据类型和包装类转换
        Integer.parseInt("123"); //String -> 其他类型
        String.valueOf(123); //其他类型->String
        // String 与char[]转换
        char[] charArrary = s1.toCharArray(); //String->char[]
        String str = new String(charArrary); //char[]->String
        // String 与byte[]转换 (解码和编码要用相同字符集，不指定则用默认编码集)
        byte[] byteArray = s1.getBytes(StandardCharsets.UTF_8); //String->byte[]
        str = new String(byteArray,StandardCharsets.UTF_8); //byte[]->String

        /*
        StringBuffer类(java.lang.StringBuffer)可变的字符序列(线程安全，效率低）
        StringBuilder类(java.lang.StringBuilder)可变的字符序列（线程不安全，效率高）（JDK5.0+)
         */
        StringBuffer sb1 = new StringBuffer("abc");
        sb1.setCharAt(0,'m'); //void方法，直接改sb1本身
        System.out.println(sb1); //直接sb1被改变

        String str0 = new String(); //char[] value = new char[0]
        String str1 = new String("abc"); //char[] value = new char[]{'a','b','c'}

        StringBuffer sb2 = new StringBuffer(); // char[] value = new char[16]
        sb2.append('a'); //value[0]='a'
        sb2.append('b'); //value[1]='b'
        StringBuffer sb3 = new StringBuffer("abc");//char[] value = new char["abc".length()+16]
        System.out.println(sb3.length()); //3
        //若容量不够会自动扩容,new新数组（扩容），然后copy旧的到新的，默认扩容一倍+2

        //常用方法：
        StringBuffer sb01 = new StringBuffer("abc");
        sb01.append(1);
        sb01.append('1');
        sb01.delete(2,4);
        sb01.replace(2,4,"Hello");
        sb01.insert(2,false);
        sb01.reverse();
        sb01.indexOf("he");
        String str00 = sb01.substring(1,3);
        int l = sb01.length();
        char c = sb01.charAt(3);
        sb01.setCharAt(3,'b');


        /* 日期与时间API
        1.java.lang.System -> public static long currentTimeMillis() //(JDK8-)
        2.java.util.Date（父类）
            java.sql.Date（子类） （对应数据库中Date对象）
        3.java.text.SimpleDateFormat
         */
        Date date1 = new Date(); //对应当前时间
        System.out.println(date1.toString()); //显示当前的年月日时分秒
        System.out.println(date1.getTime()); //返回毫秒时间戳
        Date date2 = new Date(1550306204104L); //用毫秒数生成一个Date对象

        java.sql.Date date3 = new java.sql.Date(1550306204104L);
        System.out.println(date3); //显示年月日
        //java.util.Date -> java.sql.Date
        java.sql.Date date5 = new java.sql.Date(date3.getTime());
    }

}
