package qdh;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

/**
 *
 * Intrinsic lock is on object, not on a method
 As mentioned before, if a thread has acquired the lock,
 other threads will be blocked even if they are calling other 'synchronized' methods of the same object.
 Non-synchronized methods won't be blocked.
 *
 * 不写在run方法里，使用synchronized
 */
public class ThreadLockDemo {

    public static void main (String[] args) throws InterruptedException {
        ThreadLockDemo demo = new ThreadLockDemo();
        Thread thread1 = new Thread(() -> {
            System.out.println("thread1 before call "+ LocalDateTime.now());
            demo.syncMethod("from thread1");
            //下面这一句不是同步块里的代码，执行顺序不一定紧跟在上一句之后。可能会插入线程2中的语句
            System.out.println("thread1 after call "+LocalDateTime.now());
        });
        Thread thread2 = new Thread(() -> {
            System.out.println("thread2 before call "+LocalDateTime.now());
            demo.syncMethod2("from thread2");
            System.out.println("thread2 after call "+LocalDateTime.now());
        });

        thread1.start();
        thread2.start();
    }

    private synchronized void syncMethod (String msg) {
        System.out.println("in the sync method "+msg+" "+ LocalDateTime.now());

        try {
            TimeUnit.SECONDS.sleep(5);
            System.out.println(msg+" after in call "+LocalDateTime.now());
        } catch (InterruptedException e) {
           e.printStackTrace();
        }
    }
    private synchronized void syncMethod2 (String msg) {
        System.out.println("in the syncMethod2 "+msg+" "+LocalDateTime.now());
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
//
//thread1 before call 2017-09-25T11:07:28.258
//thread2 before call 2017-09-25T11:07:28.258
// in the sync method from thread1 2017-09-25T11:07:28.260
// thread1 after call 2017-09-25T11:07:33.263
//    in the sync method from thread2 2017-09-25T11:07:33.263
//  thread2 after call 2017-09-25T11:07:38.264

//    thread1 before call 2017-09-25T12:13:20.920
//        thread2 before call 2017-09-25T12:13:20.920
//        in the sync method from thread1 2017-09-25T12:13:20.922
//        from thread1 after in call 2017-09-25T12:13:25.925
//        in the sync method from thread2 2017-09-25T12:13:25.926
//        thread1 after call 2017-09-25T12:13:25.926
//        from thread2 after in call 2017-09-25T12:13:30.927
//        thread2 after call 2017-09-25T12:13:30.927
