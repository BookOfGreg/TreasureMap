import java.util.ArrayList;
import java.util.Random;
import java.math.BigDecimal;
import java.awt.*;

/**
 * Store is the representation of the shop floor, container of the item list, 
 * currently browsing customers and the running checkouts. Controls the decision to toggle checkouts on and off 
 * as well as the creation of customers and passing them to checkouts when they have completed shopping.
 * 
 * @author AngryPirates, Cabin boy Greg and Seaman Sam
 * @version 2011,03,10
 */
public class Store
{
    private int checkoutLimit;
    private Random rand;
    private ArrayList<Checkout> checkoutList;
    private ArrayList<Customer> customerBrowsing;
    private ArrayList<Item> itemList;
    private int customerCounter;
    private double shopProfit; //BigDecimal
    private double currentProbability;
    private double cumulativeQueueTime;
    private double cumulativeExpressQueueTime;
    private int cumulativeWait;
    private final int DESIRED_AVERAGE_LENGTH = 4;
    private final int CLOSE_THRESHOLD = 1;
    private int totalInStore = 0;
    //graphics
    private Dimension shopFloor;
    private ArrayList<Point> aisles;
    private Dimension checkoutArea;
    private int checkoutCounter = 0;

    /**
     * Constructor for objects of class Store
     */
    public Store()
    {
        checkoutList = new ArrayList<Checkout>();
        customerBrowsing = new ArrayList<Customer>();
        itemList = new ArrayList<Item>();
        rand = new Random();
        customerCounter = 0;
        shopProfit = 0;   
        itemList = FileHandler.createAllItems();
        UserDialog ud = new UserDialog();
        FileHandler.add("Start Store",ud.getBoolean("Do you want to clear the loyalty file?"));
        Checkout newCheckoutExpress = new Checkout(true);
        checkoutList.add(newCheckoutExpress);
        Checkout newCheckout = new Checkout(false);
        checkoutList.add(newCheckout);
    }
    
    /**
     * Loops through the queue's and returns the number of customers in each as an array.
     * @return Customers in queue as array of ints.
     */
    public ArrayList<Integer> getQueues()
    {
        ArrayList<Integer> queues = new ArrayList<Integer>();
        
        for (Checkout check:checkoutList)
        {
            queues.add(new Integer(check.getQueueLength()));
        }
        return queues;
    }
    
    /**
     * Returns the number of checkouts.
     * @return Returns the number of checkouts as int.
     */
    public int getCheckoutListSize()
    {
        return checkoutList.size();
    }
    
    /**
     * Loops through customers to get all customer locations in the shop floor.
     * @return returns customer location as array of points.
     */
    public ArrayList<Point> getCustomerLocations()
    {
        ArrayList<Point> customers = new ArrayList<Point>();
        for (Customer cust:customerBrowsing)
        {
            customers.add(cust.getLocation());
        }
        return customers;
    }
    
    /** 
     * Runs once per tick, controls customers on the shop floor in customerBrowsing and controls passing customers to the queue area.
     * @param hour The current global hour the store is in.
     */
    public void Run(int hour)
    {
        createCustomer(hour);
        for(int i = (customerBrowsing.size()-1); i >= 0; i--) //Was error in this loop, but my bad ~Alex
        {
            Customer currentCustomer = customerBrowsing.get(i);
            if (currentCustomer.getShoppingTime() > 0)
            {
                shopProfit += currentCustomer.addItem();
            }
            else
            {
                if (currentCustomer.getTrolley().size() <= 10)
                {
                    addToSmallestQueue(customerBrowsing.remove(i),true);//join queue with least items
                }
                else
                {
                    addToSmallestQueue(customerBrowsing.remove(i),false);//join queue with least items exclude express
                }
                //addToSmallestQueue(customerBrowsing.remove(currentCustomerIndex), (currentCustomer.getTrolleyCount() <=10));
            }
        }
        for (int i = (checkoutList.size()-1); i >= 0; i--)
        {
            Checkout currentCheckout = checkoutList.get(i);
            cumulativeWait += currentCheckout.run();
            if (currentCheckout.getQueueLength() == 0 && currentCheckout.getClosing() == true) {
                checkoutList.remove(currentCheckout);
            }
        }
        if (currentAverageLength() > DESIRED_AVERAGE_LENGTH) {
            Checkout newCheckout = new Checkout(false);
            checkoutList.add(newCheckout);
            checkoutCounter++;
        } else if (currentAverageLength() < CLOSE_THRESHOLD && checkoutList.size() > 1) {
            int i = 0;
            boolean closed = false;
            while (i < checkoutList.size() && closed == false) {
                Checkout checkout = checkoutList.get(i);
                if (!checkout.isExpress() && checkout.getClosing() == false && checkoutCounter >= 1) {
                    checkout.setClosing();
                    checkoutCounter--;
                    closed = true;
                }
                i++;
            }
        }
    }
    
    /**
     * Gets the current Average Queue length.
     * @return average The average number of people in the queues as a double.
     */
    public double currentAverageLength()
    {
        int sum = 0;
        for (Checkout c:checkoutList)
        {
                sum += c.getQueueLength();
        }
        return sum / checkoutList.size();
    }
    
    /**
     * Updates the chance of a customer appearing according to the given hour.
     */
    public void updateCumulativeAverage() {
        int sum = 0, expressSum = 0, expressCounter = 0;
        for (Checkout c:checkoutList)
        {
            if (!c.isExpress()) {
                sum += c.getQueueLength();
            } else {
                expressSum += c.getQueueLength();
                expressCounter++;
            }
        }
        int average = sum / (checkoutList.size() - expressCounter);
        int expressAverage = expressSum / expressCounter;
        
        cumulativeQueueTime += average;
        cumulativeExpressQueueTime += expressAverage;
    }
    
    /**
     * Passes statistic of the total number of customers to the controller.
     * @return customerCounter The sum of customers.
     */
    public int getCustomerCounter()
    {
        return customerCounter;
    }
    
    /**
     * Calculates and returns the average customers in the store.
     * @return The average number of customers in the store.
     */
    public double getAverageInStore(int runTime)
    {
        return (double)customerCounter / (runTime / 3600);
    }
    
    /**
     * Calculates and returns the average customers in the queue.
     * @reutrn average queue size not including express.
     */
    public double getAverageQueue(int runTime)
    {
        return (double)cumulativeQueueTime / runTime;
    }
    
    /**
     * Calculates and returns the average customers in the express queue.
     * @return average express queue size.
     */
    public double getAverageExpressQueue(int runTime) {
         return (double) cumulativeExpressQueueTime / runTime;
    }
    
    /**
     * Returns the shop profit.
     * @return Shops profit.
     */
    public double getShopProfit() {
        return shopProfit;
    }
    
    /**
     * Totals up the time spent by all customers in store.
     */
    private void calcAverageInStore(Customer cust)
    {
        totalInStore += cust.getTimeInStore();
    }
    
    /**
     * Uses the total waiting time of all customers and returns the average waiting time as a double.
     * @param runTime The number of seconds (ticks) the whole program has been running.
     * @return the average wait in seconds (ticks) as double.
     */
    public double getAverageWait(int runTime) {        
        return (double)cumulativeWait / runTime;
    }
    
    /**
     * Method to decide if a customer will arrive on this tick and if so, create one and add to the shop floor.
     * @param hour The global hour the customer is created in.
     */
    private void createCustomer(int hour)
    {
        if (rand.nextFloat() <= currentProbability) {
            customerBrowsing.add(new Customer(itemList, hour));
            customerCounter++;
        }
    }
    
    /**
     * Calculates the probability that a customer will enter the store at the current hour.
     * @param currentHour The current global hour.
     */
    public void calcCurrentProbability(int currentHour)
    {
        //Assuming the the chances are going to vary to the nearest hour
        double[] timeProbabilities =  {0.2, 0.2, 0.2, 0.3, 0.4, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.8, 0.8, 0.7, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.4, 0.3};
        currentProbability = timeProbabilities[currentHour];
    }

    /**
     * Works out which checkout in checkoutList has the smallest queue length in terms of number of items in the queue and then adds that customer to the queue.
     * @param myCustomer The customer to be added to the queue.
     * @param express Toggles if the customer is eligable to join an express queue.
     */
    private void addToSmallestQueue(Customer myCustomer, boolean express)
    {
        //System.out.println("Assigning customer to queue");
        Checkout minCheckout = null;// = new Checkout();
        int min = Integer.MAX_VALUE;
        if (express)
        {
            for (Checkout currentCheckout:checkoutList)
            {
                if (currentCheckout.itemCount() < min && currentCheckout.getClosing() == false)
                {
                    minCheckout = currentCheckout;
                    min = currentCheckout.itemCount();
                }
            }
        }
        else
        {
            for (Checkout currentCheckout:checkoutList)
            {
                if (!currentCheckout.isExpress())
                    if (currentCheckout.itemCount() < min && currentCheckout.getClosing() == false)
                    {
                        minCheckout = currentCheckout;
                        min = currentCheckout.itemCount();
                }
            }
        }
        minCheckout.add(myCustomer);
    }
}
