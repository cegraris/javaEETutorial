import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
    public void reflection() throws Exception{
        //通过反射创建类的对象
        Class clazz = Human.class;
        Constructor constructor = clazz.getConstructor(String.class, int.class);//获取指定参数的构造器
        Object obj = constructor.newInstance("Tom",12); //实际生成了一个新Human对象
        Human h = (Human) obj;
        //通过反射调用属性
        Field age = clazz.getDeclaredField("age");
        age.set(h,10);
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
        name.set(h2,"Peter");
        //调用私有方法
        Method showNation = clazz.getDeclaredMethod("showNation", String.class);
        showNation.setAccessible(true);
        String nation = (String) showNation.invoke(h2,"China"); //返回该调取方法的返回值
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
    public void testClassLoader(){
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

}

class Human{
    private String name;
    public int age;

    public Human(String name, int age) {
        this.name = name;
        this.age = age;
    }

    private Human(String name){
        this.name = name;
    }

    public Human(){

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

    public void show(){
        System.out.println("Hello");
    }

    private String showNation(String nation){
        System.out.println("I come from "+nation);
        return nation;
    }
}