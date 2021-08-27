import org.junit.Test;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

/**
 * @author Jiahao Wu
 * @date 2021/8/23 - 0:37
 */
public class AoIO {

    public static void main(String[] args) {
        //java.io.File
        //1.构造器一
        File file = new File("hello.txt"); //相对路径（在main方法就是相对于当前工程，否则就是相对于当前Module）
        File file2 = new File("F:\\JavaProject\\javaEETutorial\\src\\hello.txt"); //绝对路径(路径分隔符：windows：\\,Linux：/)
        //2.构造器二
        File file3 = new File("F:\\JavaProject\\javaEETutorial", "src"); //File也可以是一个文件目录
        //3.构造器三
        File file4 = new File(file3, "hi.txt");

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

    /* IO流
    概念：输入流，输出流，字节流（8bit），字符流（16bit），节点流（作用在数据上的流），处理流（作用在流上的流）
    抽象类：
    输入流->InputStream字节流,Reader字符流
    输出流->OutputStream字节流,Writer字符流


                    字节输入流                   字节输出流               字符输入流                字符输出流
     ------- ------------------------ ------------------------- ---------------------- -----------------------
      抽象基类    InputStream(*)           OutputStream(*)           Reader(*)              Writer(*)
      访问文件    FileInputStream(*)       FileOutputStream(*)       FileReader(*)          FileWriter(*)
      访问数组    ByteArrayInputStream     ByteArrayOutputStream     CharArrayReader        CharArrayWriter
      访问管道    PipedInputStream         PipedOutputStream         PipedReader            PipedWriter
      访问字符串                                                     StringReader           StringWriter
      缓冲流     BufferedInputStream(*)   BufferedOutputStream(*)   BufferedReader(*)      (*)
      转换流                                                        InputStreamReader(*)   OutputStreamWriter(*)
      对象流     ObjectInputStream(*)     ObjectOutputStream(*)
                FilterInputStream        FilterOutputStream        FilterReader           FilterWriter
      打印流                              PrintStream                                      PrintWriter
      推回输入流  PushbackInputStream                                PushbackReader
      特殊流     DataInputStream          DataOutputStream

     */
    @Test
    public void testFileReader() {
        FileReader fr = null;
        BufferedReader br = null;
        try {
            //1.实例化File类的对象，指明要操作的文件
            File file = new File("src\\hello.txt");//相对于当前共Module
            System.out.println(file.getAbsolutePath());
            //2.提供具体的流，若文件不存在会报异常
            fr = new FileReader(file);
            br = new BufferedReader(fr);
            //3.数据读入（char转成int）,如果达到文件末尾返回-1
            //方式一
//            int read = fr.read(); //一次读一个
//            while (read!=-1) {
//                System.out.print((char)read);
//                read = fr.read();
//            }
            //方式二
//            char[] cbuf = new char[5];
//            int len;
//            while ((len = fr.read(cbuf)) != -1) { //一次读多个，返回每次读入的个数，达到文件末尾返回-1
////                for(int i = 0;i<len;i++){
////                    System.out.print(cbuf[i]);
////                }
//                String str = new String(cbuf, 0, len); //上面或这里两种方法都可以
//                System.out.print(str);
//            }
            //方式三
            String data;
            while ((data = br.readLine()) != null) { //BufferedReader有readLine（）函数，方便一次读一行
                System.out.print(data + "\n"); //data中不包含换行符
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //4.流的关闭
            try {
                if (br != null) {
                    br.close();
                }
                if (fr != null) {
                    fr.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void testFileWriter() {
        FileWriter fw = null; //可以选择是否覆盖源文件，默认覆盖（false）
        try {
            //1.提供File类的对象，指明写出到的文件
            File file = new File("src\\hello1.txt");
            //2.提供FileWriter的对象，用于数据的写出，不存在会创建文件
            fw = new FileWriter(file, true);
            //3.写出的操作
            fw.write("I hava a dream!\n");
            fw.write("Hello world.");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //4.关闭流资源
            try {
                if (fw != null) {
                    fw.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void testFileInputStream() {
        FileInputStream fis = null;
        FileOutputStream fos = null;
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            File file = new File("\\src\\cat.jpg");
            File file2 = new File("\\src\\cat_copy.jpg");
            fis = new FileInputStream(file);
            fos = new FileOutputStream(file2);
            bis = new BufferedInputStream(fis); //缓冲流代理类，提高读取速度
            bos = new BufferedOutputStream(fos); //缓冲流代理类，提高写速度
            byte[] buffer = new byte[1024];
            int len;
            while ((len = bis.read(buffer)) != -1) {
                bos.write(buffer, 0, len);
//                bos.flush(); //显示刷新（清空）缓存区，立即写出
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bis != null) { //必须先关外层的流
                    bis.close(); //实际上关闭外层的同时，内层流也会自动关闭，不必再额外关闭内层流
                }
                if (bos != null) {
                    bos.close();
                }
                if (fis != null) {
                    fis.close();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void testStreamReader() {
        /* 转换流
        InputStreamReader 字节输入流->字符输入流 （解码）
        OutputStreamWriter 字符输出流->字节输出流 （编码）
         */
        FileInputStream fis = null;
        FileOutputStream fos = null;
        InputStreamReader isr = null;
        OutputStreamWriter osw = null;
        try {
            fis = new FileInputStream("\\src\\hello.txt");
            fos = new FileOutputStream("\\src\\copy_hello.txt");
            isr = new InputStreamReader(fis, "UTF-8"); //输入：字节流包装成字符流（读的文件原来时utf-8编码）
            osw = new OutputStreamWriter(fos, "gbk");//输出：字符流包装成字节流
            char[] cbuf = new char[1024];
            int len;
            while ((len = isr.read(cbuf)) != -1) {
                osw.write(cbuf,0,len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (osw != null) {
                    osw.close();
                }
                if (isr != null) {
                    isr.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    /* UTF-8 (8bits一读进行解析)
    0xxxxxxx (兼容ASCII）
    110xxxxx 10xxxxxx
    1110xxxx 10xxxxxx 10xxxxxx
    11110xxx 10xxxxxx 10xxxxxx 10xxxxxx
     */

    @Test
    public void testOtherStreams(){
        /* 标准输入、输出流
        System.in:标准输入字节流，默认从键盘输入
        System.out:标准输出字节流，默认从控制台输出
        setIn(InputStream is),setOut(PrintStream ps)方式重新指定输入和输出的流
         */
        //IDEA的单元测试不支持控制台输入
        BufferedReader br = null; //缓冲流
        try {
            InputStreamReader isr = new InputStreamReader(System.in); //转换流
            br = new BufferedReader(isr);
            while(true){
                String data = br.readLine();
                if("e".equalsIgnoreCase(data)||"exit".equalsIgnoreCase(data)){
                    System.out.println("Program closed.");
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        /* 打印流
        PrintStream和PrintWriter，体哦概念股了一系列重载的print()和println()方法，用于多种数据类型的输出
        不会抛出IOException异常
        会自动flush
        PrintStream打印将默认字符编码转化为字节，PrintWriter则转弯为字符
        System.out返回的是PrintStream的实例
         */
        PrintStream ps = null;
        try {
            FileOutputStream fos = new FileOutputStream(new File("\\src\\hello.txt"));
            ps = new PrintStream(fos, true); //创建打印输出流，自动刷新（写入换行符或\n时自动刷新输出缓冲区）
            if (ps != null) {
                System.setOut(ps);//设置打印到的位置（一个打印输出流实例），默认控制台
            }
            System.out.println("hello world."); //会打印到文件而不是控制台
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ps!=null) {
                    ps.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        /* 数据流
        DataInputStream, DataOutputStream套在InputStream和OutputStream子类的流上
        方便操作基本数据类型和String数据类型
        boolean readBoolean()
        char readChar()
        double readDouble()
        long readLong()
        String readUTF()
        byte readByte()
        float readFloat()
        short readShort()
        int readInt()
        void readFully(byte[] b)
        可以用对应的write去写，用read去读，但是顺序要相同
         */

        DataOutputStream dos = null;
        try {
            dos = new DataOutputStream(new FileOutputStream("\\src\\hello.txt"));

            dos.writeUTF("hello");
            dos.flush();
            dos.writeInt(23);
            dos.flush();
            dos.writeBoolean(true);
            dos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (dos != null) {
                    dos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void testObjectStreams() throws IOException, ClassNotFoundException {
        /* 对象流：保存和读取类（实现了Serializable接口）
        ObjectInputStream:反序列化，从硬盘读取到内存
        ObjectOutputStream:序列化，保存类到硬盘
         */
        //序列化
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("object.dat"));
        oos.writeObject(new String("Hello World."));
        oos.flush();
        oos.close();
        //反序列化
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream("object.dat"));
        Object obj = ois.readObject();
        String str = (String) obj;
        ois.close();
    }

    @Test
    public void testRandomStreams(){
        /* 随机存取文件流
           java.io.RandomAccessFile（既可以作为输入流，又可以作为输出流）
           实现DataInput和DataOutput接口
           支持只访问文件的部分内容（跳到文件的任意地方来进行读写）
           可以向已存在的文件后追加内容
           构造器：RandomAccessFile(File file,String mode)
           构造器：RandomAccessFile(String name,String mode)
           mode:
           r:以只读方式打开（不存在不会创建新文件，不存在报异常）
           rw:打开以便读取和写入（不存在会创建新文件）（默认从头开始逐步覆盖）
           rwd:打开以便读取和写入，同步文件内容的更新
           rws:打开以便读取和写入，同步文件内容和元数据的更新
           用seak（int pos）方法，可以将指针调第n个字符
         */
    }

    @Test
    public void testTCP() throws IOException {
        //实例化IP地址（本地回路127.0.0.1,localhost）
        InetAddress inet1 = InetAddress.getByName("192.168.10.14");
        InetAddress inet2 = InetAddress.getByName("www.google.com");
        InetAddress inet3 = InetAddress.getByName("127.0.0.1"); //本机
        InetAddress inet4 = InetAddress.getLocalHost(); //本机
        //InetAddress方法
        System.out.println(inet2.getHostName()); //获取域名
        System.out.println(inet2.getHostAddress()); //获取IP地址

        //TCP -> 客户端
        InetAddress inet = InetAddress.getByName("127.0.0.1");
        Socket socket = new Socket(inet,8899); //创建Socket对象，指明服务器端的ip和端口号
        OutputStream os = socket.getOutputStream(); //获取一个输出流，用于输出数据
        os.write("hello,i am clinet".getBytes()); //写出数据的操作
        socket.shutdownOutput(); //告知服务器端结束输出操作

        InputStream inputStream = socket.getInputStream(); //从服务器端输入流
        ByteArrayOutputStream baos2 = new ByteArrayOutputStream(); //输出到控制台流
        byte[] buffer2 = new byte[20];
        int len2;
        while((len2=inputStream.read(buffer2))!=-1){
            baos2.write(buffer2,0,len2);
        }
        System.out.println(baos2.toString());

        baos2.close(); //关闭资源
        os.close();
        socket.close();
        //TCP -> 服务器
        ServerSocket ss = new ServerSocket(8899); //创建ServerSocket，指明自己端口号
        Socket so = ss.accept(); //获取客户端的Socket
        InputStream is = so.getInputStream(); //获取输入流
//        以下方法读中文字符会产生乱码
//        byte[] buffer = new byte[200];
//        int len;
//        while((len=is.read(buffer))!=-1){
//            String str = new String(buffer,0,len);
//            System.out.println(str);
//        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream(); //读取输入流中的数据
        byte[] buffer = new byte[5];
        int len;
        while((len=is.read(buffer))!=-1){
            baos.write(buffer,0,len);
        }
        System.out.println(baos.toString());

        OutputStream serveros = so.getOutputStream(); //往客户端的流
        serveros.write("SUCCESS".getBytes()); //输出

        serveros.close(); //资源关闭
        baos.close();
        is.close();
        socket.close();
        ss.close();
    }

    @Test
    public void testUDP() throws IOException {
        //发送端
        DatagramSocket socket = new DatagramSocket();
        String str = "Hello";
        byte[] data = str.getBytes(StandardCharsets.UTF_8);
        InetAddress inet = InetAddress.getLocalHost();
        DatagramPacket packet = new DatagramPacket(data,0,data.length,inet,9090);
        socket.send(packet);
        socket.close();
        //接收端
        DatagramSocket socket2 = new DatagramSocket(9090);
        byte[] buffer = new byte[100];
        DatagramPacket packet2 = new DatagramPacket(buffer,0,buffer.length);
        socket.receive(packet2);
        System.out.println(new String(packet.getData(),0,packet2.getLength()));
        socket2.close();
    }

    @Test
    public void testURL() throws IOException {
        //URL
        //<传输协议>://<主机名>:<端口号>/<文件名>#片段名?参数列表
        URL url = new URL("http://www.google.com");
        System.out.println(url.getProtocol()); //协议名
        System.out.println(url.getHost()); //主机名
        System.out.println(url.getPort()); //端口号
        System.out.println(url.getPath()); //文件路径
        System.out.println(url.getFile()); //文件名
        System.out.println(url.getQuery()); //查询名（参数列表）
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

        urlConnection.connect();
        InputStream is = urlConnection.getInputStream();

        byte[] buffer = new byte[1024];
        int len;
        while((len=is.read(buffer))!=-1){
            System.out.println(buffer.toString());
        }

        is.close();
        urlConnection.disconnect();
    }


}

/*
可被对象流保存和读取的类：
1.需要实现Serializable接口
2.当前类提供一个全局常量：serialVerisonUID
3.其内部所有的属性必须是可序列化的（默认基本数据类型和String都是可序列化的）
4.static 和 transient修饰的成员变量不能序列化
 */
class Persons implements Serializable{
    public static final long serialVersionUID = 475463534532L; //用以识别不同版本的类为同一个类，使得保存与读取不被类的修改而影响

    private String name;
    private int age;

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
