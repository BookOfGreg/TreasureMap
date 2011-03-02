import java.util.ArrayList;
/**
 * Write a description of class Customer here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Customer //This class might want to be abstract if we want to use subclasses as this will never be instantiated.
{
    // instance variables - replace the example below with your own
    private ArrayList<Item> trolley;
    private final int ITEM_COLLECTION_RATE = 1;
    private final String  LOYALTY_CARD_NUMBER = "Parrot";
    private final int  MIN_ITEM = 1;
    private final int  RANGE = 1;
    private final int  ARRIVAL_TIME = 1; //the tick the customer is initialized on

    /**
     * Constructor for objects of class Customer
     */
    public Customer()
    {
        // initialise instance variables
        trolley = new ArrayList<Item>();
        /*
         * switch(rand.nextInt(5)){
         *     case0: //Business
         *     case1: //Old
         *     case2: //Family
         *     case3: //Children
         *     case4: //generic
         *     }
         */
    }
    //addItem
    
}
