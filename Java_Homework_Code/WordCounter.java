/** 
 * @author ydlan 
 * @Date   2019-04-16
 * @Description:
 * Statistics the number of occurrences of each word in this string..
 *
 */


/*************************************/
//把字符串作为输入流，统计词频

import java.util.HashMap;
import java.util.Scanner;

public class WordCount {
	
	public static void wordCount(String str) {
		String s[] = str.split(",");
		HashMap<String, Integer> hm = new HashMap();
		
		for(int i = 0; i < s.length; i++) {
			if(!hm.containsKey(s[i])) {
				hm.put(s[i], 1);
			}
			else
				hm.put(s[i], hm.get(s[i]) + 1);
		}
		
		for(String ss : hm.keySet()) {
			System.out.println(ss + ": " + hm.get(ss));
		}		
		
	}
	
	public static void main(String args[]) {
		Scanner sc = new Scanner(System.in);
		String str = sc.nextLine();
		wordCount(str);
	}
}



/******************************************/
// 将字符串保存为文本，通过文件读取的方式统计词频

import java.io.*;
import java.util.HashMap;

public class ReadFile {
	public static void wordCount(String str) {
		String s[] = str.split(",");
		HashMap<String, Integer> hm = new HashMap();
        
		for(int i = 0; i < s.length; i++) {
			if(!hm.containsKey(s[i])) {
				hm.put(s[i], 1);
			}
			else
				hm.put(s[i], hm.get(s[i]) + 1);
		}
		
		for(String ss : hm.keySet()) {
			System.out.println(ss + ": " + hm.get(ss));
		}		
	}
	
	public static void main(String args[]) {
		try {
			BufferedReader in = new BufferedReader(
				new FileReader("C:\\Users\\15741\\Desktop\\work\\test.txt")); //读取存放字符串的文件
			String str;
			while((str = in.readLine()) != null) {
				System.out.println(str);
				wordCount(str);
				
			}   
		}catch(IOException e) {
			System.out.println("something is wrong!");
		}		
	}	
}