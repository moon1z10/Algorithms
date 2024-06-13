import com.sun.source.tree.Tree;

import java.util.*;

public class ArrayCopyTest {
    public static void main(String[] args) throws InterruptedException {
        Map<Integer, Double> data = new TreeMap<>();

        int repeat = 1;
        for (int i = 10; i <= 100000000; i *= 10) {
            double sum = 0;
            for (int j = 0; j < repeat; j++) {
                double result = test(i);
                sum += result;
            }
            double avg = sum / repeat;
            data.put(i, avg);
        }
        for (Map.Entry<Integer, Double> entry : data.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }

    private static double test(int size) {
        int[] src = new int[size];
        for (int i = 0; i < src.length; i++) {
            src[i] = i;
        }

        int[] dest = new int[src.length];

        // Measure time for for loop copy
        long startTime = System.nanoTime();
        for (int i = 0; i < src.length; i++) {
            dest[i] = src[i];
        }
        long endTime = System.nanoTime();
        long durationForLoop = endTime - startTime;
//        System.out.println("For loop copy duration: " + durationForLoop + " ns");
        System.gc(); // 가비지 콜렉터 호출해서 힙 메모리 확보

        // Measure time for System.arraycopy
        startTime = System.nanoTime();
        System.arraycopy(src, 0, dest, 0, src.length);
        endTime = System.nanoTime();
        long durationSystemArrayCopy = endTime - startTime;
//        System.out.println("System.arraycopy duration: " + durationSystemArrayCopy + " ns");
        System.gc(); // 가비지 콜렉터 호출해서 힙 메모리 확보

        // Compare the results
        double result = ((double)durationForLoop / durationSystemArrayCopy);
//        System.out.println("System.arraycopy is " + result + " times faster than for loop.");
        return result;
    }
}