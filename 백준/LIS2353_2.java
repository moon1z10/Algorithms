package น้มุ;

import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class LIS2353_2 {
	public static void main(String[] args) throws NumberFormatException, IOException {
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		int[] area = new int[N];
		int[] answerList = new int[N];
		int[] p = new int[N];
		int count = 0;
		for(int i = 0; i<N; i++){
			area[i] = sc.nextInt();
			int index = Arrays.binarySearch(answerList, 0, count, area[i]);
			if(index>=0){
				answerList[index] = area[i];
			}else{
				index = Math.abs(index);
				if(index>count){
					count++;
				}
				index--;
				answerList[index] = area[i];
			}
			p[i] = index;
		}
		System.out.println(count);
		backtrace(area, p, count);
		sc.close();
	}

	private static void backtrace(int[] a, int[] p, int c) {
		int[] answer = new int[c];
		c--;
		int index = c;
		for (int i = p.length-1; i>=0; i--) {
			if (p[i] == c) {
				answer[index--] = a[i];
				c--;
			}
			if (c < 0) break;
		}
		for (int num : answer)
			System.out.println(num);
	}
}
