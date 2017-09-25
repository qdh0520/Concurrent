package qdh;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

/**
 * Intrinsic lock is on object, not on a method
 * As mentioned before, if a thread has acquired the lock,
 * other threads will be blocked even if they are calling other 'synchronized' methods of the same object.
 * Non-synchronized methods won't be blocked.
 */
public class MultipleSyncMethodsDemo {

    public static void main (String[] args) throws InterruptedException {
        MultipleSyncMethodsDemo demo = new MultipleSyncMethodsDemo();
        Thread thread1 = new Thread(() -> {
            System.out.println("thread1 before call "+ LocalDateTime.now());
            demo.syncMethod1("from thread1");
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

    private synchronized void syncMethod1 (String msg) {
        System.out.println("in the syncMethod1 "+msg+" "+LocalDateTime.now());
        try {
            TimeUnit.SECONDS.sleep(5);
//            System.out.println("sleep "+msg+LocalDateTime.now());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private synchronized void syncMethod2 (String msg) {
        System.out.println("in the syncMethod2 "+msg+" "+ LocalDateTime.now());
        try {
            TimeUnit.SECONDS.sleep(5);
            System.out.println("sleep "+msg+LocalDateTime.now());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
//    thread1 before call 2017-09-25T13:30:15.524
//        thread2 before call 2017-09-25T13:30:15.524
//        in the syncMethod1 from thread1 2017-09-25T13:30:15.527
//        in the syncMethod2 from thread2 2017-09-25T13:30:20.529
//        thread1 after call 2017-09-25T13:30:20.529
//        sleep from thread22017-09-25T13:30:25.529
//        thread2 after call 2017-09-25T13:30:25.529
//   syncMethod2 sleep 的时候没有释放对象的锁 ，但thread1 after call 2017-09-25T13:30:20.529
//    是非同步块里的语句所以可以在未释放对象锁的时候先运行不会阻塞
// 这里的写法容易引起歧义 最好使用synchronized （this）{}语句块 在语句块里的代码会阻塞
