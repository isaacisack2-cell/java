package testing;

/*
    Java Annotations
    -   @Override                     ----     Make sure we inherit the exactly mother method
    -   @Deprecated                   ----     Make warning for methods to be used
    -   @SuppressWarnings(: warning)    ----     ignore some warnings
*/

class Food {
    String foodName = "pizza";

    /*Food(String name){
        this.foodName = name;
    }*/
   
    void benefits(){
        System.out.println("Food like "+this.foodName+" brings heat, health and energy");
    }
}

class Fruits extends Food {
    String fruitName;

    @Override
    void benefits(){
        System.out.println("Fruits contains more vitamins for body health");
    }

    public void setName(String name){
        this.fruitName = name;
    }

    public String getName(){
        return this.fruitName;
    }
}

class Meat extends Food {
    String meatName;

    Meat(String name){
        this.meatName = name;
    }

    @Override
    void benefits(){
        System.out.println("All meet like "+this.meatName+" are rich in protein. Hence builds body");
    }
}

public class PolymophismDemo1 {
    public static void main(String[] args) {
        Food chakula = new Food();
        Fruits matunda = new Fruits();
        Meat nyama = new Meat("Beef and chicken");

        chakula.benefits();
        matunda.benefits();
        nyama.benefits();

        matunda.setName("Embe");
        System.out.println("Fruit name sot was: "+matunda.getName());

    }
}