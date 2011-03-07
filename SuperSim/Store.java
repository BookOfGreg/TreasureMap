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
    private double currentProbability;
    private final int DESIRED_AVERAGE_LENGTH = 4;

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
        Checkout newCheckoutExpress = new Checkout(true);
        checkoutList.add(newCheckoutExpress);
        Checkout newCheckout = new Checkout(false);
        checkoutList.add(newCheckout);
    }

    /** 
     * Set the timing for the program
     */
    public void Run()
    {
        //Main methods
        createCustomer();
        for(int i = (customerBrowsing.size()-1); i == 0; i--)
        {
            Customer currentCustomer = customerBrowsing.get(i);
            int shoppingTime = currentCustomer.getShoppingTime(); //just so there are less method calls. ~Alex: getShoppingTime() only gets called once!
            if (shoppingTime > 0)
            {
                //currentCustomer.setShoppingTime(shoppingTime-1); //~Alex: This is now handled within Customer.
                currentCustomer.addItem();
            }
            else
            {
                //int currentCustomerIndex = customerBrowsing.indexOf(currentCustomer); //remove() will only return the element if it's removed by index and not element
                if (currentCustomer.getTrolleyCount() <= 10)
                {
                    addToSmallestQueue(customerBrowsing.remove(i),true);//join queue with lest items
                }
                else
                {
                    addToSmallestQueue(customerBrowsing.remove(i),false);//join queue with lest items exclude express
                }
                //addToSmallestQueue(customerBrowsing.remove(currentCustomerIndex), (currentCustomer.getTrolleyCount() <=10));
            }
        }
        for (Checkout currentCheckout:checkoutList)
        {
            currentCheckout.run();
        }
        if (currentAverageLength() > DESIRED_AVERAGE_LENGTH)
        {
            Checkout newCheckout = new Checkout(false);
            checkoutList.add(newCheckout);
            //openNewCheckout();
        }
    }
    
    public double currentAverageLength()
    {
        int sum = 0;
        for (Checkout c:checkoutList)
        {
            sum += c.getQueueLength();
        }
        return sum / checkoutList.size();
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
        if (rand.nextFloat() <= currentProbability) {
            //id number needs calculating.
            customerBrowsing.add(new Customer(itemList, 5));//arbitrary
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
    
    public void calcCurrentProbability(int currentHour)
    {
        //Assuming the the chances are going to vary to the nearest hour
        double[] timeProbabilities =  {0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5};
        currentProbability = timeProbabilities[currentHour];
    }

    public void addToSmallestQueue(Customer myCustomer, boolean express)
    {
        Checkout minCheckout = null;// = new Checkout();
        int min = Integer.MAX_VALUE;
        if (express)
        {
            for (Checkout currentCheckout:checkoutList)
            {
                if (currentCheckout.itemCount() < min)
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
                    if (currentCheckout.itemCount() < min)
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
