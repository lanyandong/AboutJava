package gethtmlcode;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;

public class GetHTMLCode {
	
	public static void main(String[] args){
		
		try {
			URL url = new URL("http://www.baidu.com");
			URLConnection conn = url.openConnection();
			conn.setDoOutput(true);
			
			InputStream is = url.openStream();
			
			String content = getHTML(is);
			System.out.println(content);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("输入正确的网址");
		}	
	}
	
	
	public static String getHTML(InputStream is) {
		
		String line = null;
		StringBuffer sb = new StringBuffer();
		
		try {
			BufferedReader bReader = new BufferedReader(new InputStreamReader(is,"UTF-8"));
			FileOutputStream fos = new FileOutputStream("D:/test.txt"); // 定义文件输出流
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(fos,"UTF-8"));
			
			// 按行进行读取数据
			while ( (line = bReader.readLine()) != null) {
	            String tmp_line = line;
	            int str_len = tmp_line.length();
	            if (str_len > 0) {
	              sb.append(tmp_line);
	              pw.println(tmp_line);
	              pw.flush();
	            } 
	            tmp_line = null;
			}
			
			is.close();
			pw.close();
			fos.close();
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("文件路径不存在");
		}
		return sb.toString();
	}
}
