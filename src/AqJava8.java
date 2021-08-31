import org.junit.Test;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author Jiahao Wu
 * @date 2021/8/29 - 23:25
 */
public class AqJava8 {
    /* Lambda （本质为函数式接口的实例）
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

         /* Lambda写法
         -> 左边：lambda形参列表的参数类型可以省略，如果lambda形参列表只有一个参数括号也可以省略
         -> 右边：lambda体应该使用一对{}包裹，但如果只有一条执行语句，可省略（大括号与return关键字）（要省一定要一起省）
          */


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


        //方法引用：当传递给lambda体的操作已经有实现的方法时，可以使用方法引用（本质上就是lambda表达式）
        //要求接口中的抽象方法的形参列表和返回值类型与引用的方法相同（情况三除外，情况三将引用方法中的调用者作为实现方法中的第一个参数，其余为剩余参数）
        //格式：   类或对象::方法名
        //情况一： 对象::非静态方法
        //情况二： 类::静态方法
        //情况三： 类::非静态方法
        Comparator<Integer> com3 = Integer :: compare;
        System.out.println(com3.compare(32,21));

        //构造器引用：和方法应用类似，函数式接口的抽象方法的参数列表和构造器的参数列表一致，抽象方法的返回值类型即为构造器所属类的类型
        //Supplier<Employee> sup = Employee :: new; //Supplier中的T get() 对应Employee中的空参构造器Employee()

        //数组引用：可以把数组看成一个类，则其使用起来与构造器引用相似
    }

    @Test
    public void testStreamAPI(){
        /* java.util.stream
        Stream关注的是数据的运算
        1.自己不会存储元素
        2.不会改变源对象，相反，会返回一个持有结果的新Stream
        3.操作是延迟执行的，这意味着会等到需要结果时才执行
        Stream执行流程
        1.Stream实例化
        2.一系列的中间操作（过滤，映射...）对数据源的数据进行处理
        3.终止操作，执行中间操作链，产生结果，之后该Strema不会再被使用
        */

        //创建Stream方式一：通过集合
        List<Employee> employees = EmployeeData.getEmployees();
        //default Stream<E> stream() 返回一个顺序流
        Stream<Employee> stream = employees.stream(); //依据源数据顺序
        //default Stream<E> parallelStream() 返回一个并行流
        Stream<Employee> parallelStream = employees.parallelStream(); //并行读取，可能数据顺序打乱

        //创建Stream方式二：通过数组
        //调用Arrays类的static <T> Stream<T> stream(T[] array)返回一个流
        int[] arr = new int[]{1,2,3,4,5,6}
        IntStream stream1 = (IntStream) Arrays.stream(arr);
        //对于一般类
        Employee[] arr2 = new Employee[5];
        Stream<Employee> stream2 = Arrays.stream(arr2);

        //创建Stream方式三：通过Stream的of
        Stream<Integer> stream3 = Stream.of(1,2,3,4,5,6);

        //创建Stream方式四：创建无限流
        //public static <T> Stream<T> iterate(final T seed, final UnaryOperator<T> f)
        Stream.iterate(0,t -> t + 2).limit(10).forEach(System.out::println);
        //public static <T> Stream<T> generate(Supplier<T> s)
        Stream.generate(Math::random).limit(10).forEach(System.out::println);

        //Stream中间操作
        //1.筛选与切片
        //filter()：查询员工表中薪资大于7000的员工信息
        stream.filter(e -> e.getSalary()>7000).forEach(System.out::println);
        //limit(n): 截断流，时其元素不超过给定数量
        employees.stream().limit(3).forEach(System.out::println);
        //skip(n): 跳过前n个元素，若不足n个则返回一个空流
        employees.stream().skip(3).forEach(System.out::println);
        //distinct(): 筛选，通过流所生成元素的hashCode()和equals（）去除重复元素
        employees.stream().distinct().forEach(System.out::println);

        //2.映射
        //map(Function f)
        //大写
        List<String> list = Arrays.asList("aa","bb","cc","dd");
        list.stream().map(str -> str.toUpperCase()).forEach(System.out::println);
        //获取员工姓名长度大于3的员工的姓名
        Stream<String> namesStream = employees.stream().map(Employee::getName);
        namesStream.filter(name->name.length()>3).forEach(System.out::println);
        //flatMap(Function f) 若f返回的是Stream，则会自动将所有返回的各个Stream全部拆开，并成一个Stream

        //3.排序
        //sorted()产生一个新流，其中按自然顺序排序
        //sorted(Comparator com) 产生一个新流，用比较器排序


        //终止操作：
        //1.匹配与查找
        //allMatch(Predicate p)检查是否匹配所有元素
        //anyMatch(Predicate p)检查是否至少匹配一个元素
        //noneMatch(Predicate p)检查是否没有匹配所有元素
        //findFirst()返回第一个元素
        //findAny()返回当前流中的任意元素
        //count()返回流重元素总数
        //max(Comparator c)返回流中最大值
        //min(Comparator c)返回流中最小值
        //forEach(Consumer c)内部迭代（使用Collection接口需要用户去做迭代，称为外部迭代。相反，StreamAPI使用内部迭代（它帮你把迭代做了）
        //2.规约
        //reduce(T iden, BinaryOperator b) 可以将流中元素反复结合起来，得到一个值，返回T
        stream3.reduce(0,Integer::sum); //（（（0+1）+2）+3）...
        //reduce(BinaryOperator b)可以将流中元素反复结合起来，得到一个值，返回Optional<T>
        //3.收集
        //将流转换为其他形式，接收一个Collector接口的实现，用于给Stream中元素做汇总的方法（如收集到List，Set，Map中）
        //collect(Collector c) //可以通过Collectors实用类的静态方法(↓）去获得Collector实例
        //Collectors.toList()/Collectors.toSet()/Collectors.toCollection()

        //Optional类：为了在程序中避免出现空指针异常而创建的
        Employee employee = new Employee(0,"Peter");
        //Optional.of(T t)创建一个Optional实例，t必须非空
        Optional<Employee> employee1 = Optional.of(employee);
        //Optional.empty() 创建一个空的Optional实例
        //Optional.ofNullable(T t) t可以为null
        Optional<Employee> employee2 = Optional.ofNullable(null);
        employee2.orElse(new Employee(0,"Peter")); //有存在非空实例就返回实例，否则返回一个新建的Employee

    }
}

//函数式接口：只有一个抽象方法的接口
@FunctionalInterface
interface MyInterface{
    void method1();
}

/* java.util.unction 内置四大核心函数式接口
Consumer<T> : void accept(T t)
Supplier<T> : T get()
Function<T,R> : R apply(T t)
Predicate<T> : boolean test(T t)
 */

class Employee{
    private int id;
    private String name;
    private int Salary = 2000;

    public Employee(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getSalary() {
        return Salary;
    }
}
class EmployeeData{
    public static List<Employee> getEmployees(){
        List<Employee> employees = new ArrayList<>();
        return employees;
    }
}