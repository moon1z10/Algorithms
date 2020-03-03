package น้มุ;

import java.util.Arrays;
import java.util.Scanner;

public class LIS2352 {
	
	static int N;
	static int[] a, dp;

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		N = sc.nextInt();
		a = new int[N+1];
		dp = new int[N+1];
		for (int i=1; i<=N; i++) {
			a[i] = sc.nextInt();
		}
		
		int res = Lis();
		
		System.out.println(res);
		
		sc.close();
	}

	private static int Lis() {
		int max = 1;
		Arrays.fill(dp, 1);
		
		for (int i=2; i<=N; i++) {
			for (int j=1; j<i; j++) {
				if (a[i] > a[j] && dp[j] + 1 > dp[i])
					dp[i] = dp[j] + 1;
			}
			if (max < dp[i])
				max = dp[i];
		}
		
		return max;
	}
}
