import java.util.ArrayList;
import java.util.Random;
import java.lang.Math;
import java.awt.*;
/**
 * A class to model various types of Customer. All customers have an ID (sequentially increased static variable) as well as a type and assosciated properties.
 * The items a particular customer picks is normally distributed according to the type of customer.
 * The probability that a customer is of certain type is biased according to the hour of the day. At the moment, these values are completely arbitrary and are merely
 * a projection.
 * 
 * 
 * @author AngryPirates First Mate Alex
 * @version 12/3/11
 */
public class Customer
{
    private final String CUSTOMER_TYPE; 
    private final int ID;
    private static int nextID = 1;
    private final int MEAN_ITEMS;
    private final int STD_DEV;
    private final long ITEMS_TO_PICK; //Range of items to pick dependent on type of Customer.
    private int TIME_PER_ITEM = 5; //Arbitrary
    private final int TOTAL_ITEMS_AVAIL;
    private final double BUSINESS_PROB, OLD_PROB, CHILD_PROB, GENERIC_PROB;
    private long shoppingTime; //The time the customer spends picking items
    private ArrayList<Item> trolley;
    private Random rand;
    private Math math;
    private ArrayList<Item> productList;
    //private final String  LOYALTY_CARD_NUMBER = "Parrot";
    private int timeInStore;
    private int timeInQueue;
    private Point coordinates = new Point(0,0);
    private Point newCoordinates = new Point(0,0);
    int newX, newY;
    int currentX, currentY;
    int aisles = 450/40;

    /**
     * Constructor for objects of class Customer.
     * There 
     */
    public Customer(ArrayList<Item> productList, int hour)
    {
        this.productList = productList;
        ID = nextID;
        nextID++;
        timeInStore = 0;
        trolley = new ArrayList<Item>();
        rand = new Random();
        TOTAL_ITEMS_AVAIL = productList.size();
        if(hour >= 0 && hour < 8) //Midnight to 8am i.e. slow time
        {
            BUSINESS_PROB = 0.2;
            OLD_PROB = 0.3;
            CHILD_PROB = 0.6;
            GENERIC_PROB = 1.0;
        }
        else if(hour >= 8 && hour < 10)
        {
            BUSINESS_PROB = 0.01;
            OLD_PROB = 0.3;
            CHILD_PROB = 0.7;
            GENERIC_PROB = 1.0;
        }
        else if(hour >= 10 && hour < 12)
        {
            BUSINESS_PROB = 0.1;
            OLD_PROB = 0.5;
            CHILD_PROB = 0.6;
            GENERIC_PROB = 1.0;
        }
        else if(hour >= 12 && hour < 14)
        {
            BUSINESS_PROB = 0.4;
            OLD_PROB = 0.6;
            CHILD_PROB = 0.9;
            GENERIC_PROB = 1.0;
        }
        else if(hour >= 14 && hour < 17)
        {
            BUSINESS_PROB = 0.25;
            OLD_PROB = 0.3;
            CHILD_PROB = 0.7;
            GENERIC_PROB = 1.0;
        }
        else if(hour >= 17 && hour < 19)
        {
            BUSINESS_PROB = 0.075;
            OLD_PROB = 0.1;
            CHILD_PROB = 0.2;
            GENERIC_PROB = 1.0;
        }
        else if(hour >= 19 && hour < 22)
        {
            BUSINESS_PROB = 0.09;
            OLD_PROB = 0.1;
            CHILD_PROB = 0.4;
            GENERIC_PROB = 1.0;
        }
        else
        {
            BUSINESS_PROB = 0.2;
            OLD_PROB = 0.3;
            CHILD_PROB = 0.6;
            GENERIC_PROB = 1.0;
        }
        double decider = rand.nextDouble();
        if(decider >= 0 && decider <=BUSINESS_PROB)
        {
            MEAN_ITEMS = 5;
            STD_DEV = 1;
            CUSTOMER_TYPE = "Business";
        }
        else if(decider > BUSINESS_PROB && decider <= OLD_PROB)
        {
            MEAN_ITEMS = 15;
            STD_DEV = 4;
            CUSTOMER_TYPE = "Old";
        }
        else if(decider > OLD_PROB && decider <= CHILD_PROB)
        {
            MEAN_ITEMS = 3;
            STD_DEV = 1;
            CUSTOMER_TYPE = "Children";
        }
        else //assume generic
        {
            MEAN_ITEMS = 18;
            STD_DEV = 6;
            CUSTOMER_TYPE = "Generic";
        }
        long itemPickLimit;
        //Makes sure the Customer can't choose more items than exist.
        do {
            itemPickLimit = math.abs(math.round(rand.nextGaussian()*STD_DEV)+MEAN_ITEMS); //Finds range to pick items, with custom mean and standard deviation.
        }while(itemPickLimit >= TOTAL_ITEMS_AVAIL || itemPickLimit <= 0);
        ITEMS_TO_PICK = itemPickLimit;
        setShoppingTime(ITEMS_TO_PICK * TIME_PER_ITEM);
        //System.out.println(nextID);
    }    

    public Point getLocation()
    {
        return coordinates;
    }

    /*
     * public void setLocation(Point) //given point moved to
     * or
     * public void changeLocation() //calculate own point moved to
     */

    public int getID()
    {
        return ID;
    }

    public double addItem()
    {
        double itemPrice = 0.0;
        if(shoppingTime % (rand.nextInt(TIME_PER_ITEM)+1) == 0){ //If timeinstore % randomInt(timeperitem) == 0 to prevent synchronised shopping.
            if(trolley.size() < ITEMS_TO_PICK){
                walk();
                Item itemSelect = productList.get(rand.nextInt(TOTAL_ITEMS_AVAIL));
                trolley.add(itemSelect);
                shoppingTime = shoppingTime - TIME_PER_ITEM;
                itemPrice += itemSelect.getPrice();
                /*
                 * Walk random number of paces.
                 */
            }
            else{
                //Customer has all required items
                setShoppingTime(0);
                walkToCheckout();
            }
        }
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

    private void walk()
    {
        if(currentY > newY){
            currentY -= 40;
        }
        else if(currentY < newY){
            currentY += 40;
        }
        else if(currentY == newY)
        {
            if(currentX > newX){
                currentX -= 40;
            }
            else if(currentX < newX){
                currentX += 40;
            }
            else if(currentX == newX)
            {
                pickNewPosition();
            }
        }
        coordinates.move(currentX,currentY);
    }

    private void getPosition()
    {
        currentX = (int) math.round(coordinates.getX());
        currentY = (int) math.round(coordinates.getY());
    }

    private void walkToCheckout()
    {
        newX = 8;
        newY = 458;
        walk();
    }

    private void pickNewPosition()
    {
        newX = rand.nextInt(600-16);
        newY = (rand.nextInt(aisles*40));
        if(currentX <= 20 || currentX >= 580)// If customer is at the end of an aisle.
        {
            do
            {
                newY = (rand.nextInt(aisles)*40);// Pick a new Y co-ordinate, i.e. change aisles
            }
            while(newY % 20 != 0);// Make sure we don't end up in the shelves.
        }
        newX = rand.nextInt(600-16);// Pick a new horizontal position.
        //newCoordinates.move(newX, newY);
    }
}