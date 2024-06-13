import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Main {
    static final int MAX_NUM_OF_ARRAY = 20;
    static final int GIVEN_ARRAY_LENGTH = 6;

    public static void main(String[] args) {
        // Generate Random Array
        List<Integer> list = new ArrayList<>();
        for (int i = 1; i <= MAX_NUM_OF_ARRAY; i++) {
            list.add(i);
        }
        Collections.shuffle(list);

        Integer[] array = new Integer[GIVEN_ARRAY_LENGTH];
        for (int i = 0; i < GIVEN_ARRAY_LENGTH; i++) {
            array[i] = list.get(i);
        }

        System.out.println("Given Array: " + Arrays.toString(array) + "\n");

        QuickSort<Integer> qs = new QuickSort<>();

        Integer[] l_arr = Arrays.copyOf(array, array.length);
        qs.sort(l_arr, QUICSORT_PIVOT_SELECT.LEFT_PIVOT);
        System.out.println("(Left QuickSort) Sorted Array: " + Arrays.toString(l_arr));

        Integer[] r_arr = Arrays.copyOf(array, array.length);
        qs.sort(r_arr, QUICSORT_PIVOT_SELECT.RIGHT_PIVOT);
        System.out.println("(Right QuickSort) Sorted Array: " + Arrays.toString(r_arr));

        Integer[] m_arr = Arrays.copyOf(array, array.length);
        qs.sort(m_arr, QUICSORT_PIVOT_SELECT.MIDDLE_PIVOT);
        System.out.println("(Middle QuickSort) Sorted Array: " + Arrays.toString(m_arr));
    }
}