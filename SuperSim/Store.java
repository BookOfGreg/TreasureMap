import java.util.ArrayList;

/**
 * Write a description of class Store here.
 * 
 * @author AngryPirates 
 * @version 0.1
 */
public class Store
{
    private int startTime;
    private int runTime;
    private int checkoutLimit;
    private ArrayList<Checkout> checkoutList;
    private ArrayList<Customer> customerBrowsing;
    private ArrayList<Item> itemList;

    /**
     * Constructor for objects of class Store
     */
    public Store()
    {
        checkoutList = new ArrayList<Checkout>();
        customerBrowsing = new ArrayList<Customer>();
        itemList = new ArrayList<Item>();
    }
    
    /** 
     * Set the timing for the program
     */
    public void main(String [ ] args)throws InterruptedException
    {
        //Initialization methods
        menuSystem();
        ItemHandler.createItems();
        //Main methods
        /* PSEUDOCODE
         *  for each tick
         *      create customer (calculate likelyhood of each customer type)
         *      for each customer
         *          if out of time
         *              if less than 10 items
         *                  join queue with lest items
         *              else
         *                  join queue with lest items exclude express
         *          else 
         *              reduce time, add items
         *      for each checkout
         *          if has items
         *              scan items
         *          else
         *              make receipt
         *              save stats
         *              the customer leaves
         *              add customer from queue
         *      if checkout time > limits
         *          open new checkout
         *      draw all graphics
         *      sleep till next tick
         *  report statistics
         */
        Thread.currentThread().sleep(500); //method to pause processing.
    }
    
    public void menuSystem()
    {
        //How fast you wish to run
        //Time period per tick  ticks per second
        //how long do you want ro run it for
        //option to watch simulation or go straight to stats
    }
    
    public void createCustomer()
    {
        Customer myCustomer = new Customer();
        customerBrowsing.add(myCustomer); 
    }
    
}
