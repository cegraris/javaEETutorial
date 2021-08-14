/**
 * @author Jiahao Wu
 * @date 2021/8/14 - 1:17
 */

import java.util.Scanner;

public class AbBasic { // 类名大驼峰 XxxYyyZzz

    public static void main(String[] args) {
        int myCounter; // 变量与方法名小驼峰 xxxYyyZzz
        double MY_PI = 3.1415926; // 常量名全大写 XXX_YYY_ZZZ
        myCounter = 6;

        /*
        ==========变量类型==========
        1.方法体外：成员变量
            实例变量（不以static修饰）
            类变量（以static修饰）
        2.方法体内：局部变量
            形参（方法、构造器中定义的变量）
            方法局部变量（在方法内定义）（需显式初始化）
            代码块局部变量（在代码块内定义）（需显式初始化）
         */

        /*
        ==========数据类型（基本数据类型）==========
        byte,short,int,long,float,double,char,boolean
         */
        byte b = 56; // -128~127 (1Bytes)
        short s = 10086; // -2^15~2^15-1 (2Bytes)
        int i = 1001010086; // -2^31~2^31-1 (4Bytes) 通常使用
        long l = 11001010086L; // -2^63~2^63-1 (8Bytes) 必须以l或L结尾！
        float f = 3.1415F; // -3.403E38~3.403E38 (4Bytes) 必须以f或F结尾！
        double d = 3.1415926; //-1.798E308~1.798E308 (8Bytes) 通常使用
        char c = 'a'; // (2Bytes) 1.单个字符'a' 2.转义符'\n' 3.unicode'\u0123'
        boolean bo = true; // (1bit)
        i = b + s; // 自动类型提升 byte/char/short->int->long->float->double
        i = (int) l; // 强制类型转换
        /*
        ==========数据类型（引用数据类型）==========
        class(String),Interface,Array
         */

        String st = "Hello" + 10; // String可与八种数据类型做连接运算”+“，结果仍是String

        //*** Array ***
        int[] arr = new int[]{1001, 1002, 1003};
        int[] arr2 = {1001,1002,1003};
        String[] sArr = new String[5]; //默认值 整型:0,浮点型:0.0,char:0或'\u0000'(看上去像空格),布尔:false,引用:null
        sArr[0] = "Hello";
        sArr[1] = "World";
        sArr[2] = "!";
        for (int j = 0; j < sArr.length; j++) {
            System.out.println(sArr[j]);
        }
        int[][] twoDimensionArr = new int[][]{{1,2,3},{4,5},{6,7,8}};
        String[][] twoDimensionArr2 = new String[3][];
        String[][] twoDimensionArr3 = new String[3][2];
        for(int p=0;p<twoDimensionArr.length;p++){
            for(int q=0;q<twoDimensionArr[p].length;q++){
                System.out.println(twoDimensionArr[p][q]);
            }
        }
        //常见Arrays工具类: java.util.Arrays
        // boolean equals(int[] a,int[] b)
        // String to String(int[] a)
        // void fill(int[] a, int val)
        // void sort(int[] a)
        // int binarySearch(int[] a,int key)


        /*
        ==========运算符==========
        1.算术运算符：+,1,*,/,%,++,--
            注意：%的运算结果正负号与被模数一致
            注意： a++,相当于a=a+1,返回a（未加1）
                  ++a,相当于a=a+1,返回a（加1）
            注意： s++  (s为short）是可以的，他不会改变变量本身的数据类型（s=s+1不行）
        2.赋值运算符： =,+=,-=,*=,/=,%=
            注意： s += 2  (s为short）是可以的，他不会改变变量本身的数据类型（s=s+2不行）
        3.比较运算符（关系运算符）： ==,!=,<,>,<=,>=,instanceof
        4.逻辑运算符: &,&&,|,||,!,^(异或，不同为ture)
        5.位运算符：<<(补0),>>(补原来最高位),>>>(补0),&,|,^,~(取反)
        6.三元运算符: (表达式)?表达式1:表达式2;
        */

        /*
        ==========流程控制==========
        1.顺序结构
        2.分支结构：if...else,switch-case
        3.循环结构: for,while,do-while
        */
        //+++ if-else +++
        if (true == false) {
            System.out.println("No way!");
        } else if (true == true) {
            System.out.println("Always!");
        } else {
            System.out.println("No way!");
        }

        //+++ switch-case +++ byte,short,char,int,枚举类(JDK 5.0+),String(JDK 7.0+)
        int day = 3;
        switch (day) {
            case 6:
                System.out.println("Saturday");
                break;
            case 7:
                System.out.println("Sunday");
                break;
            default:
                System.out.println("Workday");
        }

        //+++ for +++
        label:
        for (int j = 1; j <= 10; j++) {
            if (j == 3) {
                continue label; // 跳过这次循环(label可在多循环体内指定循环体)
            }
            System.out.println(j);
            if (j == 6) {
                break label; // 跳出循环(label可在多循环体内指定循环体)
            }
        }

        //+++ while +++
        while (day == 3) {
            System.out.println(day + "");
            day++;
        }

        //+++ do-while +++
        do {
            System.out.println(day + "");
        } while (day == 5);

        /*
        ==========输入==========
        java.util.Scanner
        */
        Scanner scan = new Scanner(System.in);
        int num = scan.nextInt();
        System.out.println(num);

    }
}
