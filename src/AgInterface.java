/**
 * @author Jiahao Wu
 * @date 2021/8/15 - 21:10
 */
public class AgInterface {
    public static void main(String[] args) {
        //1.创建了接口的非匿名实现类的非匿名对象
        Bullet k = new Bullet();
        //2.创建了接口的非匿名实现类的匿名对象
        F fly = new F();
        fly.flySomething(new Bullet());
        //3.创建了接口的匿名实现类的非匿名对象
        Flyable fly1 = new Flyable() {
            @Override
            public void fly() {

            }

            @Override
            public void stop() {

            }
        };
        fly.flySomething(fly1);
        //4.创建了接口的匿名实现类的匿名对象
        fly.flySomething(new Flyable() {
            @Override
            public void fly() {

            }

            @Override
            public void stop() {

            }
        });


        // 代理模式相关操作
        Server server = new Server();
        ProxyServer proxyServer = new ProxyServer(server);
        proxyServer.browse();

    }
}

/* 接口（和类并列）
 * JDK7-：只能定义全局常量（public static final）和抽象方法（public abstract）
 * JDK8+: 可以额外定义静态方法、默认方法
 * */
interface Flyable {
    //全局常量
    public static final int MAX_SPEED = 7900;
    int MIN_SPEED = 1; // (可省略 public static final，会默认)

    //接口中不能定义构造器，接口不可以实例化，必须靠类去实现来使用！

    //抽象方法
    public abstract void fly();

    void stop(); // (可省略 public abstract，会默认)

    public static void method1(){ //静态方法：只能通过接口调用，不会被继承
        System.out.println("static method");
    }

    public default void method2(){ //默认方法：通过实现类的对象去调用接口中的默认方法
        //注意，如果实现类重写了接口中的默认方法，调用时，仍然调用的是重写以后的方法
        //如果子类（或实现类）继承的父类和实现接口中声明了同名同参数的方法，那么在没重写的情况下，调用父类的方法-->类优先原则
        //如果子类（或实现类）实现的多个接口中声明了同名同参数的方法，那么在没重写的情况下，报错-->接口冲突
        System.out.println("default method");
        /*在子类（实现类中）调用同名方法，一个类又有继承又有实现如何分别指定要使用的同名类：
        super.method(); <- 调用父类中的方法
        Flyable.super.method(); <- 调用Flyable接口的默认类
        Attackable.super.methode(); <-调用Attackable接口的默认类
        * */
    }

}

interface Attackable {
    public abstract void aim();
}

class Plane implements Flyable { //具体实现接口
    @Override
    public void fly() {
        System.out.println("5,4,3,2,1...");
    }

    @Override
    public void stop() {
        System.out.println("000000000...");
    }
}

abstract class Kite implements Flyable { //抽象实现接口
    @Override
    public void fly() {

    }
}

class Bullet extends Object implements Flyable, Attackable { //单继承，多实现
    @Override
    public void fly() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void aim() {

    }
}

interface AA {
}

interface BB {
}

interface CC extends AA, BB { //接口可以多继承
}

class F {
    public void flySomething(Flyable f) { //直接使用实现该接口的所有对象(多态)
        f.fly();
        f.stop();
    }

}

/*
代理模式Proxy
 */
interface Network {
    public void browse();
}

//被代理类:实现核心功能
class Server implements Network {
    @Override
    public void browse() {

    }
}

//代理类
class ProxyServer implements Network {
    private Network nw;

    public ProxyServer(Network work) {
        this.nw = work;
    }

    public void check() { //实现一些额外功能
        System.out.println("check... ");
    }

    @Override
    public void browse() {
        check(); //额外功能由代理类实现
        nw.browse(); //核心功能仍由被代理类实现
    }
}

/*
工厂模式 Factory
写一个额外的工厂接口 Factory，有一个抽象方法用来返回一个对象
对于不同的想要返回的对象，（通过反射）生成不同的对应类去实现工厂接口，实现返回指定对象
（抽象工厂：族）
 */

