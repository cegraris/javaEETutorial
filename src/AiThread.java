import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Jiahao Wu
 * @date 2021/8/16 - 10:15
 */
/* 多线程 java.lang.Thread
    线程的State：新建，就绪，运行，阻塞，死亡
    开启阻塞：sleep(),join(),等待同步锁,wait()
    取消阻塞：sleep()到点,joint()结束,获得同步锁,notify()/notifyAll()
 */
public class AiThread {
    public static void main(String[] args) {
        //3.创建Thread类的子类的对象
        Thread1 t1 = new Thread1();
        t1.setName("++Thread0++");
        System.out.println(t1.getPriority());//获取线程优先级
        t1.setPriority(Thread.MAX_PRIORITY); //设置优先级1~10,仅是优先执行的概率较高
        //4.通过此对象调用start()，启动当前线程，调用当前线程的run()
        t1.start();
//        try {
//            t1.join(); //在一个线程A中调用另一个线程B的join方法（需要处理异常），线程A阻塞直至线程B执行完成
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        Thread1 t2 = new Thread1();
        t2.start();
        System.out.println(t1.isAlive());//判断线程是否还活着

        //方法二：
        //创建实现runnable接口的类的对象
        MThread mThread = new MThread();
        //将此对象作为参数传递到Thread类的构造器中，创建Thread类的对象
        Thread t3 = new Thread(mThread);
        t3.start();
        Thread t4 = new Thread(mThread);
        t4.start();

        //方法三：
        //创建实现Callable接口的类的对象
        NumThread numThread = new NumThread();
        FutureTask<Integer> futureTask = new FutureTask<Integer>(numThread);
        new Thread(futureTask).start();

        try {
            Integer sum = futureTask.get();//get()返回值既为FutureTask构造器函数Cakkabke实现类重写的call()的返回值
            System.out.println("result:" + sum);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        //方法四：
        //创建线程池
        //1.提供指定线程数量的线程池
        Executors.newCachedThreadPool(); //创建一个可根据需要创建新线程的线程池
        ExecutorService service = Executors.newFixedThreadPool(5); //创建一个可重用固定线程数的线程池（*）
        Executors.newSingleThreadExecutor(); //创建一个只有一个线程的线程池
        Executors.newScheduledThreadPool(5); //创建一个线程池，它可安排在给定延迟后运行命令或者定期地执行
        //设置线程池的属性
//        ThreadPoolExecutor service1 = (ThreadPoolExecutor) service;
//        service1.setCorePoolSize(15); //设置核心池的大小
//        service1.setMaximumPoolSize(20); //设置最大线程数
//        service1.setKeepAliveTime(); //线程没有任务是最多保持多长时间后会终止

        //2.指定现成的操作，开启并执行，需提供实现Runnable或Callable接口实现类的对象
        service.execute(new MThread());//开启并运行一个新线程（适合使用与Runnable）
//        service.submit(numThread);//开启并运行一个新线程（适合适用于Callable）
        service.shutdown(); //关闭线程池
    }
}

/*
方法一：继承Thread类 （受限于单继承）
 */
// 1. 写一个Thread的子类
class Thread1 extends Thread {
    //2.重写run方法
    @Override
    public void run() {
        super.run();
        for (int i = 1; i < 10000; i++) {
            System.out.println(Thread.currentThread().getName()); //获取当前线程名
            try {
                sleep(1000); //阻塞一秒，该时间内彻底把资源让给别的线程
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            yield(); //立即释放cpu执行权，但可能接下来这个线程又被cpu选上
        }
    }
}

/*
方法二：实现Runnable接口 （优先选择的方法，无单继承局限性，便于共享数据）
 */
class MThread implements Runnable {
    @Override
    public void run() {
        for (int i = 1; i < 10000; i++) {
            System.out.println(Thread.currentThread().getName());
        }
    }
}

/*
线程安全
解决方法优先顺序：Lock>同步代码块>同步方法
解决方法一：同步代码块 synchronized(锁(任何对象)){需要被同步的代码（操作共享数据的代码）}
实现接口的多线程
 */
class Window1 implements Runnable {
    private int ticket = 100;

    @Override
    public void run() {
        while (true) {
            synchronized (this) { //所有线程共享一个锁
                if (ticket > 0) {
                    //此体中出现阻塞会产生线程安全问题
                    System.out.println(Thread.currentThread().getName() + "sell ticket" + ticket);
                    ticket--;
                } else {
                    break;
                }
            }
        }
    }
}


/*
线程安全
解决方法一：同步代码块 synchronized(锁(任何对象)){需要被同步的代码（操作共享数据的代码）}
继承的多线程
 */
class Window2 extends Thread {
    private static int ticket = 100;

    @Override
    public void run() {
        while (true) {
            synchronized (Window2.class) {
                if (ticket > 0) {
                    //此体中出现阻塞会产生线程安全问题
                    System.out.println(Thread.currentThread().getName() + "sell ticket" + ticket);
                    ticket--;
                } else {
                    break;
                }
            }
        }
    }
}

/*
线程安全
解决方法二：如果操作共享数据的代码完整的声明在一个方法中，我们不妨将此方法声明为同步
实现接口的多线程
 */
class Window3 implements Runnable {
    private int ticket = 100;

    @Override
    public void run() {
        while (true) {
            show();
        }
    }

    private synchronized void show() { //隐式的同步监视器就是this
        if (ticket > 0) {
            //此体中出现阻塞会产生线程安全问题
            System.out.println(Thread.currentThread().getName() + "sell ticket" + ticket);
            ticket--;
        }
    }
}

/*
线程安全
解决方法二：如果操作共享数据的代码完整的声明在一个方法中，我们不妨将此方法声明为同步
继承的多线程
 */
class Window4 extends Thread {
    private static int ticket = 100;

    @Override
    public void run() {
        while (true) {
            show();
        }
    }

    private static synchronized void show() { //隐式的同步监视器就是Window4.class
        if (ticket > 0) {
            //此体中出现阻塞会产生线程安全问题
            System.out.println(Thread.currentThread().getName() + "sell ticket" + ticket);
            ticket--;
        }
    }
}

/* 死锁
不同的线程都需要对方手上的锁，不放弃
Thread-0:拿key_A -> 需要key_B
Thread-1:拿key_B -> 需要key_A
 */

/*
线程安全
解决方法三：Lock锁（JDK5.0+)
synchronized机制在执行完相应的同步代码以后，自动地释放同步监视器
lock需要手动启动同步，同时结束同步也需要手动实现
 */
class Window5 implements Runnable {
    private int ticket = 100;

    //1.实例化Reentrantlock
    private ReentrantLock lock = new ReentrantLock(); // new ReentrantLock(true) 公平锁

    @Override
    public void run() {
        while (true) {
            try {
                //2.调用锁定方法lock();
                lock.lock();
                if (ticket > 0) {
                    //此体中出现阻塞会产生线程安全问题
                    System.out.println(Thread.currentThread().getName() + "sell ticket" + ticket);
                    ticket--;
                } else {
                    break;
                }
            } finally {
                //3.调用解锁方法unlock();
                lock.unlock();
            }
        }
    }
}

/*线程通信
在同步代码块或同步方法中使用：(java.Object)
wait():一旦执行此方法，当前线程就进入阻塞状态，并释放同步监视器
notify():一旦执行此方法，就会唤醒被wait的一个线程，如果有多个线程被wait，就唤醒优先级高的那个
notifyAll():一旦执行此方法，就会唤醒所有被wait的线程
注：他们的调用者是同步代码块或同步方法中的同步监视器，否则会报异常
 */
class Number implements Runnable {
    private int number = 1;
    private Object obj = new Object();

    @Override
    public void run() {
        while (true) {
            synchronized (obj) {
                obj.notify(); //唤醒一个被wait的线程，这个线程也可能没有锁而不能马上执行
                if (number <= 100) {
                    System.out.println(Thread.currentThread().getName() + ":" + number);
                    number++;

                    try {
                        Thread.sleep(10); // sleep阻塞不会让出锁
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    try {
                        obj.wait(); // wait阻塞会让出锁
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    break;
                }
            }
        }
    }
}

/* 新增线程创建方式（JDK5.0+）
1.实现Callable接口
    可以有返回值，可以报出异常，支持泛型的返回值，需要借助FutureTask类
2.使用线程池
    提前创建好多个线程，放入线程池中，可以避免频繁创建和销毁
 */
//方式三：实现Callable接口
class NumThread implements Callable<Integer> {
    @Override
    public Integer call() throws Exception {
        int sum = 0;
        for (int i = 1; i <= 100; i++) {
            if (i % 2 == 0) {
                System.out.println(i);
                sum += i;
            }
        }
        return sum;
    }
}
//方法四：线程池
