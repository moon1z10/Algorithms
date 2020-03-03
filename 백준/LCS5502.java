package น้มุ;

import java.util.Arrays;
import java.util.Scanner;

public class LCS5502 {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		int N = sc.nextInt();
		sc.nextLine();
		String s = sc.nextLine();
		char[] c = new char[N];
		for (int i=0, j=N-1; i<N; i++, j--)
			c[i] = s.charAt(j);
		String s2 = new String(c);
		
		int[][] d = new int[N+1][N+1];
		Arrays.fill(d[0], 0);
		for (int i=1; i<=N; i++) {
			d[i][0] = 0;
			for (int j=1; j<=N; j++) {
				if (s2.charAt(i-1) == s.charAt(j-1))
					d[i][j] = d[i-1][j-1] + 1;
				else
					d[i][j] = Math.max(d[i-1][j], d[i][j-1]);
			}
		}
		
		System.out.println(N-d[N][N]);
		
		sc.close();
	}

}
