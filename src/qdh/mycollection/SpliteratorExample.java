package qdh.mycollection;

/**
 * Created by Administrator on 2017/10/11.
 */
import java.util.Arrays;
import java.util.Spliterator;

public class SpliteratorExample {

    public static void main(String... args) {
        int[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 9,10,11};
        Spliterator<Integer> s1 = Arrays.spliterator(arr);
        Spliterator<Integer> s2 = s1.trySplit();
        System.out.println("spliterator 1");
        s1.forEachRemaining(System.out::println);
        System.out.println("spliterator 2");
        s2.forEachRemaining(System.out::println);

        // Spliterator分成两半 先返回后一半
    }
}