import java.util.Scanner;


public class Myclass{

    public int sum(int a, int b){
        return a + b;
    }
    public static void main(String[] args){
        Myclass method1 = new Myclass();
        System.out.println("SUM OF NUMBERS:"+method1.sum(12,8));
       Scanner kb=new Scanner(System.in);
       System.out.println("Enter your name below");
       String name = kb.nextLine();
       System.out.println("WELCOME "+name+" INTO OUR CLAB");
    }
}