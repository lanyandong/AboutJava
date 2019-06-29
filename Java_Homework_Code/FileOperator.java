/** 
 * @author ydlan 
 * @Date   2019-04-23
 * @Description:
 * 1. Create, delete and enter folders.
 * 2. Realize the content listing under the current folder.
 * 3. Implement file copy and folder copy (folder copy refers to deep copy, including all subdirectories and files).
 * 4. Encryption and decryption of specified files.
 *
 *
 */


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

public class Operator {
	
	static Scanner sc = new Scanner(System.in);

	// create directory
	public static void createDir(String path, String dirname) {
		File dir = new File(path + dirname);   
        if ( !dir.exists()){   
            dir.mkdir();  
            System.out.println("create dir："+ dirname);  
        }
        else {
        	System.out.println("the dir is exist！");  
        }
	}
	

	// create file
	public static void createFile(String path, String filename) {
		File file = new File(path + filename);
		if(!file.exists()) {
			try {
				file.createNewFile();
				System.out.println("create file："+ file);
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
		else
			System.out.println("the file is exist！");
	}
	
	// delete directory and file
	public static void deleteFile(File directory) {
		if (!directory.isDirectory()){
	        directory.delete();
	        System.out.println("delete successful");
	    } else{
	        File [] files = directory.listFiles();
	        if (files.length == 0){
	            directory.delete();
	            System.out.println("delete directory：" + directory.getAbsolutePath());
	            return;
	        }
	        // delete child file
	        for (File file : files){
	            if (file.isDirectory()){
	            	deleteFile(file);
	            } else {
	                file.delete();
	                System.out.println("delete：" + file.getAbsolutePath());
	            }
	        }
	        directory.delete();  //  delete this directory
	    }
	}
	
	
	// display the file lists
	public static void displayFile(File directory) {
		if(directory.isDirectory() ){
			File [] files = directory.listFiles();
			for(int i = 0; i < files.length; i++) {
				if(files[i].isDirectory()) {
					System.out.println("dir：" + files[i]);
				}
				else
					System.out.println("file: " + files[i]);
			}	
		}
		else {
			System.out.println("the directory is not exist！" );
		}
	}
	
	
	// enter into the directory
	public static void fileCd(File directory) {
		if(!directory.isDirectory()) {
			System.out.println("is not directory，can't come！");
		}
		else {
			System.out.println("the directory lists：");
			displayFile(directory);
		}
	}
	
	
	// copy the file(the source file to the target file)
	public static void fileCopy(File sfile, File tfile) throws IOException {
		if(sfile.exists() ) {
			if(!tfile.exists()) {
				tfile.createNewFile();		
			}
			
	        FileInputStream input = new FileInputStream(sfile);  
	        BufferedInputStream inbuff = new BufferedInputStream(input);  
	        
	        FileOutputStream output = new FileOutputStream(tfile);  
	        BufferedOutputStream outbuff=new BufferedOutputStream(output);  
	            
	        byte buf[] = new byte[128];  
	        int len = 0;
	        //read(buf) 此输入流中将最多 buf.length 个字节的数据读入一个字节数组中
	        while ((len =inbuff.read(buf)) != -1) {  
	            outbuff.write(buf, 0, len); //buf是缓冲数组   '0'目标数组的buf的起始偏移量 ,len读取的最大字节量
	        }  
	       
	        outbuff.flush(); // 刷新此缓冲的输出流   
	        System.out.println("copy file successfully! " );
	        inbuff.close();  
	        outbuff.close();  
	        output.close();  
	        input.close();  	
	
		}else {
			System.out.println("the source file is not exist... " );
		}
	}
	
	
	// copy the directory(the source dir to the target dir)  and include all files
    public static void dirCopy(String sourceDir, String targetDir) throws IOException { 
    	try {
        	(new File(targetDir)).mkdirs();
            File[] file = (new File(sourceDir)).listFiles();  
            for (int i = 0; i < file.length; i++) {  
                if (file[i].isFile()) {  
                   File sourceFile=file[i];    
                   File targetFile=new File(new File(targetDir).getAbsolutePath() +File.separator+file[i].getName());  
                   fileCopy(sourceFile,targetFile);  
                }  
                if (file[i].isDirectory()) {     
                    String dir1=sourceDir + File.separator + file[i].getName();  
                    String dir2=targetDir + File.separator+ file[i].getName();  
                    dirCopy(dir1, dir2);   // recursion
                }  
            }
            System.out.println("copy the directory successfully！ " );
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("the source directory is not exist！ " );
		}  
    }  
	
    
    // file encrypt and file decode
    public static void fileEncrypt(String path) throws IOException {
    	System.out.println("please input filename... " );
    	String filename = sc.nextLine();
    	
    	File file = new File(path + filename);

    	if(file.exists()) {
    		byte skey = 101; // secret key
        	FileInputStream input = new FileInputStream(file);
        	System.out.println("please input encrypt filename..." );
        	String lockfile = sc.nextLine();
        	FileOutputStream output = new FileOutputStream(lockfile);  
        	
        	System.out.println("start file encrypt... " );
        	byte buf[] = new byte[128];
        	int len = 0;
        	while((len = input.read(buf)) != -1) {
        		for(int i = 0; i < len; i++) {
        			buf[i] = (byte)(buf[i] ^ skey);  // file encrypt 
        		}
        		output.write(buf, 0, len);	
        	}
        	System.out.println("file encrypt successful! encrypt file is:" + lockfile);
        	input.close();
        	output.close();
        	
        	// file encode
        	System.out.println("file decode... " );
        	input = new FileInputStream(lockfile);
        	
        	System.out.println("please input decode filename... " );
        	String unlockfile = sc.nextLine();
        	output = new FileOutputStream(unlockfile);
        	System.out.println("start file decode... " );
        	
        	buf = new byte[128];
        	len = 0;
        	while((len = input.read(buf)) != -1) {
        		for(int i = 0; i < len; i++) {
        			buf[i] = (byte)(buf[i] ^ skey);
        		}
        		output.write(buf, 0, len);	
        	}
        	
        	System.out.println("file decode successful! decode file is:" + unlockfile );
        	input.close();
        	output.close();	

    	}else {
    		System.out.println("the file is not exist！" );
    	}
    }
}
