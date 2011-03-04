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
    private int startTime; //starting hour
    private int runTime; //total running time
    private int currentTick; //current running time
    private int checkoutLimit;
    private Random rand;
    private ArrayList<Checkout> checkoutList;
    private ArrayList<Customer> customerBrowsing;
    private ArrayList<Item> itemList;
    private int customerCounter;

    /**
     * Constructor for objects of class Store
     */
    public Store()
    {
        checkoutList = new ArrayList<Checkout>();
        customerBrowsing = new ArrayList<Customer>();
        itemList = new ArrayList<Item>();
        rand = new Random();
    }

    /** 
     * Set the timing for the program
     */
    public void run()
    {
        
        //Main methods
        
            for(Customer currentCustomer:customerBrowsing) //for each customer
            {
                int shoppingTime = currentCustomer.getShoppingTime(); //just so there are less method calls.
                if (shoppingTime > 0)
                {
                    currentCustomer.setShoppingTime(shoppingTime-1);
                    currentCustomer.addItem();
                }
                else
                {
                    int currentCustomerIndex = customerBrowsing.indexOf(currentCustomer); //remove() will only return the element if it's removed by index and not element
                    if (currentCustomer.getTrolleyCount() <= 10) //ask alex for trolleycount
                    {
                        addToSmallestQueue(customerBrowsing.remove(currentCustomerIndex),true);//join queue with lest items //ask kieran for itemCount
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
                /*if (hasItems) //This should be part of checkout, not store.
                {
                scanItems
                randomDelays
                }
                else 
                {
                makeReceipt
                saveStats
                customerLeaves
                addCustomerFromQueue
                }
                 */
                currentCheckout.runCheckout();
            }
            //if (checkoutLength > desiredAverageLength)
            {
                //openNewCheckout();
            }


    }

    public void addToSmallestQueue(Customer myCustomer, boolean express)
    {
        Checkout minCheckout = new Checkout();
        int min = Integer.MAX_VALUE;
        if (express)
        {
            for (Checkout currentCheckout:checkoutList)
            {
                if (currentCheckout.itemCount() > min)
                {
                    minCheckout = currentCheckout;
                    min = currentCheckout.itemCount;
                }
            }
        }
        else
        {
            for (checkout currentCheckout:checkoutList)
            {
                if (!currentCheckout.express())
                    if (currentCheckout.itemCount > min)
                    {
                        minCheckout = currentCheckout;
                        min = currentCheckout.itemCount;
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
