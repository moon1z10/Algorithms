package น้มุ;

import java.util.Scanner;

public class LIS1965_2 {
	static int N;
	static int[] a;

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		N = sc.nextInt();
		a = new int[N + 1];
		for (int i = 1; i <= N; i++) {
			a[i] = sc.nextInt();
		}

		int ans = Lis();

		System.out.println(ans);

		sc.close();
	}

	private static int Lis() {
		int[] M = new int[N + 1];

		int length = 0;

		M[1] = a[1];
		++length;

		for (int i = 2; i <= N; i++) {
			int index = lowerBound(1, length, a[i]+1, M);

			if (index > length)
				M[++length] = a[i];
			else
				M[index] = a[i];
		}

		return length;
	}

	private static int lowerBound(int start, int end, int key, int[] M) {
		int low = start;
		int high = end;
		int index = 0;

		while (low <= high) {
			int middle = (low + high) / 2;

			if (M[middle] <= key) {
				index = middle;
				low = middle + 1;
			} else
				high = middle - 1;
		}

		return index + 1;
	}

}
