import java.util.ArrayList;
import java.util.Random;
import java.lang.Math;
/**
 * Write a description of class Customer here.
 * 
 * @author AngryPirates First Mate Alex
 * @version 0.1
 */
public class Customer
{
    private final String CUSTOMER_TYPE; 
    private final int ID;
    private final int  MIN_ITEMS = 1; //going to change soon
    private final int MEAN_ITEMS;
    private final long ITEMS_TO_PICK; //Range of items to pick dependent on type of Customer.
    private int TIME_PER_ITEM = 5; //Arbitrary
    private final int TOTAL_ITEMS_AVAIL;
    private long shoppingTime; //The time the customer spends picking items
    private ArrayList<Item> trolley;
    private Random rand;
    private Math math;
    private ArrayList<Item> productList;
    //private final String  LOYALTY_CARD_NUMBER = "Parrot";
    private int timeInStore;
    private int timeInQueue;

    /**
     * Constructor for objects of class Customer
     */
    public Customer(ArrayList<Item> productList,int myId, int hour)
    {
        this.productList = productList;
        ID = myId;
        timeInStore = 0;
        trolley = new ArrayList<Item>();
        rand = new Random();
        TOTAL_ITEMS_AVAIL = productList.size();
        /*Code to bias random based on time
         * Might have to change from switch to if and ifelse or have if and else if calculate the int for
         * switch based omc probabilities and distributions and then have a rand.nextInt() as last else.
         */
        //double multiplyer = math.abs(rand.nextGaussian()*2*14); //Probability * Mean * StdDev then absolute.
        switch(rand.nextInt(5)){
            case 0: MEAN_ITEMS = 5; CUSTOMER_TYPE = "Business"; break;
            case 1: MEAN_ITEMS = 15; CUSTOMER_TYPE = "Old"; break;
            case 2: MEAN_ITEMS = 12; CUSTOMER_TYPE = "Family"; break;
            case 3: MEAN_ITEMS = 3; CUSTOMER_TYPE = "Children"; break;
            case 4: MEAN_ITEMS = 18; CUSTOMER_TYPE = "Generic"; break;
            default: MEAN_ITEMS = MIN_ITEMS; CUSTOMER_TYPE = "Generic"; break;//Ensures the compiler knows that it can always set MEAN_ITEMS
        }
        long itemPickLimit;// = TOTAL_ITEMS_AVAIL;
        do {//Makes sure the Customer can't choose more items than exist.
            itemPickLimit = MIN_ITEMS + math.abs(math.round(rand.nextGaussian())+MEAN_ITEMS); //Finds range to pick items.
        }while(itemPickLimit >= TOTAL_ITEMS_AVAIL);
        ITEMS_TO_PICK = itemPickLimit;
        setShoppingTime(ITEMS_TO_PICK * TIME_PER_ITEM);
    }    

    public double addItem()
    {
        double itemPrice = 0;
        if(shoppingTime % (rand.nextInt(TIME_PER_ITEM)+1) == 0){ //If timeinstore % randomInt(timeperitem) == 0 to prevent synchronised shopping.
            if(trolley.size() < ITEMS_TO_PICK){
                Item itemSelect = productList.get(rand.nextInt(TOTAL_ITEMS_AVAIL));
                trolley.add(itemSelect);
                shoppingTime = shoppingTime - TIME_PER_ITEM;
                itemPrice += itemSelect.getPrice();
            }
            else{
                //Customer has all required items
                setShoppingTime(0);
            }
        }
        timeInStore++;
        System.out.println("Method call" + "\n\tItems to pick = " + ITEMS_TO_PICK + "\n\tTrolley size = " + trolley.size());
        return itemPrice;
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
    public long getShoppingTime()
    {
        System.out.println("Shopping Time = " + shoppingTime);
        return shoppingTime;
    }

    /**
     * A mutator method to decrement the time remaining to pick items based on the global time from the Store class
     * @param newTime the new global time
     */
    public void setShoppingTime(long newTime)
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

    public Item removeTrolleyItem()
    {
        return trolley.remove(0);
    }

    public int getTimeInStore()
    {
        return timeInStore;
    }

    /**/

    public void incWait()
    {
        timeInQueue++;
    }

    public int getTimeInQueue()
    {
        return timeInQueue;
    }
}
