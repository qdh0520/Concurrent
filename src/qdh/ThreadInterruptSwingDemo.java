package qdh;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2017/9/24.
 */
public class ThreadInterruptSwingDemo {
    public static void main (String[] args) {
        JFrame frame = new JFrame("Interrupt Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(500, 500));
        frame.add(createAnimationContainer());
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static Component createAnimationContainer () {
        JPanel panel = new JPanel(new BorderLayout());
        AnimatedPanel animatedPanel = new AnimatedPanel();
        panel.add(animatedPanel);
        JButton button = new JButton("Reverse");
        panel.add(button, BorderLayout.SOUTH);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                animatedPanel.getThread().interrupt();

            }
        });
        return panel;
    }

    private static class AnimatedPanel extends JComponent {
        private int angle = 0;
        boolean clockwise = true;
        private Thread thread;

        AnimatedPanel () {
            startAnimation();
        }

        private void startAnimation () {
            thread = new Thread(() -> {
                while (true) {
                    angle++;
                    if (angle >= 360) {
                        angle = 0;
                    }
                    if(thread.isInterrupted()){
                        clockwise = !clockwise;
                    }
                    //no need to create EDT
                    repaint();
                    try {
                        TimeUnit.MILLISECONDS.sleep(10);
                    } catch (InterruptedException e) {
                        clockwise = !clockwise;

                    }
                }
            });
            thread.start();
        }

        @Override
        public void paint (Graphics g) {
            g.setColor(Color.MAGENTA);
            g.fillArc(10, 10, 400, 400, clockwise? -angle: angle, 30 );
        }

        public Thread getThread () {
            return thread;
        }
    }
}