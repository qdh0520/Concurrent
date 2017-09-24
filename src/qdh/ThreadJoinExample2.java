package qdh;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2017/9/23.
 */
public class ThreadJoinExample2 {
    public static void main (String[] args) {
        final List<Integer> integers = Arrays.asList(10, 12, 13, 14, 15, 20);

        new Thread(new Runnable() {//thread A
            @Override
            public void run () {
                List<FactorialCalculator> threads = new ArrayList<>();
                for (Integer integer : integers) {
                    FactorialCalculator calc = new FactorialCalculator(integer);
                    threads.add(calc);
                 calc.start();
                }
                for (FactorialCalculator calc : threads) {
                  try {
                       calc.join();  //不加join 不能确保阶乘计算完再输出
                        System.out.println(calc.getNumber() + "! = "
                                + calc.getFactorial());//这是A线程语句输出的时候，calc线程的阶乘结果可能还没有算好

                  } catch (InterruptedException e) {
                        e.printStackTrace();
                   }
              }
            }
        }).start();
    }

    private static class FactorialCalculator extends Thread {

        private final int number;
        private BigDecimal factorial= BigDecimal.valueOf(3628801);

        FactorialCalculator (int number) {
            this.number = number;
        }

        @Override
        public void run () {
            factorial = calculateFactorial(number);
        }

        private static BigDecimal calculateFactorial (int number) {
            BigDecimal factorial = BigDecimal.ONE;
            for (int i = 1; i <= number; i++) {
                factorial = factorial.multiply(new BigDecimal(i));
            }
            return factorial;
        }

        public BigDecimal getFactorial () {
            return factorial;
        }

        public int getNumber () {
            return number;
        }
    }
}
