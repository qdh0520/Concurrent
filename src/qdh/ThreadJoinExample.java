package qdh;

/**
 * Created by Administrator on 2017/9/23.
 */
public class ThreadJoinExample {
    public static void main (String[] args) throws InterruptedException {
        Task task1 = new Task();
        Thread thread1 = new Thread(task1);
        thread1.start();
//        thread1.join();//here the main thread will wait until thread1 fishes.
//        System.out.println("after join");

        Task task2 = new Task();  //Note Thread-1 starts only after Thread-0 terminates.
        // 如果去掉thread1.join();task 与task2的启动是随机的
        new Thread(task2).start();
    }

    private static class Task implements Runnable {

        @Override
        public void run () {
            int c = 0;
            String threadName = Thread.currentThread()
                    .getName();

            System.out.println(threadName + " started.");
            for (int i = 0; i < 1000; i++) {
                c+=i;
            }

            System.out.println(threadName + " ended.");
        }
    }
}
