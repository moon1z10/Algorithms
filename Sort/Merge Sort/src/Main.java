import java.util.*;

public class Main {
    static int GIVEN_ARRAY_LENGTH = 6;
    static int MAX_NUM_OF_ARRAY = 20;
    static int bigOCount = 0;

    public static void main(String[] args) {
        // Generate Random Array
        List<Integer> list = new ArrayList<>();
        for (int i = 1; i < MAX_NUM_OF_ARRAY; i++) {
            list.add(i);
        }
        Collections.shuffle(list);

        int[] array = new int[GIVEN_ARRAY_LENGTH];
        for (int i = 0; i < GIVEN_ARRAY_LENGTH; i++) {
            array[i] = list.get(i);
        }

        System.out.println("Given Array: " + Arrays.toString(array) + "\n");

        mergeSort(array, 0, array.length - 1);

        System.out.println("Sorted Array: " + Arrays.toString(array));
        System.out.println("  > N : " + array.length + ", O(" + bigOCount + ")");

        // Print actual vs expected time complexity
        int N = array.length;
        int expected = (int) (N * Math.log(N) / Math.log(2));  // N * log2(N)
        System.out.println("\nActual count: " + bigOCount);
        System.out.println("Expected (NlogN): " + expected);
        System.out.println("Difference: " + Math.abs(bigOCount - expected));
    }

    private static void mergeSort(int[] array, int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;
            // Left Divide
            mergeSort(array, left, mid);
            // Right Divide
            mergeSort(array, mid + 1, right);

            // Merge
            merge(array, left, mid, right);
//            mergeInPlace(array, left, mid, right);
        }
    }

    // 공간 복잡도 : O(n) : 병합 과정에서 두 개의 보조 배열(leftArray, rightArray)을 사용하여 데이터를 임시로 저장. 최대 n/2개 배열 두 개가 필요하다.
    static void merge(int[] array, int left, int middle, int right) {
        int n1 = middle - left + 1;
        int n2 = right - middle;

        int[] leftArray = new int[n1];
        int[] rightArray = new int[n2];

        // copy elements
        for (int i = 0; i < n1; i++) {
            leftArray[i] = array[left + i];
        }
        for (int j = 0; j < n2; j++) {
            rightArray[j] = array[middle + 1 + j];
        }

        // 현재 상태 출력
        System.out.println(" > {Merging} left(" + left + "): " + Arrays.toString(leftArray) + " right(" + right + "): " + Arrays.toString(rightArray));

        int i = 0, j = 0, k = left;

        while (i < n1 && j < n2) {
            array[k++] = (leftArray[i] <= rightArray[j]) ? leftArray[i++] : rightArray[j++];
            bigOCount++;
        }

        while (i < n1) {
            array[k++] = leftArray[i++];
            bigOCount++;
        }
        while (j < n2) {
            array[k++] = rightArray[j++];
            bigOCount++;
        }

        // merge 결과 출력
        System.out.println("  > {Merged} " + Arrays.toString(Arrays.copyOfRange(array, left, right + 1)));
    }

    // 공간 복잡도 : O(1), 구현의 난이도 상승
    static void mergeInPlace(int[] array, int left, int middle, int right) {
        int start2 = middle + 1;

        if (array[middle] <= array[start2]) {
            return;
        }

        // 현재 상태 출력
        System.out.println(" > {Merging} left : " + left + ", middle : " + middle +", right : " + right +", " + Arrays.toString(array));

        while (left <= middle && start2 <= right) {
            // If element 1 is in right place
            if (array[left] <= array[start2]) {
                left++;
            } else {
                int value = array[start2];
                int index = start2;

                // Shift all the elements between element 1
                // and element 2, right by 1.
                while (index != left) {
                    array[index] = array[index - 1];
                    index--;
                    bigOCount++;
                }
                array[left] = value;

                // Update all the pointers
                left++;
                middle++;
                start2++;
            }
            bigOCount++;
        }

        // merge 결과 출력
        System.out.println("  > {Merged} " + Arrays.toString(array));
    }
}
