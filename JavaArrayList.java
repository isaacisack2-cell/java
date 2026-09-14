import java.util.ArrayList;

public class JavaArrayList {
    public static void main(String[] args){
        ArrayList<Integer> marks = new ArrayList();
        marks.add(10);
        marks.add(20);
        marks.add(30);
        for(int alama : marks){
            System.out.println(alama+10);
        }

    }
}
