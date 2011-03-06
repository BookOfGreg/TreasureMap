import java.util.ArrayList;
/**
 * Write a description of class Checkout here.
 * 
 * @author AngryPirates 
 * @version 0.1
 */
public class Checkout
{
    private ArrayList<Customer> queue;
    private final int SCANNING_SPEED = 2;
    private ArrayList<String> itemReceipt;
    private ArrayList<String> valueReceipt;
    //private boolean isExpress = false;
    /**
     * Constructor for objects of class Checkout
     */
    public Checkout()
    {
        // initialise instance variables
      
      
    }
    
    public void run()
    {
        /*if (hasItems) //This should be part of checkout, not store.
        * {
        *     scanItems
        *     randomDelays
        *  }
        *  else 
        *  {
        *      makeReceipt
        *      saveStats
        *      customerLeaves
        *      addCustomerFromQueue
        *  }
        */
    }
    
    public int itemCount()
    {
        //getQueueLength||getItemsInQueue
        return 5; //arbitrary
    }
    
    public boolean isExpress()
    {
        //
        return true; //arbitrary
    }
    
    public void add(Customer newCustomer)
    {
        //
    }
    //SOME SUGGESTED METHODS.
    //scan item
    //write to loyalty log
    //pushCustomer
    //popCustomer
    //getQueueLength||getItemsInQueue
}
