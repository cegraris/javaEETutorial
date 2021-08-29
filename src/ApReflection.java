import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.Properties;

/**
 * @author Jiahao Wu
 * @date 2021/8/28 - 12:06
 */
public class ApReflection {

    /* 反射
    java.lang.Class 代表一个类
        class:外部类，成员（成员内部类，静态内部类），局部内部类，匿名内部类 Object.class
        interface：接口 Comparable.class
        []：数组 String[].class, int[][].class (相同类型与维度的数组为同一Class)
        enum：枚举 ElementType.class
        annotation：注解@interface Override.class
        primitive type：基本数据类型 int.class
        void 无返回值函数 void.class
        Class本身 Class.class
    java.lang.reflect.Method 代表类的方法
    java.lang.reflect.Field 代表类的成员变量
    java.lang.reflect.Constructor 代表类的构造器
     */
    @Test
    public void reflection() throws Exception {
        //通过反射创建类的对象
        Class clazz = Human.class;
        Constructor constructor = clazz.getConstructor(String.class, int.class);//获取指定参数的构造器
        Object obj = constructor.newInstance("Tom", 12); //实际生成了一个新Human对象
        Human h = (Human) obj;
        //通过反射调用属性
        Field age = clazz.getDeclaredField("age");
        age.set(h, 10);
        //通过反射调用方法
        Method show = clazz.getDeclaredMethod("show");
        show.invoke(h);
        //通过反射可以调用类的私有结构（构造器、方法、属性）
        Constructor declaredConstructor = clazz.getDeclaredConstructor(String.class);
        declaredConstructor.setAccessible(true);
        Human h2 = (Human) declaredConstructor.newInstance("Jerry");
        //调用私有属性
        Field name = clazz.getDeclaredField("name");
        name.setAccessible(true);
        name.set(h2, "Peter");
        //调用私有方法
        Method showNation = clazz.getDeclaredMethod("showNation", String.class);
        showNation.setAccessible(true);
        String nation = (String) showNation.invoke(h2, "China"); //返回该调取方法的返回值
    }

    @Test
    public void testGetClass() throws ClassNotFoundException {
        Class clazz1 = Human.class; //方式一：调用运行时类的属性

        Human p = new Human();
        Class clzz2 = p.getClass(); //方式二：通过运行时类的对象获取

        Class clazz3 = Class.forName("Human"); //方式三：调用Class的静态方法 (*)

        ClassLoader classLoader = ApReflection.class.getClassLoader(); //方法四：使用类的加载器(了解）
        Class clazz4 = classLoader.loadClass("Human");

    }

    @Test
    public void testClassLoader() {
        //对于自定义类，使用系统类加载器进行加载
        ClassLoader classLoader = ApReflection.class.getClassLoader();
        //调用系统类加载器的getParent()获取扩展类加载器
        ClassLoader classLoader1 = classLoader.getParent();
        //调用扩展类加载器的getParent()无法获取引导类加载器
        //引导类加载器主要负责加载java的核心类库，无法加载自定义类
        ClassLoader classLoader2 = classLoader1.getParent();
        ClassLoader classLoader3 = String.class.getClassLoader();
    }

    @Test
    public void testProperties() throws IOException {
        Properties pros = new Properties();

        ClassLoader classLoader = ApReflection.class.getClassLoader();
        InputStream is = classLoader.getResourceAsStream("jdbc.properties"); //默认位置为当前module的src下
        pros.load(is);

        String user = pros.getProperty("user");
        String password = pros.getProperty("password");

    }

    @Test
    public void newInsatanceTest() throws InstantiationException, IllegalAccessException {
        Class<Human> clazz = Human.class; //类的泛型决定了newInstance返回的类
        /*
        newInstance():调用此方法，创建对应的运行时类的对象，内部调用了运行时类的空参的构造器
        要想此方法正常的创建运行时类的对象，要求：
        1.运行时类必须提供空参的构造器
        2.空参的构造器的访问权限得够，通常设置为public
        在javabean中要求提供一个public的空参构造器，原因：
        1.便于通过反射，创建运行时类的对象
        2.便于子类继承此运行时类时，默认调用super()时，保证父类有此构造器
         */
        Human human = clazz.newInstance();
    }

    @Test
    public void getFieldAndMethodTest() {
        Class clazz = Human.class;
        //getFields(),获取当前运行时类及其父类中声明为public访问权限的属性
        Field[] fields = clazz.getFields();
        for (Field f : fields) {
            System.out.println(f);
        }

        //getDeclaredFields(),获取当前运行时类中声明的所有属性（所有权限）（不包含父类中声明的属性）
        Field[] declaredFiedls = clazz.getDeclaredFields();
        for (Field f : declaredFiedls) {
            //获取属性
            System.out.println(f);
            //获取权限修饰符
            int modifier = f.getModifiers();
            System.out.println(Modifier.toString(modifier));
            //获取数据类型
            Class type = f.getType();
            System.out.println(type.getName() + "\t");
            //变量名
            Class fName = f.getType();
            System.out.println(fName);
        }
        //getMethods()获取当前运行时类及其父类中声明为public权限的方法
        Method[] methods = clazz.getMethods();
        for (Method m : methods) {
            System.out.println(m);
        }
        //getDeclaredMethods()获取当前运行时类中声明的所有方法（不包含父类中声明的方法）
        Method[] declaredMethods = clazz.getDeclaredMethods();
        for (Method m : declaredMethods) {
            System.out.println(m);
            //1.获取方法声明的注解(注解需是Retention需设置为RUNTIME)
            Annotation[] annos = m.getAnnotations();
            for (Annotation a : annos) {
                System.out.println(a);
            }
            //2.获取权限修饰符
            System.out.print(Modifier.toString(m.getModifiers()) + "\t");
            //3.获取返回值类型
            System.out.print(m.getReturnType().getName() + "\t");
            //4.获取方法名
            System.out.println(m.getName() + "(");
            //5.获取形参列表
            Class[] parameterTypes = m.getParameterTypes();
            if (!(parameterTypes == null && parameterTypes.length == 0)) {
                for (int i = 0; i < parameterTypes.length; i++) {
                    if (i == parameterTypes.length - 1) {
                        System.out.println(parameterTypes[i].getName() + "args_" + i);
                        break;
                    }
                    System.out.print(parameterTypes[i].getName() + "args_" + i + ",");
                }
            }
            System.out.println(")");
            //6.抛出的异常
            Class[] exceptionTypes = m.getExceptionTypes();
            if (exceptionTypes.length > 0) {
                System.out.println("throws");
                for (int i = 0; i < exceptionTypes.length; i++) {
                    if (i == exceptionTypes.length - 1) {
                        System.out.println(exceptionTypes[i].getName());
                        break;
                    }
                    System.out.print(exceptionTypes[i].getName() + ",");
                }
            }
            System.out.println();
        }
        //getConstructors()获取当前运行时类中声明为public的构造器
        Constructor[] constructors = clazz.getConstructors();
        for (
                Constructor c : constructors) {
            System.out.println(c);
        }

        //getDeclaredConstructors()获取当前运行时类中声明的所有构造器
        Constructor[] declaredConstructors = clazz.getDeclaredConstructors();
        for (
                Constructor c : declaredConstructors) {
            System.out.println(c);
        }

        //运行时类的父类
        Class superclass = clazz.getSuperclass();
        System.out.println(superclass);
        //运行时类的带泛型的父类
        Type genericSuperclass = clazz.getGenericSuperclass();
        System.out.println(genericSuperclass);
        //运行时类的带泛型的父类的泛型
        ParameterizedType paramType = (ParameterizedType) genericSuperclass;
        Type[] actualTypeArguments = paramType.getActualTypeArguments();
        System.out.println(actualTypeArguments[0].getTypeName());
        System.out.println(((Class) actualTypeArguments[0]).getName());
        //获取运行时类实现的接口(不会get父类实现的接口)
        Class[] interfaces = clazz.getInterfaces();
        for (
                Class c : interfaces) {
            System.out.println(c);
        }

        //获取运行时类所在的包
        Package pack = clazz.getPackage();
        System.out.println(pack);
        //获取运行时类声明的注解
        Annotation[] annotations = clazz.getAnnotations();
        for (
                Annotation annos : annotations) {
            System.out.println(annos);
        }
    }


    /*
    如何操作运行时类中指定的属性和方法
     */
    @Test
    public void testReflection() throws Exception {
        //调用运行时类中指定的结构：属性、方法、构造器
        Class clazz = Human.class;
        Human h = (Human) clazz.newInstance();
        //获取指定的属性:要求运行时类中属性声明为public
        //通常不采用此方法
        Field id = clazz.getField("id");
        //设置当前属性的值
        id.set(h, 1001);
        //获取当前属性的值
        int pId = (int) id.get(h);

        //目前主要采用此方法：
        //调用属性
        //1.getDeclaredField(String fieldName):获取运行时类中指定变量名的属性
        Field name = clazz.getDeclaredField("name");
        //2.保证当前属性是可访问的
        name.setAccessible(true);
        //3.获取、设置指定对象的此属性值
        name.set(h, "Tom");

        //调用方法
        //1.获取指定的某个方法getDeclaredMethod():参数一：指明获取的方法的名称，参数二：指明获取的方法的形参列表
        Method show = clazz.getDeclaredMethod("show", String.class);
        //2.保证当前方法是可访问的
        show.setAccessible(true);
        //3.调用invoke()：参数一：方法的调用者，方法二：给方法形参赋值的实参
        Object returnValue = show.invoke(h, "CHN");
        String returnValueString = (String) returnValue;

        //调用静态方法
        //private static void showDesc()
        Method showDesc = clazz.getDeclaredMethod("showDesc");
        showDesc.setAccessible(true);
        Object returnVal = showDesc.invoke(Human.class); //void方法返回null

        //调用构造器（不常用,还是更多用clazz.newInstance()）
        //private Person(String name)
        //1.获取指定的构造器getDeclaredConstructor():参数：指明构造器的参数列表
        Constructor constructor = clazz.getDeclaredConstructor(String.class);
        //2.保证此构造器是可访问的
        constructor.setAccessible(true);
        //3.调用此构造器创造运行时类的对象
        Human hTom = (Human) constructor.newInstance("Tom");
    }

    @Test
    public void testProxy() {
        //静态代理:代理类和被代理类在编译期间就确定下来了
        NikeClothFactory nike = new NikeClothFactory(); //被代理类
        ProxyClothFactory proxyClothFactory = new ProxyClothFactory(nike); //代理类
        proxyClothFactory.produceCloth();
        //动态代理：
        //问题1：如何根据加载到内存中的被代理类，动态的创建一个代理类及其对象
        //问题2：当通过代理类的对象调用方法时，如何动态的去调用被代理类中的同名方法
//        SuperMan superMan = new SuperMan();
//        Human proxyInstance = (Human) ProxyFactory.getProxyInstance(SuperMan);
//        proxyInstance.eat("Yammy");
    }

}

class Human {
    private String name;
    public int age;

    public Human(String name, int age) {
        this.name = name;
        this.age = age;
    }

    private Human(String name) {
        this.name = name;
    }

    public Human() {

    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Human{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    public void show() {
        System.out.println("Hello");
    }

    private String showNation(String nation) {
        System.out.println("I come from " + nation);
        return nation;
    }
}

//*****静态代理*****
interface ClothFactory {
    void produceCloth();
}

//代理类
class ProxyClothFactory implements ClothFactory {
    private ClothFactory factory; //用被代理类对象进行实例化

    public ProxyClothFactory(ClothFactory factory) {
        this.factory = factory;
    }

    @Override
    public void produceCloth() {
        System.out.println("Proxy ...");
        factory.produceCloth();
        System.out.println("Proxy ...");
    }
}

//被代理类
class NikeClothFactory implements ClothFactory {
    @Override
    public void produceCloth() {
        System.out.println("Nike ...");
    }
}

//*****动态代理*****
class ProxyFactory {
    //调用此方法，返回一个代理类的对象
    public static Object getProxyInstance(Object obj) { //obj:被代理的对象
        MyInvocationHandler handler = new MyInvocationHandler();
        handler.bind(obj);
        return Proxy.newProxyInstance(obj.getClass().getClassLoader(),obj.getClass().getInterfaces(),handler);
    }
}

class MyInvocationHandler implements InvocationHandler{
    private Object obj; //赋值时需要使用被代理类的对象
    public void bind(Object obj){
        this.obj = obj;
    }
    //当我们通过代理类的对象，调用方法a时，就会自动地调用如下的方法
    //将被代理类要执行的方法a的功能就声明再invoke()中
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //method即为代理类对象调用的方法，此方法也就作为了被代理类对象要调用的方法
        //obj：被代理的对象
        Object returnValue = method.invoke(obj,args);
        //上述方法的返回值就作为invoke()的返回值
        return returnValue;
    }
}