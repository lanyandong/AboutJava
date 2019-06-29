/** 
 * @author ydlan 
 * @Date   2019-04-30
 * @Description:
 * 1. Use Java set framework achieves student grades management.
 * 2. add,display,search,sort .
 * 3. ArrayList is not good strategy,i think hashmap is better
 *
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class Function {
	
	static Scanner sc  = new Scanner(System.in);
	
	//add student info
	public static void addStudent(ArrayList<Student> stuList){
        System.out.println("Please Student Id，Name，GradeA, GradeB...");
        String stuId = sc.nextLine();
        String stuName = sc.nextLine();
        String gradeA = sc.nextLine();
        String gradeB = sc.nextLine();
        Student student = new Student(stuId, stuName, Integer.parseInt(gradeA),Integer.parseInt(gradeB));
        stuList.add(student);
    }
	
	//shou student info
	 public static void showStu(ArrayList<Student> stuList) {
		 
		 System.out.println("stuId-----stuName-----GradeA-----GradeB");
		 for (Student temp : stuList) {
			 System.out.println(temp.getSid() +"\t"+ temp.getName() +"\t"
		 + temp.getGradeA() +"\t"+ temp.getGradeB()); 
		 
		 }
	}
	
	 // search student info
	 public static void searchStu(ArrayList<Student> stuList) {
		 System.out.println("please input student name...");
		 String name = sc.nextLine();
		 
		 // sort by stu GradeA
		 Collections.sort(stuList, new SortByGradeA());
		 ArrayList<Student> stuListA = stuList;
		 
		 ArrayList<String> table = new ArrayList<>();
		 int rankA = 0;
		 for (Student stu : stuListA) {
			 rankA = rankA + 1;
			 if(stu.getName().equals(name)) {
				 System.out.println(rankA);
				 table.add("stuName:"+ stu.getName()+"\tstuID:"+ stu.getSid() 
				 + "\tGradeA:"+ stu.getGradeA() + "\trankA:"+ rankA);
			 } 
		 }
		 
		 // sort by stu GradeB 
		 Collections.sort(stuList, new SortByGradeB());
		 ArrayList<Student> stuListB = stuList;
		 int rankB = 0;
		 for (Student stu : stuListB) {
			 rankB = rankB + 1;
			 if(stu.getName().equals(name)) {
				 table.add("stuName:"+ stu.getName()+"\tstuID:"+ stu.getSid() 
				 + "\tGradeB:"+ stu.getGradeB() + "\trankB:"+ rankB);
			 } 
		 }
		 
		 if(!table.isEmpty()) {
			 for(String stu : table ) {
				 System.out.println(stu); // output the search result
			 }
		 }
		 else {
			 System.out.println("can't search the student！");
		 }
	 }
	 
	// sort by stuID
	public static void sortBySid(ArrayList<Student> stuList) {
          System.out.println("sort by stuID：");
          System.out.println("stuId-----stuName-----GradeA-----GradeB");
          Collections.sort(stuList, new SortBySid());
          for (Student stu : stuList) {
           System.out.println( stu.getSid()+"\t"+ stu.getName()+"\t" 
          + stu.getGradeA()+ "\t"+ stu.getGradeB());
          }
	}
	
	// sort by stu Grade 
	public static void sortByGrade(ArrayList<Student> stuList) {
		
		 System.out.println("sort list by GradeA：");
		 Collections.sort(stuList, new SortByGradeA());
		 System.out.println("stuId-----stuName-----GradeA");
         for (Student stu : stuList) {
          System.out.println(stu.getSid() +"\t"+ stu.getName() +"\t"+ stu.getGradeA());
        } 
         
         System.out.println("sort list by GradeB：");
		 Collections.sort(stuList, new SortByGradeB());
		 System.out.println("stuId-----stuName-----GradeB");
         for (Student stu : stuList) {
          System.out.println(stu.getSid() +"\t"+ stu.getName() +"\t"+ stu.getGradeB());
        }  
	}
	
	// read the student info from local file
	public static void readStuTable() throws IOException {
		try {
			BufferedReader br = new BufferedReader(new FileReader("stutable.txt")); // read a student table
			String line = null;
	        while ((line = br.readLine()) != null) {
	        	String[] arr = line.split(",");
	        	Student student = new Student(arr[0],arr[1],Integer.parseInt(arr[2]),Integer.parseInt(arr[3]));
	        	stuList.add(student);
	        }
	        br.close();
		} catch (FileNotFoundException e) {
			// TODO 自动生成的 catch 块
			System.out.println("this no student info, please add student info");
		}
	}
	
	// write back the student info to local file
	public static void writeStuTable() throws IOException {
		BufferedWriter bw = new BufferedWriter(new FileWriter("stutable.txt"));
        for (Student temp : stuList) {
            bw.write(temp.getSid()+ ","+ temp.getName() +","+  temp.getGradeA() +","+  temp.getGradeB());
            bw.newLine();
            bw.flush();
        }
        bw.close();	
	}
	
		
}



// 特殊的需求排序，需要实现Comparator接口
// 重写public int compare(Object o1, Object o2) 方法
class SortBySid implements Comparator<Student>  {
	@Override
    public int compare(Student s1, Student s2) {
		return s1.getSid().compareTo(s2.getSid());
    }
}

class SortByGradeA implements Comparator<Student> {
	@Override
    public int compare(Student s1, Student s2) {
     if(s1.getGradeA() < s2.getGradeA())
    	 return 1;
     return -1;
    }
}

class SortByGradeB implements Comparator<Student> {
	@Override
    public int compare(Student s1, Student s2) {
     if(s1.getGradeB() < s2.getGradeB())
    	 return 1;
     return -1;
    }
}


