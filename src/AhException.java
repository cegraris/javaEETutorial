import jdk.nashorn.internal.runtime.ECMAException;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @author Jiahao Wu
 * @date 2021/8/16 - 1:24
 */
public class AhException {
    /*
    Throwable （java.lang.Throwable）
        - Error （SVM的异常，资源异常） （java.lang.Error）
        - Exception （java.lang.Exception）
            - checked Exception 编译时异常，一定要处理，不然无法编译通过
                - IOException
                    -FIleNotFoundException
                - ClassNotFoundException
            - Runtime Exception 运行时异常，可以不处理，可以编译通过
                - NullPointerException
                - ArrayIndexOutOfBoundException
                - ClassCastException
                - NumberFormatException
                - InputMismatchException
                - ArithmeticException
     */

    public static void main(String[] args) {
        String str = "123";
        str = "abc";
        int num = 0;
        try{
            num = Integer.parseInt(str);
        }catch (NumberFormatException e){
            System.out.println("Wrong String");
        }catch(NullPointerException e){
            System.out.println("Null Pointer");
            e.printStackTrace(); //常用处理方法
        }catch(Exception e){
            System.out.println(e.getMessage());
        }finally{ //可选

        }
        System.out.println(num);

        //处理向上抛出的异常
        try{
            method1();
        } catch (FileNotFoundException e){

        } catch (IOException e){

        }

        try{
            regist(6);
        }catch(Exception e){
            System.out.println("Handle Exception");
        }
    }

    // 向上抛出异常
    // 方法执行时，出现异常，仍会在一场代码处生成一个异常类的对象，此对象满足throws后异常类型时，就会被抛出。后续代码不再执行
    public static void method1() throws FileNotFoundException, IOException{
    }

    // 手动抛出异常
    public static void regist(int id) throws Exception{
        if(id>0){
            System.out.println("legal ID");
        }else if (id>-5) {
            throw new RuntimeException("illegal ID"); //可以不处理
        }else{
            throw new Exception("illegal ID"); //一定要处理
        }
    }
}

/*
自定义异常类
1.继承与现有的异常结构：RuntimeException、Exception
2.提供全局常量serialVersionUID
3.重载构造器
 */
class MyException extends RuntimeException{
    static final long serialVersionUID = -3712564761715672L;
    public MyException(){}
    public MyException(String msg){super(msg);}
}
