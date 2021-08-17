/**
 * @author Jiahao Wu
 * @date 2021/8/17 - 21:00
 */
public class AkEnum {
    public static void main(String[] args) {
        System.out.println(Season.SPRING); //打印出”SPRING“，即toString()
        Season[] values = Season.values(); //返回一个数组,里面遍历了枚举类中的所有对象
        Season winter = Season.valueOf("WINTER"); //返回指定名的对象，若无，则抛异常
    }
}

interface Info{
    void show();
}

//枚举类:默认继承于java.lang.Enum类
enum Season implements Info{
    //提供当前枚举类的对象，多个对象之间用，隔开”，“末尾对象用”;“结束
    SPRING("Spring","warm"){
        @Override
        public void show() { //Enum可以为每个对象分别实现接口,当然也可以用传统方式在方法中重写show

        }
    },
    SUMMER("Summer","hot"){
        @Override
        public void show(){

        }
    },
    AUTUMN("Autumn","cool"){
        @Override
        public void show() {

        }
    },
    WINTER("Winter","cold"){
        @Override
        public void show() {

        }
    };


    private final String seasonName;
    private final String seasonDesc;

    private Season(String seasonName,String seasonDesc){
        this.seasonName = seasonName;
        this.seasonDesc = seasonDesc;
    }

    public String getSeasonName() {
        return seasonName;
    }

    public String getSeasonDesc() {


        return seasonDesc;
    }
}

