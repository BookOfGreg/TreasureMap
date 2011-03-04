import java.util.ArrayList;
import java.util.Random;
/**
 * Write a description of class Customer here.
 * 
 * @author AngryPirates First Mate Alex
 * @version 0.1
 */
public class Customer
{
    private final String CUSTOMER_TYPE; 
    private final int  MIN_ITEMS = 1; 
    private final int MAX_ITEMS;
    private final int ITEMS_TO_PICK; //Range of items to pick dependent on type of Customer.
    private final int TIME_PER_ITEM = 5;
    private final int TOTAL_ITEMS_AVAIL;
    private int shoppingTime;
    private int itemTime; //~Greg added this as time till next item. Used in addItem.
    private ArrayList<Item> trolley;
    private Random rand;
    private ItemHandler itemHandler;
    private ArrayList<Item> productList;
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
        itemTime = 30; //abitrary number, change this later.
        
        shoppingTime = 0;
        trolley = new ArrayList<Item>();
        rand = new Random();
        itemHandler = new ItemHandler();
        productList = itemHandler.getItemList();
        TOTAL_ITEMS_AVAIL = productList.size();
        //Code to bias random based on time
        switch(rand.nextInt(5)){
            case 0: MAX_ITEMS = 5; CUSTOMER_TYPE = "Business"; break;
            case 1: MAX_ITEMS = 15; CUSTOMER_TYPE = "Old"; break;
            case 2: MAX_ITEMS = 90; CUSTOMER_TYPE = "Family"; break;
            case 3: MAX_ITEMS = 3; CUSTOMER_TYPE = "Children"; break;
            case 4: MAX_ITEMS = 25; CUSTOMER_TYPE = "Generic"; break;
            default: MAX_ITEMS = MIN_ITEMS; CUSTOMER_TYPE = "Generic"; break;//Ensures the compiler knows that it can always set MAX_ITEMS
        }
        int itemPickLimit = TOTAL_ITEMS_AVAIL;
        while(itemPickLimit >= TOTAL_ITEMS_AVAIL){//Makes sure the Customer can't choose more items than exist.
            itemPickLimit = MIN_ITEMS + rand.nextInt(MAX_ITEMS - MIN_ITEMS); //Finds range to pick items.
        }
        ITEMS_TO_PICK = itemPickLimit;
    }    

    public void addItem()
    {
        if(itemTime == 0){
            if(trolley.size() < ITEMS_TO_PICK){
                trolley.add(productList.get(rand.nextInt(TOTAL_ITEMS_AVAIL)));
                setShoppingTime(trolley.size() * TIME_PER_ITEM);
            }
            else{
                //Customer has all required items
                setShoppingTime(0);
                //return getShoppingTime();
            }
            itemTime = 30; //Abritrary number atm. Replace with constant set at creation later.
        }
        else{
            itemTime--;
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
    
    /**
     * An accessor method to make use of the 'trolley' in the Checkout and Store classes
     * @return trolley An ArrayList of Item containing what the customer has 'chosen'
     */
    public ArrayList<Item> getTrolley()
    {
        return trolley;
    }
}
