/**
 * @author Jiahao Wu
 * @date 2021/8/14 - 19:53
 */
public class AcClass {
    public static void main(String[] args) {
        Person p1 = new Person();
        new Person().talk("haha", "hello"); //匿名对象
        Person p2 = new Student(); //对象的多态：父类的引用指向子类的对象，向上转型
        p2.sleep(); // 当调用子父类同名同参数的方法时，实际执行的是子类重写父类的方法---虚拟方法调用
        // 不能调用子类中新写的方法
        // 对象的多态性只适用于方法，不适于属性。即：当调用子父类同名属性时，实际调用的是父类中的属性值。不能调用子类中新属性
        if (p2 instanceof Student) { //避免向下转型失败，先用instanceof判断是否真的是该类或其子类的实例
            Student s2 = (Student) p2; //向下转型，有泪第项的多态性后，内存中实际上是加载了这类特有的属性和方法的，通过向下转型可以调用。
        }

        //实例化非静态内部类
        Person p = new Person();
        Person.BB b = p.new BB();
    }
}

class Person {
    /* 权限：修饰类（仅public或default）和类中的属性、方法、构造器、内部类
    private   -> 内部类
    （default）-> 内部类，同一个包
    protected -> 内部类，同一个包，不同包的子类
    public    -> 内部类，同一个包，不同包的子类，同一个工程
     */

    //属性
    /* 赋值顺序：
    默认初始化->显式初始化/代码块初始化（先后取决于代码顺序）->构造器中赋值->通过对象.方法或对象.属性赋值
     */
    private String name;
    private int age = 1;
    private boolean isMale;

    //构造器: 默认有一个空参构造器(权限与类相同)，但是一旦人为定义一个构造器，则默认空参构造器不再提供
    public Person() {
        this.name = "Muster";
        this.age = 0;
        this.isMale = false;
    }

    public Person(String name, int age, boolean isMale) {
        this(); //this调用构造器，也可以带参数，必须放在首行（仅能用一个）
        this.name = name;
        this.age = age;
        this.isMale = isMale;
    }

    //代码块：用来初始化类或对象（仅能用static修饰）
    static { //静态代码块，随着类的加载而自动执行，仅执行一次，可以用来初始化类的信息，可以定义多个静态代码块，按定义顺序执行，不可以调用非静态属性和方法
        System.out.println("static block");
    }

    { //非静态代码块，随着每次对象的创造而自动执行,可以对对象的属性等进行初始化，可以定义多个非静态代码块，按定义顺序执行，可以调用静态/非静态属性和方法
        System.out.println("unstatic block");
    }

    //方法: 方法中可以调用当前类的属性或方法，但不能方法中定义新方法
    public void eat() {
        System.out.println("eating...");
    }

    public void sleep() {
        System.out.println("zzzz.....");
    }

    public void talk() {
        System.out.println("^&^(&%^*%....");

    }

    //overload重载：同名函数参数个数或类型或不同（将他们的参数类型排成有序列，他们不同）（与方法的权限，返回值类型，形参变量名，方法体无关）
    public void talk(String s) {
        System.out.println(s);
    }

    //可变个数形参（仅一个且在末尾）(实质为数组) 与 形参类型相同的数组之间不构成重载（不可共存）
    public void talk(String... s) {
        System.out.println(s[0]);
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public boolean isMale() {
        return isMale;
    }

    /* 内部类
    内部类：成员内部类(静态，非静态)vs局部内部类（方法内、代码块内、构造器内）
    成员内部类：
        一方面，作为外部类的成员：
            调用外部类的结构
            可以被static修饰
            可以被四种不同权限修饰
        另一方面，作为类：
            类内可以定义属性、方法、构造器等。
            可以被final修饰，表示此类不能被继承。
            可以被abstract修饰
     */
    static class AA { //静态成员内部类
        //可调用静态的外部类成员、方法
    }

    class BB { //非静态成员内部类
        //可调用静态与非静态的外部类成员、方法
        public void method() {
            eat();
            Person.this.eat(); //与上等价
            /* 同名情况
            String n = name; <-形参
            String n = this.name; <-内部类属性
            String n = Person.this.name; <-外部类属性
             */
        }
    }

    public void innerClass() {
        final int num = 10; // JDK8+ 可省略此处显式final声明
        class CC { //成员内部类：很少用，一般用来创建一个想要的类然后生成一个实例将其return
            public void show(){
                System.out.println(num); //在局部内部类的方法中，如要调用其所在外部方法中的局部变量时，这个变量一定要声明为final
            }
        }
    }

    public static void useInnerClass(){
        Person.AA p = new Person.AA(); // 创建静态内部类对象
        //创建非静态内部类对象可以看psvm，因为需要实例化外部类先
    }

}

/*
JavaBean:
1.类是公共的
2.有一个无参的公共的构造器
3.有属性，且有对应的get、set方法
 */
class Customer {
    private int id;
    private String name;

    public Customer() {

    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}

/*
继承类
子类能够获得父类中的属性与方法（包括父类的私有属性与方法，但不能直接调，可通过父类的公有get方法获得）
通过子类构造器创造子类对象时，我们一定会直接或间接调用其父类的构造器，直至java.lang.Object类中的空参构造器为止
 */
class Student extends Person {

    public Student() {
        super(); //super()可以用来调用父类的构造器(可带参)（必须在首行，既与this（）仅能二选一）
        //若既没用this（形参列表）也没用super（形参列表），则系统默认调用super（）（父类空参构造器）
    }

    public void exercise() {
        System.out.println("100!");
    }

    /* 重写override/overwrite
    父类同名同参数列表,权限修饰符不小于父类，（不能重写父类中private方法，算是另起炉灶）
    父类返回值是void/基本数据类型，子类重写也只能返回void/相同基本数据类型。父类返回值是C类，子类重写也只能返回C类或其子类。
    子类中重写抛出的异常类不大于父类的抛出异常类
    子类和父类中的同名同参数的方法要么都声明为非static（考虑重写），要么都声明为static（不是重写）
     */
    @Override
    public void sleep() {
        super.sleep(); //super可以用来调用父类的属性、方法、构造器
        System.out.println("... at 2：00AM");
    }
}

class Chinese {
    /* 静态static
    可以用来修饰：属性，方法，代码块，内部类
     */
    static String nation = "Chinese"; //静态属性（公用，内存中仅一份），一改（其类）全改，随类的加载而加载（即早于对象的创建），可以通过类.静态变量来调用
    String name;
    int age;

    public static void show() { //静态方法，随类的加载而加载（即早于对象的创建），可以通过类.方法来调用
        System.out.println("Hi！I'm " + nation); //静态方法中，只能调用静态的方法或属性（非静态方法中两者都可以调），且不能使用this，super
    }
}

/*
利用静态设计单例模式
 */
class Bank1 { // 饿汉式实现(线程安全)
    //1.私有化类的构造器
    private Bank1() {
    }

    //2.内部创建类的对象
    private static Bank1 instance = new Bank1();

    //3.提供公共方法，返回类的对象
    public static Bank1 getInstance() {
        return instance;
    }
}

class Bank2 { // 懒汉式实现
    //1.私有化类的构造器
    private Bank2() {
    }

    //2.内部创建类的对象(先声明，不创建实例）
    private static Bank2 instance = null;

    //3.提供公共方法，返回类的对象
    public static Bank2 getInstance() {
        if (instance == null) {
            synchronized (Bank2.class) {
                if (instance == null) {
                    instance = new Bank2();
                }
            }
        }
        return instance;
    }

}

/* final
可以用来修饰类、方法、变量
 */
final class FinalA { //不能再被继承（有子类了）
    final double PI = 3.14; //常量,不能再被赋值，仅可以在显示初始化、代码块、构造器中初始化值，注意一定保证要被赋值
    static final double PII = 3.1415926; //全局常量，static final还可以修饰方法，即全局不可修改的方法

    public final void show() {
        // 不能在被重写了
    }

    public void show(final int num) { //final修饰形参，方法体内不可再对其进行修改
        final int NUM = 10; //常量，不可再对其进行修改
    }
}

/*
Object类的使用
 */
//1.Object clone(),需事先实现clonable接口
//2.boolean equals(Object obj) *
//3.void finalize(),回收对象，由垃圾收集器自动调用
//4.Class<?> getClass(),获取该实例的类
//5.int hashCode(),返回对象的哈希值
//6.void notify(),用于多线程
//7.void notifyAll(),用于多线程
//8.void wait(),用于多线程
//9.void wait(long timeout),用于多线程
//10.void wait(long timeout,int nanos),用于多线程
//11.String toString(),将对象转化成String *


