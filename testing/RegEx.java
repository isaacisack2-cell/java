package testing;

import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.Scanner;

public class RegEx {
    public static void main(String[] args) {
        boolean invalid = true;
        String name;

        do {

            System.out.print("Enter name of file: ");
            Scanner kbd = new Scanner(System.in);
            name = kbd.nextLine();

            Pattern pattern = Pattern.compile("\\w+{3,10}.java$",Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(name);
            invalid = matcher.find();
        } while (!invalid);
        System.out.printf("%-5s  %-10s %-12s %12s\n","the","file","name","is good");
        System.out.println("-".repeat(40));
        char c;
        for (int i = 0x1F600; i < 0x1F64F; i++) {
            c = (char) i;
            System.out.printf("\\u%04X -> %c%n",i,c);
        }
    }
}