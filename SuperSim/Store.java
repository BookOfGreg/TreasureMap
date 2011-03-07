import java.util.ArrayList;
import java.util.Random;

/**
 * Write a description of class Store here.
 * 
 * @author AngryPirates, Cabin boy Greg and Seaman Sam
 * @version 0.2
 */
public class Store
{
    private int checkoutLimit;
    private Random rand;
    private ArrayList<Checkout> checkoutList;
    private ArrayList<Customer> customerBrowsing;
    private ArrayList<Item> itemList;
    private int customerCounter;
    private int currentProbability;

    /**
     * Constructor for objects of class Store
     */
    public Store()
    {
        checkoutList = new ArrayList<Checkout>();
        customerBrowsing = new ArrayList<Customer>();
        itemList = new ArrayList<Item>();
        rand = new Random();
        ItemHandler myItemHandler = new ItemHandler();
        itemList = myItemHandler.getItemList();
    }

    /** 
     * Set the timing for the program
     */
    public void Run()
    {
        //Main methods
        for(Customer currentCustomer:customerBrowsing)
        {
            int shoppingTime = currentCustomer.getShoppingTime(); //just so there are less method calls. ~Alex: getShoppingTime() only gets called once!
            if (shoppingTime > 0)
            {
                //currentCustomer.setShoppingTime(shoppingTime-1); //~Alex: This is now handled within Customer.
                currentCustomer.addItem();
            }
            else
            {
                int currentCustomerIndex = customerBrowsing.indexOf(currentCustomer); //remove() will only return the element if it's removed by index and not element
                if (currentCustomer.getTrolleyCount() <= 10)
                {
                    addToSmallestQueue(customerBrowsing.remove(currentCustomerIndex),true);//join queue with lest items
                }
                else
                {
                    addToSmallestQueue(customerBrowsing.remove(currentCustomerIndex),false);//join queue with lest items exclude express
                }
                //addToSmallestQueue(customerBrowsing.remove(currentCustomerIndex), (currentCustomer.getTrolleyCount() <=10));
            }
        }
        for (Checkout currentCheckout:checkoutList)
        {
            currentCheckout.run();
        }
        /*if (checkoutLength > desiredAverageLength)
        {
            //openNewCheckout();
        }
        */
    }
    
    public int getCustomerCounter()
    {
        return customerCounter;
    }
    
    public double getAverageStore()
    {
        //
        return 1.0; //arbitrary
    }
    
    public double getAverageQueue()
    {
        //
        return 2.0; //arbitrary
    }
    
    public void createCustomer()
    {
        if (rand.nextFloat() <= getCurrentProbability()) {
            //id number needs calculating.
            customerBrowsing.add(new Customer(itemList, 5));
            customerCounter++;
        }
        
        /*PSEUDOCODE
         *  Random the chance a person will appear depending on the time of day (somehow)
         *  if someone appears
         *      random which kind of person they are //Part of the Customer class already
         *      get general attributes of that person
         *      random the in-store time (will decide how many items they get) //Part of Customer class already
         *      create the person
         */
    }

    public int getCurrentProbability() {
        return currentProbability;
    }
    
    public void calcCurrentProbability(int currentHour)
    {
        //Assuming the the chances are going to vary to the nearest hour
        double[] timeProbabilities =  {0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5};
        currentProbability = (int)timeProbabilities[currentHour];
    }

    public void addToSmallestQueue(Customer myCustomer, boolean express)
    {
        Checkout minCheckout = null;// = new Checkout();
        int min = Integer.MAX_VALUE;
        if (express)
        {
            for (Checkout currentCheckout:checkoutList)
            {
                if (currentCheckout.itemCount() > min)
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
                    if (currentCheckout.itemCount() > min)
                    {
                        minCheckout = currentCheckout;
                        min = currentCheckout.itemCount();
                }
            }
        }

        /*for(checkout currentCheckout : checkoutList){
            if(currentCheckout.getItemCount() > min){
                if (express || !currentCheckout.express() ){// Will only consider if we're allowing express checkouts if the current checkout is normal
                    // assigment etc.
                }
            }
        }*/
        minCheckout.add(myCustomer);
    }



}
