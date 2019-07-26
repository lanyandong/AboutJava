package datastructure;

/**
 * 利用栈结构实现分隔符匹配
 * a{b[c(d)e]f}g
 */

public class BracketsApp {

    public static void main(String[] args) {

        System.out.println("测试");

        String str = "{(](()}";

        BracketsCheck bc = new BracketsCheck(str);
        bc.check();

    }
}


// 定义一个 stack 结构
// 主要存储字符
class StackX{

    private int maxSize;
    private char[] data;
    private int top;

    public StackX(int size){
        maxSize = size;
        data = new char[maxSize];
        top = -1;
    }

    public void push(char c){
        data[++top] = c;
    }

    public char pop(){
        return data[top--];
    }

    public char peek(){
        return data[top];
    }

    public boolean isEmpty(){
        return (top == -1);
    }
}

class BracketsCheck{

    private String input;

    public BracketsCheck(String in){
        input = in;
    }

    public void check(){

        int size = input.length();
        StackX stackX = new StackX(size);

        for (int i = 0; i < input.length() ; i++) {
            char ch = input.charAt(i);

            switch (ch){
                case '{':
                case '[':
                case '(':
                    stackX.push(ch);
                    break;

                case '}':
                case ']':
                case ')':
                    if(!stackX.isEmpty()){
                        char chx = stackX.pop(); // 反括号出栈,进行匹配
                        if((ch == '}') && (chx !='{')
                                || (ch == ']') && (chx !='[')
                                ||(ch == ')') && (chx !='(')){
                            System.out.println("Error:" + ch + i);
                        }
                        else
                            System.out.println("Error:" + ch + i);
                        break
                                ;
                    }

                default:
                    break;
            }

        }
    }

}


