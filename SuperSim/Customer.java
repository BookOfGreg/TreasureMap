import java.util.ArrayList;
import java.util.Random;
import java.lang.Math;
import java.awt.*;
/**
 * A class to model various types of Customer. All customers have an ID (sequentially increased static variable) as well as a type and assosciated properties.
 * The items a particular customer picks is normally distributed according to the type of customer.
 * The probability that a customer is of certain type is biased according to the hour of the day. These values are completely arbitrary and are merely
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
    private final String ID;
    private final int MEAN_ITEMS;
    private final int STD_DEV; // Standard deviation.
    private final long ITEMS_TO_PICK; // The total items this instance of customer has to pick up.
    private final int TIME_PER_ITEM = 10; //This value is assumed.
    private ArrayList<Item> trolley;

    // Store specific variables
    private final int TOTAL_ITEMS_AVAIL; // The number of different item types.
    private final double BUSINESS_PROB, OLD_PROB, CHILD_PROB, GENERIC_PROB;
    private long shoppingTime; // The time the customer spends picking items, constant x number of items.
    private int nextItemTime; //time till next item.

    //Statistics collection variables.
    private int timeInQueue;

    // Graphics
    private Point coordinates = new Point(0,0);
    private Point target = new Point(0,0);
    private final int WALK_DISTANCE = 5;

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
        // Instantiate variables needed later on.
        rand = new Random();
        TOTAL_ITEMS_AVAIL = productList.size();

        ID = "#" + nextID;
        nextID++;
        this.productList = productList;
        trolley = new ArrayList<Item>();

        /*
         * The following block of code sets the probability of a customer being a certain type based on the hour of the day.
         * The probabilities are cumulative so as to be of more use later on. These would be in a seperate method, but they must be initialized within the
         * constructor, since they are of type final.
         */
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

        //Make sure the Customer can't choose more items than exist.
        long itemPickLimit;
        do {
            /*
             * As nextGaussian() returns the 'Z number', we can rearrange the formula for finding the number of required standard deviations for a given value
             * with a certain mean. So where Z = (X - mean)/standard deviation, to find X we arrive at the formula (Z x standard deviation) + mean.
             * Also with the formula below, since items and their counts are integers, the desired number of items must also be an integer and positive to avoid
             * any strange errors. 
             * All the multiplication and addition is done before the result is rounded and made positive regardless of original sign.
             */
            itemPickLimit = math.abs(math.round((rand.nextGaussian()*STD_DEV)+MEAN_ITEMS));
        }
        // Ensures the number of items to choose is less than or equal to the total available.
        while(itemPickLimit >= TOTAL_ITEMS_AVAIL || itemPickLimit <= 0); 

        ITEMS_TO_PICK = itemPickLimit;// ITEMS_TO_PICK is final so a local variable must be used.
        nextItemTime=TIME_PER_ITEM+rand.nextInt(TIME_PER_ITEM/2); // Calculate the time the customer spends picking items.
    } 

    /**
     * An accessor method for use in the Store class to tell when a customer has finished shopping.
     * @return The amount of time the customer should spend picking items.
     */
    public long getShoppingTime()
    {
        return shoppingTime;
    }

    /**
     * A method that returns the timeInQueue for statistical processing.
     * @return The time the customer has spent in the queue.
     */
    public int getTimeInQueue()
    {
        return timeInQueue;
    }

    /**
     * An accessor method, used as part of Checkout, when a customer's trolley has been scanned, the ID is written out to a file.
     * @return A string of the customer's ID.
     */
    public String getID()
    {
        return ID;
    }

    /**
     * An accessor method to make use of the 'trolley' in the Checkout and Store classes for statistic collection.
     * @return trolley An ArrayList of Item containing what the customer has 'chosen'.
     */
    public ArrayList<Item> getTrolley()
    {
        return trolley;
    }

    /**
     * A method called from Checkout when scanning items in a customer's trolley.
     * @return The item object that was removed from the trolley.
     */
    public Item removeTrolleyItem()
    {
        return trolley.remove(0);
    }

    /**
     * A method to change the shoppingTime used only within this class.
     * @param newTime The new shoppingTime
     */
    private void setShoppingTime(long newTime)
    {
        shoppingTime = newTime;
    }

    /**
     * A method to increment the time the customer has spent in the queue. Since all queue processing is handled as part of Checkout, this is called for every tick that
     * the customer is in the queue, for more details, see the Checkout class.
     */
    public void incWait()
    {
        timeInQueue++;
    }

    /**
     * This method is called every tick in the Controlling classes.
     * To start with, and to prevent 'synchronised shopping', a randomly generated number is used to determine if a customer should pick up an item and walk on this tick.
     * The amount of time they have left in the store is then decreased by the time per item, or per tick. Not a huge amount of difference occurs in the simulation
     * results for either.
     * Once the customer has all their items, the shoppingTime is set to 0, which the Store class picks up on and further customer processing is done.k
     * @return The price of the item for statistic collection.
     */
    public double addItem()
    {
        double itemPrice = 0.0;
        //if(shoppingTime /* % (rand.nextInt(TIME_PER_ITEM)+1)*/ == 0){ //If timeinstore % randomInt(timeperitem) == 0 to prevent synchronised shopping.
        if(trolley.size() < ITEMS_TO_PICK){
            walk();
            nextItemTime--;
            if (nextItemTime == 0)
            {
                Item itemSelect = productList.get(rand.nextInt(TOTAL_ITEMS_AVAIL));
                trolley.add(itemSelect);
                nextItemTime=TIME_PER_ITEM+rand.nextInt(TIME_PER_ITEM/2);
                itemPrice += itemSelect.getPrice();
            }
            //shoppingTime = shoppingTime - //TIME_PER_ITEM;
            
        }
        else{
            //Customer has all required items
            //setShoppingTime(0);
            walkToCheckout();
            walk();
        }
        //}
        shoppingTime++;
        return itemPrice;
    }

    //coords for graphics

    public Point getLocation()
    {
        return coordinates;
    }

    private void walk()
    {
        if ((coordinateDifference() <= WALK_DISTANCE) &&(trolley.size() != ITEMS_TO_PICK))
        {
            int i = rand.nextInt(450);
            target.move(rand.nextInt(620-20),i-i%40);
        }
        if (wrongAisle())
        {
            if (coordinates.getY() < target.getY())
            {
                aboveAisle();
            }
            else
            {
                belowAisle();
            }
        }
        else
        {
            if (coordinates.getX() < target.getX())
            {
                coordinates.move((int)coordinates.getX()+WALK_DISTANCE,(int)coordinates.getY());
            }
            else
            {
                coordinates.move((int)coordinates.getX()-WALK_DISTANCE,(int)coordinates.getY());
            }
        }
    }

    private void aboveAisle()
    {
        if (inAisleEnd())
        {
            coordinates.move((int)coordinates.getX(),(int)coordinates.getY()+WALK_DISTANCE);
        }
        else
        {
            moveToAisleEnd();
        }
    }

    private void belowAisle()
    {
        if (inAisleEnd())
        {
            coordinates.move((int)coordinates.getX(), (int)coordinates.getY()-WALK_DISTANCE);
        }
        else
        {
            moveToAisleEnd();
        }
    }

    private void moveToAisleEnd()
    {
        if (coordinates.getX() < 600/2)
        {
            coordinates.move((int)coordinates.getX()-WALK_DISTANCE,(int)coordinates.getY());
        }
        else if (coordinates.getX() >= 600/2)
        {
            coordinates.move((int)coordinates.getX()+WALK_DISTANCE,(int)coordinates.getY());
        }
    }

    private boolean wrongAisle()
    {
        if(coordinates.getY() != target.getY())
        {
            return true;
        }
        return false;
    }

    private boolean inAisleEnd()
    {
        if((coordinates.getX() == 0 )||(coordinates.getX() == 600))
        {
            return true;
        }
        return false;
    }

    private int coordinateDifference()
    {
        if (coordinates.getY() == target.getY())
        {
            if (coordinates.getX() > target.getX())
            {
                return (int)coordinates.getX() - (int)target.getX();
            }
            else
            {
                return (int)target.getX() - (int)coordinates.getX();
            }
        }
        else
        {
            return Integer.MAX_VALUE;
        }
    }

    private void walkToCheckout()
    {
        target.move(600,440);
    }

    public boolean done()
    {
        if ((trolley.size() == ITEMS_TO_PICK)&&(coordinateDifference() <= WALK_DISTANCE))
        {
            return true;
        }
        return false;
    }
}