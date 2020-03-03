package น้มุ;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class LIS2568 {
	public static void main(String[] args) throws NumberFormatException, IOException {
		FastScanner sc = new FastScanner();
		int K = sc.nextInt();
		int[] a = new int[500000+1];
		int[] L = new int[500000+1];
		int[] p = new int[500000+1];
		int count = 0;
		int N = 0;
		for(int i = 0; i<K; i++){
			int n1 = sc.nextInt(), n2 = sc.nextInt();
			a[n1] = n2;
			N = Math.max(N, Math.max(n1, n2));
		}
		for (int i=1; i<=N; i++) {
			if (a[i] == 0) continue;
			int index = Arrays.binarySearch(L, 0, count, a[i]);
			if(index>=0){
				L[index] = a[i];
			}else{
				index = Math.abs(index);
				if(index>count){
					count++;
				}
				index--;
				L[index] = a[i];
			}
			p[i] = index;
		}
		System.out.println(K-count);
		backtrace(a, p, N, count);
	}

	private static void backtrace(int[] a, int[] p, int N, int c) {
		int lis_count = c;
		int[] answer = new int[c];
		c--;
		int index = c;
		for (int i = N; i>0; i--) {
			if (a[i] == 0) continue;
			if (p[i] == c) {
				answer[index--] = i;
				c--;
			}
			if (c < 0) break;
		}
		for (int i=1, cnt=0; i<=N; i++) {
			if (a[i] != 0 && Arrays.binarySearch(answer, i) < 0) {
				System.out.println(i);
				cnt++;
			}
			if (cnt == N-lis_count) break;
		}
	}
}

class FastScanner {
    BufferedReader br;
    StringTokenizer st;
 
    public FastScanner(String s) {
        try {
            br = new BufferedReader(new FileReader(s));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
 
    public FastScanner() {
        br = new BufferedReader(new InputStreamReader(System.in));
    }
 
    String nextToken() {
        while (st == null || !st.hasMoreElements()) {
            try {
                st = new StringTokenizer(br.readLine());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return st.nextToken();
    }
 
    int nextInt() {
        return Integer.parseInt(nextToken());
    }
 
    long nextLong() {
        return Long.parseLong(nextToken());
    }
 
    double nextDouble() {
        return Double.parseDouble(nextToken());
    }
 
    String nextLine() {
        String str = "";
        try {
            str = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str;
    }
}