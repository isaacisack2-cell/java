package testing;

import java.util.ArrayList;

public class WrapperClass {
    public static void main(String[] args){
        ArrayList<Integer> marks = new ArrayList<Integer>();
        marks.add(10);
        marks.add(20);
        marks.add(30);
        marks.add(40);
        for(Integer i : marks){
            System.out.println(i);
        }

        Integer num = 12345;
        String text = num.toString();
        System.out.println(text.length());

        Byte age = 20;
        var name = "isaac";
        String umri = age.toString();
        System.out.println("My name is "+name+" And I'm "+umri+" Years Old");
        System.out.println(umri.length() <= 2);
    }
}