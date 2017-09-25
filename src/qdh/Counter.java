package qdh;

/**
 * Created by Administrator on 2017/9/25.
 */

public class Counter {

    long count = 0;


    public synchronized void add(long value) {

        this.count = this.count + value;
    }
    public static void main(String[] args) {

        Counter counter = new Counter();

          Thread threadA =  counter.new CounterThread(counter);  //创建内部类
        Thread threadB =  counter.new CounterThread(counter);
//         Thread threadB = new CounterThread(counter);    //只有CounterThread是静态的时候才能这么用
        //一般都将内部类声明为static
        threadA.start();

        threadB.start();
//       Counter.CounterThread abc=new Counter.CounterThread()  静态内部类的创建
        // 内部类的声明要先声明外部类

    }




    public    class   CounterThread extends Thread {
        protected Counter counter = null;

        public CounterThread(Counter counter) {
            this.counter = counter;

        }

        public void run() {
            for (int i = 0; i < 10; i++) {
                counter.add(i);
            }
        }

    }


}
