import java.util.ArrayList;

/**
 * Write a description of class Store here.
 * 
 * @author AngryPirates 
 * @version 0.1_2
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
         *          if finished shopping
         *              if less than 10 items
         *                  join queue with lest items
         *              else
         *                  join queue with lest items exclude express
         *          else 
         *              reduce time, add items
         *      for each checkout
         *          if has items
         *              scan items
         *              random (barcode related) delays
         *          else
         *              make receipt
         *              save stats
         *              the customer leaves (delete 0)
         *              //add customer from queue
         *      if checkout average time > average time limits
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
        
        /* PSEUDOCODE
         *  if stats only
         *      sleep = 0, draw graphics = 0
         *      ticks (aka runtime) = (how many hours)*3600 //number of seconds in hour.
         *  else
         *      sleeptime = (1/speed)*1000; //max speed = 1000x real life, 1 second = 1 millisecond.
         *      ticks (aka runtime) = (how many hours)*3600 //number of seconds in hour.
         */
    }
    
    public void createCustomer()
    {
        Customer myCustomer = new Customer();
        customerBrowsing.add(myCustomer); 
    }
    
}
