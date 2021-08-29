import org.junit.Test;

import java.util.Comparator;
import java.util.function.Consumer;

/**
 * @author Jiahao Wu
 * @date 2021/8/29 - 23:25
 */
public class AqJava8 {
    /* Lambda （本质为接口的实例）
    1.举例 (o1,o2) -> Integer.compare(o1,o2);
    2.格式 -> : lambda操作符或箭头操作符
          ->左边 : lambda形参列表（其实就是接口中的抽象方法的形参列表）
          ->右边 : lambda体（其实就是重写的抽象方法的方法体）
     */
    @Test
    public void testLambda(){
        //一般匿名类写法
        Runnable r1 = new Runnable() {
             @Override
             public void run() {
                 System.out.println("Hello!");
             }
         };
         r1.run();

         //Lambda写法

        //例三 (方法引用)
        Comparator<Integer> com3 = Integer :: compare;
        System.out.println(com3.compare(32,21));

        //情况一：无参，无返回值
        Runnable r2 = () -> {
            System.out.println("Ola!");
        };
        r2.run();
        //情况二：需要一个参数，但是没有返回值
        Consumer<String> con = (String s) -> {
            System.out.println(s);
        };
        con.accept("Hello");
        //情况三：数据类型可以省略，因为可由编译器推断得出，称为”类型推断“
        Consumer<String> con2 = (s) -> {
            System.out.println(s);
        };
        //情况四：Lambda若只需要一个参数时，参数的小括号可以省略
        Consumer<String> con3 = s -> {
            System.out.println(s);
        };
        //情况五：Lambda需要两个或以上的参数，多条执行语句，并且可以有返回值
        Comparator<Integer> com2 = (o1,o2) -> {
            System.out.println(o1);
            System.out.println(o2);
            return  Integer.compare(o1,o2);
        };
        System.out.println(com2.compare(32,21));
        //情况六：当Lambda体只有一条语句时，return与大括号若有，都可以省略
        Comparator<Integer> com4 =  (o1,o2) -> o1.compareTo(o2);
    }
}
