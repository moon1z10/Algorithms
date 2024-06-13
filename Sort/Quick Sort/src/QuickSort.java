import java.util.Arrays;

public class QuickSort<T extends Comparable<T>> {
    int N;
    int bigOCount = 0;

    public void sort(T[] array, QUICSORT_PIVOT_SELECT pivotSelect) {
        N = array.length;
        bigOCount = 0;

        switch (pivotSelect) {
            case LEFT_PIVOT -> l_quickSort(array, 0, array.length - 1);
            case RIGHT_PIVOT -> r_quickSort(array, 0, array.length - 1);
            case MIDDLE_PIVOT -> m_quickSort(array, 0, array.length - 1);
        }

        printTimeComplexity();
    }

    void l_quickSort(T[] array, int left, int right) {
        if (left >= right) return;

//        System.out.println("> {l_quickSort - start} left : " + left + ", right : " + right +", " + Arrays.toString(array));

        // partitioning
        int low = left + 1, high = right, pivot = left;
        T pivotValue = array[pivot];
        while (low <= high) {
            while (low <= high && array[low].compareTo(pivotValue) < 0) {
                low++;
                bigOCount++;
            }

            while (low <= high && array[high].compareTo(pivotValue) > 0) {
                high--;
                bigOCount++;
            }

            if (low <= high) { // Added this check to ensure valid swap
                swap(array, low, high);
                low++;
                high--;
            }

            // swap
//            System.out.println("  > {Swapping in a partition} low : " + low + ", high : " + high +", pivot : " + pivot +", " + Arrays.toString(array));
        }
        swap(array, pivot, high); // swap - pivot(left) <> last index of partition
//        System.out.println(" > {End of partition} low : " + low + ", high : " + high +", pivot : " + pivot +", " + Arrays.toString(array));

        l_quickSort(array, left, high - 1);
        l_quickSort(array, high + 1, right);
    }

    void r_quickSort(T[] array, int left, int right) {
        if (left >= right) return;

        int low = left, high = right - 1, pivot = right;
        T pivotValue = array[pivot];

        while (low <= high) {
            while (low <= high && array[low].compareTo(pivotValue) < 0) {
                low++;
                bigOCount++;
            }

            while (low <= high && array[high].compareTo(pivotValue) > 0) {
                high--;
                bigOCount++;
            }

            if (low <= high) {
                swap(array, low, high);
                low++;
                high--;
            }
        }

        swap(array, pivot, low);

        r_quickSort(array, left, low - 1);
        r_quickSort(array, low + 1, right);
    }

    void m_quickSort(T[] array, int left, int right) {
        if (left >= right) return;
        int low = left, high = right, pivot = (left + right) / 2;
        T pivotValue = array[pivot];

        while (low <= high) {
            while (low <= high && array[low].compareTo(pivotValue) < 0) {
                low++;
                bigOCount++;
            }

            while (low <= high && array[high].compareTo(pivotValue) > 0) {
                high--;
                bigOCount++;
            }

            if (low <= high) {
                swap(array, low, high);

                if (low == pivot) {
                    pivot = high;
                } else if (high == pivot) {
                    pivot = low;
                }
                low++;
                high--;
            }
        }

        m_quickSort(array, left, high);
        m_quickSort(array, low, right);
    }

    void swap(T[] array, int i, int j) {
        T temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    public void printTimeComplexity() {
        // Print actual vs expected time complexity
        int expected = (int) (N * Math.log(N) / Math.log(2));  // N * log2(N)
        System.out.println("\n=================================");
        System.out.println("Actual count: " + bigOCount);
        System.out.println("Expected (NlogN): " + expected);
        System.out.println("Difference: " + Math.abs(bigOCount - expected));
        System.out.println("=================================");
    }
}
