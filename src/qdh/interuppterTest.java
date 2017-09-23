package qdh;

/**
 * Created by Administrator on 2017/9/23.
 *
 * https://praveer09.github.io/technology/2015/12/06/understanding-thread-interruption-in-java/
 */
public class interuppterTest {
    public static void main(String[] args) throws InterruptedException {
        Thread taskThread = new Thread(taskThatFinishesEarlyOnInterruption());
        Thread task=new Thread(task2());
        taskThread.start();      // requirement 3
        task.start();
        Thread.sleep(3_000);     // requirement 4
        task.join(3_000);
//       taskThread.interrupt();  // requirement 5
//        taskThread.join(10_000);  // requirement 6
    }

    private static Runnable taskThatFinishesEarlyOnInterruption() {
        return () -> {
            for (int i = 0; i < 10; i++) {
                System.out.print(i);      // requirement 1
                try {
                    Thread.sleep(1_000);  // requirement 2
                } catch (InterruptedException e) {
//                    break;                // requirement 7
                    System.out.print(i);
                }
            }
        };
    }


    private static Runnable  task2(){
        return () -> {
            for (int i = 0; i < 4; i++) {
                System.out.print("#");      // requirement 1
                try {
                    Thread.sleep(1_000);  // requirement 2
                } catch (InterruptedException e) {
//                    break;                // requirement 7
                    System.out.print("￥");
                }
            }
        };


    }
}
