package qdh.ForkJoin;

import java.util.concurrent.RecursiveTask;

/**
 * Created by Administrator on 2017/10/10.
 */
public class ParallelMax {

    public static void main(String[] args) {

    }

    public static  int Max(int[] list){
        return 1;
    }
    private static class MaxTask extends RecursiveTask<Integer> {
        private final static int THRESHOLD = 1000;
        private int[] list;
        private int low;
        private int high;

        public MaxTask(int[] list, int low, int high) {
            this.list = list;
            this.low = low;
            this.high = high;

        }


        @Override
        protected Integer compute() {

            if (high - low < THRESHOLD) {
                int max = list[0];
                for (int i = low; i < high; i++) {
                  if(list[i]>max){
                      max=list[i];

                  }
                }
                return max;
            } else {
                int mid=(low+high)/2;
                RecursiveTask<Integer> left =new MaxTask(list,low,mid);
                RecursiveTask<Integer> right =new MaxTask(list,mid,high);
                left.fork();
                right.fork();
                return Math.max(left.join(), right.join());
            }
        }
    }
}
