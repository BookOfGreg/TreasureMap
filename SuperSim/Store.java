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
    private int totalInStore = 0;

    /**
     * Constructor for objects of class Store
     */
    public Store()
    {
        checkoutList = new ArrayList<Checkout>();
        customerBrowsing = new ArrayList<Customer>();
        itemList = new ArrayList<Item>();
        rand = new Random();
        FileHandler myFileHandler = new FileHandler();
        itemList = myFileHandler.getItemList();
        Checkout newCheckoutExpress = new Checkout(true);
        checkoutList.add(newCheckoutExpress);
        Checkout newCheckout = new Checkout(false);
        checkoutList.add(newCheckout);
    }
    
    /** 
     * Set the timing for the program
     */
    public void Run(int hour)
    {
        createCustomer(hour);
        for(int i = (customerBrowsing.size()-1); i == 0; i--)
        {
            Customer currentCustomer = customerBrowsing.get(i);
            if (currentCustomer.getShoppingTime() > 0)
            {
                currentCustomer.addItem();
            }
            else
            {
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
    
    public void calcAverageInStore(Customer cust)
    {
        totalInStore += cust.getTimeInStore();
    }
    
    public double getAverageInStore()
    {
        double average = totalInStore/customerCounter;
        return average; //arbitrary
    }
    
    public double getAverageQueue()
    {
        double avgTotal = 0;
        for (Checkout check:checkoutList)
        {
            avgTotal += check.getAverageQueue();
        }
        double average = avgTotal/checkoutList.size();
        return average; //arbitrary
    }
    
    public void createCustomer(int hour)
    {
        if (rand.nextFloat() <= currentProbability) {
            //id number needs calculating.
            customerBrowsing.add(new Customer(itemList, 5, hour));//arbitrary
            customerCounter++;
        }
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
        minCheckout.add(myCustomer);
    }
}
