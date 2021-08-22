import java.util.*;

/**
 * @author Jiahao Wu
 * @date 2021/8/22 - 21:46
 */
public class AnGeneric {
    public static void main(String[] args) {
        //在集合ArrayList中使用泛型
        ArrayList<Integer> list = new ArrayList<Integer>(); //不指明时默认Object类
        //泛型类不能用基本数据类型，可以用包装类替换
        list.add(78);
        list.add(100);
        list.add(87);
        //遍历一
        for (Integer score : list) {
            int stuScore = score;
            System.out.println(stuScore);
        }
        //遍历二
        Iterator<Integer> iterator = list.iterator();
        while (iterator.hasNext()) {
            int stuScore = iterator.next();
            System.out.println(stuScore);
        }

        //HashMap中使用泛型
        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("Tom", 78);
        map.put("Jerry", 100);
        map.put("Jack", 87);
        Set<Map.Entry<String, Integer>> entry = map.entrySet();
        Iterator<Map.Entry<String, Integer>> iterator1 = entry.iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Integer> next = iterator1.next();
            String key = next.getKey();
            Integer value = next.getValue();
            System.out.println(key + "-->" + value);
        }

        //泛型不同的引用不能相互赋值
        ArrayList<String> list1 = null;
        ArrayList<Integer> list2 = null;

        //泛型在继承方面的体现
        Object obj = null;
        String str = null;
        obj = str; //二者是子父类关系

        Object[] arr1 = null;
        String[] arr2 = null;
        arr1 = arr2; //二者是子父类关系

        List<Object> list3 = null;
        List<String> list4 = null;
//        list3 = list4; <- 不行！二者是并列关系，不是子父类关系,不然会在list4中混入其他类的实例
        //A是B父类，G<A>和G<B>不具备父子关系，二者共同的父类是G<?>
        //A是B父类,A<G>,B<G>是子父类（接口）关系

        //通配符的使用
        List<Object> list5 = null;
        List<Object> list6 = null;
        List<?> list7 = null; //相当于是该系列泛型类的通用父类
         list7 = list5; //此后就不能向里添加数据（但null可以），防止流入异类
         list7 = list6;
         Object o = list7.get(0); //允许读取，读取的数据类型为Object
        //有限制条件的通配符
//        List<? extends Person> list1 = null; （<=Person的类）
//        List<? super Person> list2 = null; （>=Person的类）
//        List<Student> list3 = null;
//        List<Person> list4 = null;
//        List<Object> list5 = null;
//        list1 = list3; <- 行
//        list1 = list4; <- 行
//        list1 = list5; <- 不行
    }
}

//自定义类中使用泛型
class Order<T> { //可以有多个泛型类参数
    String orderName;
    int orderID;
    T orderT; //泛型T

    public Order() {
    }

    public Order(String orderName, int orderId, T orderT) {
        this.orderID = orderId;
        this.orderT = orderT;
        this.orderName = orderName;
    }

    public T getOrderT() {
        return orderT;
    }

    public void setOrderT(T orderT) {
        this.orderT = orderT;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderName='" + orderName + '\'' +
                ", orderID=" + orderID +
                ", orderT=" + orderT +
                '}';
    }

    public void order() {
//        T[] arr  = new T[10]; <- 不能new一个抽象类
        T[] arr = (T[]) new Object[10]; //这么写可以编译通过
    }

//    静态部分不能使用泛型（异常类也不可以用，防止使用时传入非异常类）
//    public static void show(){
//        System.out.println(orderT);
//    }

    //泛型方法：在方法中出现了泛型结构，泛型参数与类类的泛型参数没有任何关系
    //调用时放入什么类型的数组就返回什么类型的ArrayList
    //也可以是static方法，泛型参数是在调用方法时确定的
    public <E> List<E> copyFromArrayToList(E[] arr) {
        ArrayList<E> list = new ArrayList<>();
        for (E e : arr) {
            list.add(e);
        }
        return list;
    }
}

//子类不保留父类的泛型
//父类没有类型（父类泛型采用默认Object）
//父类有具体类
class SubOrder extends Order<Integer> { //生成SubOrder实例时不需要声明泛型选择
}

//子类保留父类的泛型
//全部保留
//部分保留，部分声明具体类
class SubOrder2<T> extends Order<T> { //生成SubOrder1实例时需要声明泛型选择
}
//除此之外，子类也可以自己定义新的额外泛型

