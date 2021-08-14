/**
 * 文档注释，可以被javadoc解析生成文档
 * javadoc -d mydoc -author -version HelloWorld,java
 * @author Jiahao Wu
 * @date 2021/8/14 - 0:38
 * @version alpha1.0
 */
public class AaHelloWorld {
    /*
    多行注释
     */
    public static void main(String[] args) {
        System.out.print("++");
        System.out.println("Hello World++"); //单行注释
    }
}

/*
一个文件中能有多个类，但是只能有一个public类（且该类与文件名同名）
 */
class Person {

}
