import java.lang.annotation.*;
import java.util.ArrayList;

/**
 * @author Jiahao Wu
 * @date 2021/8/17 - 21:37
 */
/* Annotation
1.是代码里的特殊标记，可以在编译，类加载，运行时被读取，并执行相应的处理，
程序员可以在不改变原有逻辑的情况下，在源文件中嵌入一些补充信息
2.主要使用方式
    2.1生成文档相关的注解
    2.2在编译时进行格式检查
        @Override,@Deprecated,@SuppressWarnings
    2.3跟踪代码的依赖性，实现替代配置文件功能
    2.4JDK中的元注解（修饰Annotation的注解）
        @Retention：注解生命周期 SOURCE\CLASS（默认生命周期）\RUNTIME(仅此支持反射获取) (*)
        @Target：注解可以用来修饰的内容(*)
            （以下JDK8+）
            ElementType.TYPE_PARAMETER 表示该注解能写在类型变量的声明语句中（如泛型声明）
            ElementType.TYPE_USE 表示该注解能用来修饰任何出现类型的地方
        @Documented:注解将被提取成文档
        @Inherited：被其修饰的类，子类会自动继承这个修饰
        @Repeatable():可重复注解(JDK8+)

 */
@MyAnnotation({"a","b"})//自定义注解，稍后在类中用反射去读取这行注解中的内容
@MyAnnotation({"c","d"})//声明过@Repeatable的Annotation
public class AlAnnotation {
    public static void main(String[] args) {
        @SuppressWarnings("unused") //忽视编译器警告
        int num = 10;
        @SuppressWarnings({"unused","rawtypes"}) //忽视编译器警告
        ArrayList list = new ArrayList();

    }
}

//自定义注解
@Repeatable(MyAnnotation2.class)
@Retention(RetentionPolicy.RUNTIME) // 注解保留的生命周期,此处：到Class文件
@Target({ElementType.TYPE,ElementType.TYPE_PARAMETER})
@Inherited
@interface MyAnnotation{
    //成员变量以无参数方法的形式声明，方法名和返回值定义了该成员的名字和类型
    //支持八种基本数据类型, String类型,Class,enum,Annotation
    //如果自定义注解没有成员，表明是一个表示作用
    String[] value(); //一般名为value
//    String value2() default "hello"; <-默认值
}

//下面这个部分是用来支持repetable功能
@Retention(RetentionPolicy.RUNTIME)//要相同
@Target({ElementType.TYPE,ElementType.TYPE_PARAMETER})//要相同
@Inherited//要相同
@interface MyAnnotation2{
    MyAnnotation[] value();
}
