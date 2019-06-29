/** 
 * @author ydlan 
 * @Date   2019-05-07
 * @Description:
 * 1. Use Socket achieves file transfer client and server.
 * 2. display server file directory and achieves download/upload.
 * 3. Using Swing to generate GUI
 *
 */



/*******************************************/
// 服务端
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;


class ServerThread implements Runnable{
    private Socket s;
    public ServerThread(Socket socket){
        this.s = socket;
    }

    public void run() {
        try {
			
            System.out.println("已连接到服务器");
			
            BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
            String mess=br.readLine();
            System.out.println("客户端发过来的" + mess);
			
            String[] split = mess.split("!!!");
            String basePath="C:\\Users\\15741\\Desktop\\Server"; //自定义服务器根目录
			
			
			// 将服务器目录返回给客户端
            if(split[0].equals("ok")) {
            	ArrayList<String> list = new ArrayList<String>();  
            	String[] list1=new File(basePath).list();
            	list.add(basePath);
	            for(String name:list1){
	            	list.add(name);
                } 
	            System.out.println("服务端给客户端发送文件目录");//对象输出流
	            ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
	        	out.writeObject(list);
	        	out.flush();
	        	out.close();
            }
            
            if(split[0].equals("download")) {
            	 //文件下载，数据输出流
                 DataOutputStream dos = new DataOutputStream(s.getOutputStream());
                 File file = new File(basePath +"\\" + split[1]);
				 
                 if(file.exists()) { 
                 	 FileInputStream fis = new FileInputStream(file);
                 	 dos.writeUTF(file.getName());
                 	 dos.flush(); 
                     byte[] bytes = new byte[128];  
                     int length = 0;  
                     while((length = fis.read(bytes, 0, bytes.length)) != -1) {  
                         dos.write(bytes, 0, length);  
                         dos.flush();  
                     }  
                     System.out.println("服务端文件发送成功！");        
                     fis.close();
                 } 
                 dos.close();	
            }

            if(split[0].equals("upload")) {
				//文件上传，数据输出流
           	 	System.out.println("upload|filename|datasteam");
           	 	String filename = split[1];
	            File file = new File(basePath +"\\" +filename);
	            PrintStream ps = new PrintStream(new FileOutputStream(file));
	            ps.println(split[2]);
	            System.out.println("服务端文件接收成功！");
                ps.close();
            }
            br.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


class Server {
	public static final int PORT = 1234; //端口
	public static void server() throws IOException {
		ServerSocket serverSocket = new ServerSocket(PORT); 
		while (true){
			Socket s = serverSocket.accept();
            Thread thread = new Thread(new ServerThread(s)); 
            thread.start();  
        }
	}
}



/*******************************************/
// 带有GUI的客户端 

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class ClientGui extends JFrame implements ActionListener  {
	
	JLabel head = new JLabel("基于Socket的文件下载客户端");
	JLabel j1 = new JLabel("IP地址");
	JTextField jip = new JTextField(15);
	JLabel j2 = new JLabel("端口号");
	JTextField jport = new JTextField(5);
	JButton bnt = new JButton("快速连接");
	
	JLabel j3 = new JLabel("本机");
	//默认客户端文件路径
	JTextField tf = new JTextField("C:\\Users\\15741\\Desktop\\client",20); 
	JButton bnt1 = new JButton("查看目录");
	
	JLabel j4 = new JLabel("请输入要上传的文件名");
	JTextField jpath = new JTextField(15);
	JButton bnt2 = new JButton("确定上传");
	
	JLabel j5 = new JLabel("服务端");
	JTextField tf2 = new JTextField(20);
	JButton bnt3 = new JButton("查看目录");
	
	JLabel j6 = new JLabel("请输入要下载的文件名");
	JTextField jpath2 = new JTextField(15);
	JButton bnt4 = new JButton("确定下载");
	
	JLabel j7 = new JLabel("Copyright@2019");
	JPanel p1 = new JPanel();
	JPanel p2 = new JPanel();
	JPanel p3 = new JPanel();
	JPanel p4 = new JPanel();
	JPanel p5 = new JPanel();
	JPanel p6 = new JPanel();
	JPanel p7 = new JPanel();
	TextArea ta = new TextArea(20, 50);
	TextArea ta1 = new TextArea(20, 50);
	
	 public ClientGui(){
		 setTitle("客户端");
		 setSize(780,600);
		 setLocationRelativeTo(null);
		 setDefaultCloseOperation(EXIT_ON_CLOSE);
	     setVisible(true); 
	     
	     head.setFont(new Font("宋体",Font.BOLD,30));
	     p7.add(head);
	     
		 //link
	     p1.add(j1);
	     p1.add(jip);
	     p1.add(j2);
	     p1.add(jport);
	     p1.add(bnt);
	     p6.setLayout(new GridLayout(2, 1));
	     p6.add(p7);
	     p6.add(p1);
	   
	     p2.setLayout(new FlowLayout());
	     //client
	     p2.add(j3);
	     p2.add(tf);
	     p2.add(bnt1);
	     p2.add(ta);
	     p2.add(j4);
	     p2.add(jpath);
	     p2.add(bnt2);
	     
	     // server
	     p3.add(j5);
	     p3.add(tf2);
	     p3.add(bnt3);
	     p3.add(ta1);
	     p3.add(j6);
	     p3.add(jpath2);
	     p3.add(bnt4);
	     
	     
	     p4.setLayout(new GridLayout(1, 2));
	     p4.add(p2);
	     p4.add(p3);
	     
	    
	     p4.setBorder(BorderFactory.createLineBorder(Color.gray, 2));
		 p5.add(j7);
	     
	     this.add(p6,BorderLayout.NORTH);
	     this.add(p4,BorderLayout.CENTER);
	     this.add(p5,BorderLayout.SOUTH);
	     
		 // button 事件监听
	     bnt.addActionListener(this);  //快速连接
         bnt1.addActionListener(this); //客户端目录
         bnt2.addActionListener(this); //上传
         bnt3.addActionListener(this); //服务端目录
         bnt4.addActionListener(this); //下载
	 }
	 
	 
	 public void actionPerformed(ActionEvent e){
		 
	        if(e.getSource().equals(bnt)) { 
	        	String ip = jip.getText();
	        	String port = jport.getText();
	            Socket s;
				try {
					//直接连接服务端
					s = new Socket(ip, Integer.parseInt(port));
					JOptionPane.showMessageDialog(null,"操作成功！");
					System.out.println("客户端：连接服务器");

		            OutputStream os = s.getOutputStream();
	                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
	                bw.write("ok" + "\n");
	                bw.flush();
	                
	                //接收服务端发来的目录
	            	ObjectInputStream oi = new ObjectInputStream(s.getInputStream());
	            	@SuppressWarnings("unchecked")//不检查
					ArrayList<String> list = (ArrayList<String>) oi.readObject();
					
	        		System.out.println("收到服务端发过来的文件目录");
	        		tf2.setText(list.get(0));
	        		ta1.setText(null);
	        		for(int i = 1;i < list.size(); i ++){
	                     System.out.println(list.get(i)); 
	                     ta1.append(list.get(i)+"\r\n");//追加文本内容并换行     
	                 } 
	        		
				} catch (Exception e2) {
		            e2.printStackTrace();
		        }
	        }
	        
			// 查看本地目录文件
	        if(e.getSource().equals(bnt1)) {
	        	String dirname = tf.getText();
	        	File dir=new File(dirname);
	            if(dir.exists() && dir.isDirectory()){
	                ta.setText(null);   
	                String[] names=dir.list();
	                for(String name :names){
	                    ta.append(name+"\r\n");
	                }
	            }	
	        }
	        
			//上传文件
	        if(e.getSource().equals(bnt2)) {
	        	String dirname = tf.getText();
	        	String filename = jpath.getText();
	        	File file=new File(dirname +"\\"+ filename);
				
	            if(file.exists()){
	            	JOptionPane.showMessageDialog(null,"操作成功！");
	            	String ip = jip.getText(); 
		            String port = jport.getText();
	                Socket s;
	                try {
	    				s = new Socket(ip, Integer.parseInt(port));
	    				DataOutputStream dos = new DataOutputStream(s.getOutputStream());
	    				dos.writeUTF("upload" + "!!!");
						
	                    if(file.exists()) { 
	                    	FileInputStream fis = new FileInputStream(file);
	                    	dos.writeUTF(filename + "!!!");
	                        byte[] bytes = new byte[128];  
	                        int length = 0;
	                        while((length = fis.read(bytes, 0, bytes.length)) != -1) {  
	                            dos.write(bytes, 0, length);  
	                            dos.flush();  
	                        }  
	                        System.out.println("客户端文件发送成功！");        
	                        fis.close();
	                    } 
	                    dos.close();	
	    			} catch (Exception e2) {
	    	            e2.printStackTrace();
	    	        } 
	            }
	            else {
	            	JOptionPane.showMessageDialog(null,"请输入正确的文件名！");
	            }
	        }
			
			
			// 查看服务端目录文件
	        if(e.getSource().equals(bnt3)) {
	        	JOptionPane.showMessageDialog(null,"显示服务端目录");
	        	String dirname = tf2.getText();
	        	File dir=new File(dirname);
	            if(dir.exists() && dir.isDirectory()){
	                ta1.setText(null);   
	                String[] names=dir.list();
	                for(String name :names){
	                    ta1.append(name+"\r\n");
	                }
	            }	
	        }
	        
			
		    //下载文件
	        if(e.getSource().equals(bnt4)) {	  
	        	String ip = jip.getText();
	            String port = jport.getText();
	            Socket s;
				try {
					//直接连接服务端
					s = new Socket(ip, Integer.parseInt(port));
					System.out.println("ip= " + ip + "port=" + port);
					System.out.println("客户端连接服务器");
					
					String filepath = tf2.getText();
					String filename = jpath2.getText();
					File f =new File(filepath +"\\"+ filename);
					if(f.exists()) {
						OutputStream os = s.getOutputStream();
		                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
		                bw.write("download" +"!!!" + filename + "\n");
		                bw.flush();
		                
		                String localpath = tf.getText();
		                
		                DataInputStream dis = new DataInputStream(s.getInputStream());
		                String filename2 = dis.readUTF();
		                File file = new File(localpath +"\\" +filename2);
		                FileOutputStream fos = new FileOutputStream(file);
		                //开始接收文件  
		                byte[] bytes = new byte[1024];  
		                int length = 0;  
		                while((length = dis.read(bytes, 0, bytes.length)) != -1) {  
		                    fos.write(bytes, 0, length);  
		                    fos.flush();  
		                }   
		                fos.close();
		                dis.close();
		                bw.close();
		                System.out.println("客户端文件接收成功！"); 	
					}
					else {
						JOptionPane.showMessageDialog(null,"请输入正确的文件名！");
					}  	
				} catch (Exception e2) {
		            e2.printStackTrace();
		        }	
	        }
		 }	
}


/*********************************************/
// 主函数，集成客户端和服务端

public class Mian {
	public static void main(String args[]) {
		new ClientGui();
		try {
				Server.server();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	}
}

	
	
	
	