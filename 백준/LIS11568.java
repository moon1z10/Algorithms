package น้มุ;

import java.util.Arrays;
import java.util.Scanner;

public class LIS11568 {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		int N = sc.nextInt();
		int[] a = new int[N];
		int[] answerList = new int[N];
		int count = 0;
		for (int i=0; i<N; i++) {
			a[i] = sc.nextInt();
			int index = Arrays.binarySearch(answerList, 0, count, a[i]);
			if (index >= 0) {
				answerList[index] = a[i];
			} else {
				index *= -1;
				if (index > count)
					count++;
				answerList[--index] = a[i];
			}
		}
		
		System.out.println(count);
		
		sc.close();
	}

}
