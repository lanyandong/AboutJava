package basetostring;

import java.util.Stack;


public class IntegerToString {
	
	//将一个整数转换为任意进制(16进制内)的字符串
	//num：整数,base:进制
	public static String baseString(int num,int base){
		if(base > 16){
			throw new RuntimeException("进制数超出范围，base<=16");
		}
		StringBuffer str = new StringBuffer("");
		String digths = "0123456789ABCDEF";
		Stack<Character> s = new Stack<Character>();
		while(num != 0){
			s.push(digths.charAt(num%base));
			num/=base;
		}
		while(!s.isEmpty()){
			str.append(s.pop());
		}
		return str.toString();
	}

	
	// 将一个任意进制的字符串转换为任意进制字符串
	public static String baseNum(String num,int srcBase,int destBase){
		if(srcBase == destBase){
			return num;
		}
		String digths = "0123456789ABCDEF";
		char[] chars = num.toCharArray();
		int len = chars.length;
		if(destBase != 10){//目标进制不是十进制 先转化为十进制
			num = baseNum(num,srcBase,10);
		}
		else{
			int n = 0;
			for(int i = len - 1; i >=0; i--){
				// 转换公式
				n+=digths.indexOf(chars[i])*Math.pow(srcBase, len - i - 1);
			}
			return n + "";
		}
		
		// 调用baseString方法，十进制转任意进制
		return baseString(Integer.valueOf(num),destBase);
	}
}
