package 백준;

import java.util.Scanner;
import java.util.Stack;

public class LCS3793 {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		while (sc.hasNext()) {
			String s = sc.next();
			String s2 = sc.next();
			sc.nextLine();
			
			int[][] d = new int[s2.length()+1][s.length()+1];
			for (int i=1; i<=s2.length(); i++) {
				for (int j=1; j<=s.length(); j++) {
					if (s2.charAt(i-1) == s.charAt(j-1))
						d[i][j] = d[i-1][j-1] + 1;
					else
						d[i][j] = Math.max(d[i-1][j], d[i][j-1]);
				}
			}
			
			System.out.println(d[s2.length()][s.length()]);
			
			//	debug - LCS 문자수열 찾아내기
//			Stack<Character> st = new Stack<>();
//			int i = s2.length(), j = s.length();
//			while(d[i][j] != 0) {
//				if (d[i][j] == d[i][j-1])
//					j--;
//				else if (d[i][j] == d[i-1][j])
//					i--;
//				else if (d[i][j] - 1 == d[i-1][j-1])
//				{
//					st.push(s2.charAt(i-1));
//					i--; j--;
//				}
//			}
//			System.out.println("스택 사이즈 : "+st.size()); 
//			while(!st.isEmpty())
//				System.out.print(st.pop());
//			System.out.println("");
		}
		
		sc.close();
	}

}
