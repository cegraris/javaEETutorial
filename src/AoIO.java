import java.io.File;
import java.io.IOException;

/**
 * @author Jiahao Wu
 * @date 2021/8/23 - 0:37
 */
public class AoIO {

    public static void main(String[] args) {
        //java.io.File
        //1.构造器一
        File file = new File("hello.txt"); //相对路径
        File file2 = new File("F:\\JavaProject\\javaEETutorial\\src\\hello.txt"); //绝对路径(路径分隔符：windows：\\,Linux：/)
        //2.构造器二
        File file3 = new File("F:\\JavaProject\\javaEETutorial","src"); //File也可以是一个文件目录
        //3.构造器三
        File file4 = new File(file3,"hi.txt");

        //当硬盘中真的有文件时才会有特殊值，不然就是默认值
        file.getAbsolutePath(); //完整路径
        file.getPath(); //路径
        file.getName(); //文件名
        file.getParent(); //父级目录
        file.length(); //字节长度
        file.lastModified(); //最近修改时间（1970毫秒）
        //一下两个方法仅适用于目录File，且需存在，不然报错
        file3.list(); //返回Stirng[]返回目录下的所有文件或目录的名称数组
        file3.listFiles(); //返回File[]返回录下的所有文件或目录的File数组
        //移动，file存在，file5不存在
        file.renameTo(new File("F:\\JavaProject"));

        //判断
        file.isDirectory();
        file.isFile();
        file.exists();
        file.canRead();
        file.canRead();
        file.canWrite();
        file.isHidden();

        //文件创建与删除
        try {
            file4.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        file4.delete();
        //目录创建与删除
        file3.mkdir(); //创建目录，若上级目录不存在则失败，返回false
        file3.mkdirs(); //创建目录，若上级目录不存在则建立上级目录
    }
}
