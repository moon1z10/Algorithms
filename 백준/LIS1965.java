package ����;
import java.util.Arrays;
import java.util.Scanner;

public class LIS1965 {
	static int N;
	static int[] a;
	static int[] dp;

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		N = sc.nextInt();
		a = new int[N+1];
		dp = new int[N + 1];
		for (int i = 1; i <= N; i++) {
			a[i] = sc.nextInt();
		}

		int ans = Lis();

		System.out.println(ans);

		sc.close();
	}

	private static int Lis() {
		Arrays.fill(dp, 1);
		int max = 1;
		for (int i = 2; i <= N; i++) {

			// �ش� ���ں��� �� �ʿ� ��ġ�ϰ� ���� ���� ���� ã�´�.
			for (int j = 1; j < i; j++) {

				if (a[i] > a[j] && dp[j] + 1 > dp[i]) {
					dp[i] = dp[j] + 1;
				}
			}
			
			if (max < dp[i]) {
				max = dp[i];
			}
		}
		return max;
	}

}
