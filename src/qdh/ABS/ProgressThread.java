package qdh.ABS;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2017/9/28.
 */
public class ProgressThread extends Thread {
    public static final int PROGRESS_WIDTH = 350;
    private static final int CATCH_UP_MULTIPLE = 25;
    private final JProgressBar progressBar;
    private final CyclicBarrier barrier;
    private final int slowness;

    public ProgressThread(CyclicBarrier barrier, int slowness) {
        this.barrier = barrier;
        this.slowness = slowness;
        progressBar = new JProgressBar();
        progressBar.setPreferredSize(
                new Dimension(PROGRESS_WIDTH - 30, 25));
        progressBar.setStringPainted(true);
    }

    JComponent getProgressComponent() {
        return progressBar;
    }

    @Override
    public void run() {

        int c = 0;
        boolean reversed = false;
        while (true) {
            progressBar.setValue(reversed ? --c : ++c);
            if (c == 100) {
                reversed = true;
            } else if (c == 0) {
                reversed = false;
            }

            try {
                Thread.sleep(slowness);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (c % CATCH_UP_MULTIPLE == 0) {//if c is multiple of CATCH_UP_MULTIPLE
                try {
                    /**
                     * This is the barrier point.
                     * On calling await() method, the current thread (party)
                     * will wait until all threads (parties) have called this
                     * method. At this method call
                     * barrier.getNumberWaiting() will increase by one.*/
//                    barrier.await(10,TimeUnit.SECONDS ); //用来测试BrokenBarrierException
                    barrier.await();
                    //at this point no threads will be waiting and
                    // barrier.getNumberWaiting() will be zero
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}