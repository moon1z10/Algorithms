package น้มุ;

import java.util.Arrays;
import java.util.Scanner;

public class LIS2565 {
	
	static int N;
	static int[] a;
	static int[] dp;

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		N = sc.nextInt();
		a = new int[501];
		dp = new int[501];
		Arrays.fill(dp, 1);
		
		for (int i=0; i<N; i++) {
			a[sc.nextInt()] = sc.nextInt();
		}
		
		int res = Lis();
		
		System.out.println(N - res);
		
		sc.close();
	}

	private static int Lis() {
		int max = 1;
		for (int i=2; i<=500; i++) {
			dp[i] = 1;
			for (int j=1; j<i; j++) {
				if (a[j] == 0) continue;
				if (a[i] > a[j] && dp[j] + 1 > dp[i])
					dp[i] = dp[j] + 1;
			}
			if (max < dp[i])
				max = dp[i];
		}
		return max;
	}

}
