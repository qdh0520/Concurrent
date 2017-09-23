package qdh;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2017/9/23.
 *
 * InterruptedException and interruption status
 Before I finish, I wanted to emphasize on an important detail about what happens to
 a thread’s interruption status when a blocking code responds to interruption
 by throwing InterruptedException. I had left out the detail till now to avoid confusion.

 Before a blocking code throws an InterruptedException,
 it marks the interruption status as false.
 Thus, when handling of the InterruptedException is done,
 you should also preserve the interruption status by calling Thread.currentThread().interrupt().
 Let’s see how this information applies to the example below.
 In the task that is submitted to the ExecutorService, the printNumbers() method is called twice.
 When the task is interrupted by a call to shutdownNow(), the first call to the method finishes early
 and then the execution reaches the second call.
 The interruption is called by the main thread only once.
 The interruption is communicated to the second execution of the printNumber() method by the call to Thread.currentThread().interrupt() during the first execution. Hence the second execution also finishes early just after printing the first number.
 Not preserving the interruption status would have caused the second execution of the method to run fully for 9 seconds.
 *
 */
public class interruptionStatus {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<?> future = executor.submit(() -> {
            printNumbers(); // first call
            printNumbers(); // second call
        });
        Thread.sleep(3_000);
        executor.shutdownNow();  // will interrupt the task
        executor.awaitTermination(3, TimeUnit.SECONDS);
    }

    private static void printNumbers() {
        for (int i = 0; i < 10; i++) {
            System.out.print(i);
            try {
                Thread.sleep(1_000);
            } catch (InterruptedException e) {
             Thread.currentThread().interrupt(); // preserve interruption status
                break;
            }
        }
    }
}
