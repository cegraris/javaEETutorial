/**
 * @author Jiahao Wu
 * @date 2021/8/15 - 20:04
 */
public class AfAbstract {
    public static void main(String[] args) {
        AbstractPerson ap = new AbstractPerson() { //匿名子类
            @Override
            public void eat() {
                System.out.println("yammy,yammy...");
            }
        };
    }

}

abstract class Creature{
}

//abstract用来修饰class和方法，不能用来修饰属性、构造器等结构
//abstract不能用来修饰私有方法、静态方法、final方法、final的类
abstract class AbstractPerson extends Creature{ //抽象类，无法实例化，是用来生成可实例化子类的
    // 抽象类中一定有构造器，便于子类实例化时调用
    String name;
    int age;
    public AbstractPerson(){

    }
    public void sleep(){ // 抽象类中可以放置非抽象方法
        System.out.println("zzzz....");
    }
    public abstract void eat(); //抽象方法，只有声明，没有方法体，等待子类去重写实现
}

/*
模板方法设计模式TemplateMethod
先实现固定通用方法，留下可变的部分，提供不同的子类去实现
 */
abstract class Template{
    public void spendTime(){
        long start = System.currentTimeMillis();
        this.code();
        long end = System.currentTimeMillis();
        System.out.println("Time spent: " + (end-start));
    }
    public abstract void code();
}