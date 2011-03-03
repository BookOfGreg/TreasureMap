import java.util.ArrayList;
import java.util.Random;
/**
 * Write a description of class Customer here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Customer
{
    private String CUSTOMER_TYPE; 
    private final int  MIN_ITEMS = 1; 
    private final int MAX_ITEMS;
    private final int ITEMS_TO_PICK;
    private final int TIME_PER_ITEM = 5;
    private final int TOTAL_ITEMS_AVAIL = 58; //Could make method call from Item class
    private int shoppingTime;
    private ArrayList<Item> trolley;
    private Random rand;
    //private final String  LOYALTY_CARD_NUMBER = "Parrot";
    //private final int ITEM_COLLECTION_RATE = 1;

    //private final int  RANGE = 1;
    //private final int  ARRIVAL_TIME = 1; //the tick the customer is initialized on

    /**
     * Constructor for objects of class Customer
     */
    public Customer()
    {
        // initialise instance variables
        trolley = new ArrayList<Item>();
        rand = new Random();
        int temp = 0;
        shoppingTime = 0;
        //Code to bias random based on time
        switch(rand.nextInt(5)){
            case 0: MAX_ITEMS = 5; CUSTOMER_TYPE = "Business"; break;//Business
            case 1: MAX_ITEMS = 15; CUSTOMER_TYPE = "Old"; break;//Old
            case 2: MAX_ITEMS = 90; CUSTOMER_TYPE = "Family"; break;//Family
            case 3: MAX_ITEMS = 3; CUSTOMER_TYPE = "Children"; break;//Children
            case 4: MAX_ITEMS = 25; CUSTOMER_TYPE = "Generic"; break;//generic
            default: MAX_ITEMS = MIN_ITEMS; //Ensures the compiler knows that it can always set MAX_ITEMS
        }
        ITEMS_TO_PICK = MIN_ITEMS + rand.nextInt(MAX_ITEMS - MIN_ITEMS); //Finds range to pick items
        //System.out.println(temp);
    }    

    public void addItem()
    {
        if(trolley.size() < ITEMS_TO_PICK){
            trolley.add(new Item(rand.nextInt(TOTAL_ITEMS_AVAIL)));
            setShoppingTime(trolley.size() * TIME_PER_ITEM);
        }
        else{
            //Customer has all required items
            setShoppingTime(0);
        }

    }

    /**
     * An accessor method for use in the Store class
     * @return trolley.size() The number of items in the Customer's 'trolley'
     */
    public int getTrolleyCount()
    {
        return trolley.size();   
    }

    /**
     * An accessor method for use in the Store class
     * @return SHOPPING_TIME
     */
    public int getShoppingTime()
    {
        return shoppingTime;
    }

    /**
     * A mutator method to decrement the time remaining to pick items based on the global time from the Store class
     * @param newTime the new global time
     */
    public void setShoppingTime(int newTime)
    {
        shoppingTime = newTime;
    }
}
