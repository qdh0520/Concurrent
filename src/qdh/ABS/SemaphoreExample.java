package qdh.ABS;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * Created by qdh on 2017/9/27.
 */
public class SemaphoreExample {
    private static final int WORKER_COUNT = 6;
    private static final int PERMITS = 2;

    public static void main(String[] args) throws Exception {
        List<WorkerPanel> workers = initPanels();
        Collections.shuffle(workers);
        Semaphore semaphore = new Semaphore(PERMITS);

        ExecutorService es = Executors.newFixedThreadPool(WORKER_COUNT);
        while (true) {
            for (WorkerPanel worker : workers) {
                //it will block until a permit is available
                semaphore.acquire();
                es.execute(() -> {
                    worker.work();
                    semaphore.release();
                });
            }
        }
    }

    private static List<WorkerPanel> initPanels() {
        JFrame frame = new JFrame("Permits: " + PERMITS);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(250, 200);
        frame.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        List<WorkerPanel> panels = new ArrayList<>();
        for (int i = 0; i < WORKER_COUNT; i++) {
            WorkerPanel wp = new WorkerPanel();
            frame.add(wp);
            panels.add(wp);
        }

        frame.setLocationRelativeTo(null);  //使窗口显示在屏幕正中心
        frame.setVisible(true);
        return panels;
    }
}