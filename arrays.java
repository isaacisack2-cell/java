public class arrays {
    public static void main(String[] args){
        String[][] names = {{"isaac","juma","john","bensoul"},{"asha","amina","jasmin"}};
        int group = 1;
        for(String[] gender : names){
            System.out.println("Group No"+group++);
            for(String name : gender){
                System.out.println(name);
            }
            System.out.println("-------------------------");
        }
    }
}