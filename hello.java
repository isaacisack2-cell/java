public class hello{

    public static void salimia(){
        System.out.println("Habari isaac isack");
    }

    public static void greetings(String name){
        System.out.println("how are you "+name);
    }

    public int jumla(int a,int b){
        return a + b;
    }

    public static String check_age(int age){
        if(age < 0 || age > 100){
            return "invalid age input";
        }else if(age < 18){
            return "Access denied, Your so young";
        }else{
            return "Access granted, Welcome back";
        }
    }

    public static void main(String[] maneno){
        if(maneno.length > 0){
            System.out.println("first world "+maneno[0]);
        }else{
        System.out.println("No world passed yet");
        }
        System.out.println("hello world");
        salimia();
        greetings("isaac");
        greetings("adolf");
        System.out.println(check_age(20));
        //System.out.println(jumla(6,4));
    }

}
