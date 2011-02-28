import java.util.ArrayList;
/**
 * Write a description of class Customer here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Customer
{
    // instance variables - replace the example below with your own
    private ArrayList<Item> trolley;
    private final int ITEM_COLLECTION_RATE = 1;
    private final String  LOYALTY_CARD_NUMBER = "Parrot";
    private final int  MIN_ITEM = 1;
    private final int  RANGE = 1;
    private final int  ARRIVAL_TIME = 1; //Tick initialized

    /**
     * Constructor for objects of class Customer
     */
    public Customer()
    {
        // initialise instance variables
        trolley = new ArrayList<Item>();
    }

    
}
