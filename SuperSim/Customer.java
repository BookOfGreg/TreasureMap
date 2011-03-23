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
 * @author Alex Stuart-Kregor, 51010371
 * @version 23/3/11
 */
public class Customer
{
    // Instance variables for use later on.
    private Random rand;
    private Math math;
    private static ArrayList<Item> productList;    
    private static int nextID = 1;    

    // Instance specific variables
    private final String CUSTOMER_TYPE; 
    private final int ID;
    private final int MEAN_ITEMS;
    private final int STD_DEV; // Standard deviation.
    private final long ITEMS_TO_PICK; // The total items this instance of customer has to pick up.
    private final int TIME_PER_ITEM = 5; //This value is assumed.
    private ArrayList<Item> trolley;

    // Store specific variables
    private final int TOTAL_ITEMS_AVAIL; // The number of different item types.
    private final double BUSINESS_PROB, OLD_PROB, CHILD_PROB, GENERIC_PROB;
    private long shoppingTime; // The time the customer spends picking items, constant x number of items.

    //Statistics collection variables.
    private int timeInStore;
    private int timeInQueue;

    // Graphics
    private Point coordinates = new Point(0,0);
    int newX, newY; // Position to move to.
    int currentX, currentY;
    int aisles = 450/40; // Number of aisles to ensure the customer doesn't end up in the middle of the shelves.

    /**
     * Constructor for objects of class Customer.
     * When the customer is created, the probability of their type (see below) is biased according to the hour of the day.
     * The number of items a customer chooses is normally distributed with a varied mean and standard deviation according to the type. Assistance for using the
     * nextGaussian() functionality was found on http://en.wikipedia.org/wiki/Normal_distribution and http://answers.yahoo.com/question/index?qid=20080417012416AAB0BJw
     * More detail can be found in the comments within the code itself.
     * @param productList An ArrayList of Item passed in from Store.
     * @param hour The hour of the day.
     */
    public Customer(ArrayList<Item> productList, int hour)
    {
        rand = new Random();
        TOTAL_ITEMS_AVAIL = productList.size();

        ID = nextID;
        nextID++;
        this.productList = productList;
        timeInStore = 0;
        trolley = new ArrayList<Item>();
        // The probabilities of a customer being of certain type are cumulative otherwise, they'd be useless.
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

        double typeChooser = rand.nextDouble();
        // The standard deviation and mean number of items is varied according to the type of customer.
        if(typeChooser >= 0 && typeChooser <=BUSINESS_PROB)
        {
            MEAN_ITEMS = 5;
            STD_DEV = 1;
            CUSTOMER_TYPE = "Business";
        }
        else if(typeChooser > BUSINESS_PROB && typeChooser <= OLD_PROB)
        {
            MEAN_ITEMS = 15;
            STD_DEV = 4;
            CUSTOMER_TYPE = "Old";
        }
        else if(typeChooser > OLD_PROB && typeChooser <= CHILD_PROB)
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
            /*
             * As nextGaussian() returns the 'Z number', we can rearrange the formula for finding the number of required standard deviations for a given value
             * with a certain mean. So where Z = (X - mean)/standard deviation, to find X we arrive at the formula (Z x standard deviation) + mean.
             * Also with the formula below, since items and their counts are integers, the desired number of items must also be an integer and positive to avoid
             * any strange errors. 
             * All the multiplication and addition is done before the result is rounded and made positive regardless of original sign.
             */
            itemPickLimit = math.abs(math.round((rand.nextGaussian()*STD_DEV)+MEAN_ITEMS));
        }while(itemPickLimit >= TOTAL_ITEMS_AVAIL || itemPickLimit <= 0); // Ensures the number of items to choose is less than or equal to the total available.
        // ITEMS_TO_PICK is final so a local variable must be used.
        ITEMS_TO_PICK = itemPickLimit;
        setShoppingTime(ITEMS_TO_PICK * TIME_PER_ITEM);
    }    

    public Point getLocation()
    {
        if(shoppingTime == 0)
        {
            walkToCheckout();
        }
        return coordinates;
    }

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
        if(currentY != newY)
        {
            moveY();
        }
        else if(currentY == newY)
        {
            moveX();
            if(currentX == newX)
            {
                pickNewPosition();
            }
        }
        coordinates.move(currentX,currentY);
    }

    private void moveY()
    {
        if(currentY > newY){
            currentY -= 20;
        }
        else if(currentY < newY){
            currentY += 20;
        }
    }

    private void moveX()
    {
        if(currentX > newX){
            currentX -= 20;
        }
        else if(currentX < newX){
            currentX += 20;
        } 
    }

    private void getPosition()
    {
        currentX = (int) math.round(coordinates.getX());
        currentY = (int) math.round(coordinates.getY());
    }

    public void walkToCheckout()
    {
        if(currentX <= 300)
        {
            newX = 8;
        }
        else
        {
            newX = 592;
        }
        newY = 440;
        if(currentX <= 20 || currentX >= 584)
        {
            moveY();
        }
        else
        {
            moveX();
        }
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
    }
}