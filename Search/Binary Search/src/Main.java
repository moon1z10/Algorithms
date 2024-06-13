import java.util.*;

class Solution {
    public int solution(int x, int[] arr, boolean sorted) {
        int bigOCount = 0;
        int max = Integer.MIN_VALUE, min = Integer.MAX_VALUE;
        int result = -1;

        // If array is sorted, we can use Binary Search
        // But if it's not sorted, let's apply O( N )
        if (sorted) {
            int l = 0, r = arr.length - 1;

            // if search item(x) is out of array, let's compare with the first, last in array and return it.
            if (x < arr[l] || arr[r] < x) {
                System.out.println("  > N : " + arr.length + ", O(" + bigOCount + ")");
                return -1;
            }

            // if it's sorted, use 'Binary Search' which will take O( log N )
            while (l <= r) {
                bigOCount++;
                int mid = l + (r - l) / 2;
                if (arr[mid] == x) {
                    System.out.println("  > N : " + arr.length + ", O(" + bigOCount + ")");
                    return x;
                } else if (arr[mid] < x) {
                    l = mid + 1;
                } else {
                    r = mid - 1;
                }
            }

            if (0 < r && r < arr.length) {
                result = arr[r];
            }

            System.out.println("  > N : " + arr.length + ", O(" + bigOCount + ")");
            return result;
        } else {
            // the given array isn't sorted, then iterate all elements and find
            for (int j : arr) {
                bigOCount++;
                if (j == x) {
                    System.out.println("  > N : " + arr.length + ", O(" + bigOCount + ")");
                    return x;
                } else if (j < x) {
                    result = Math.max(result, j);
                }
                max = Math.max(max, j);
                min = Math.min(min, j);
            }

            System.out.println("  > N : " + arr.length + ", O(" + bigOCount + ")");
            return (max < x || x < min) ? -1 : result;
        }
    }
}

/**
 * Question : Let give x (searching item) and array, array may be sorted or not.
 * - If x is in array, return x,
 * - If x is not in array, return the biggest number which is smaller than x
 * - if x is out of array, return -1
 */
public class Main {
    public static void main(String[] args) {
        Solution s = new Solution();

        boolean sorted = true;
        int x = 19;
        int[] arr = new int[10];

        // create random number to insert
        List<Integer> nums = new ArrayList<>();
        for (int i = 0; i <= 20; i++) {
            nums.add(i);
        }
        Collections.shuffle(nums);

        // generate test case array
        for (int i = 0; i < arr.length; i++) {
            arr[i] = nums.get(i);
        }
        if (sorted) Arrays.sort(arr);

        System.out.println("x : " + x + ", arr : " + Arrays.toString(arr));
        System.out.println(s.solution(x, arr, sorted));
    }
}
