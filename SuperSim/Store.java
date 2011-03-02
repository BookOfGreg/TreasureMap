import java.util.ArrayList;

/**
 * Write a description of class Store here.
 * 
 * @author AngryPirates 
 * @version 0.1_4
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
        ItemHandler myItemHandler = new ItemHandler(); //myItemHandler.createItems is an instance method. When called from static context it needs to be an instance as a class is too vague.
        myItemHandler.createItems();
        //Main methods
        /* PSEUDOCODE
         *  for each tick
         *      if (tick % 3600)//if tick is in new hour
         *      {
         *          reevaluate customer balance
         *      }
         *      if it's time for a new customer
         *      {
         *          create customer (calculate likelyhood of each customer type)
         *      }
         *      for(Customer currentCustomer:customerBrowsing) //for each customer
         *      {
         *          if (currentCustomer.getShoppingTime() == 0)
         *          {
         *              if (currentCustomer.getTrolleyCount <= 10) //ask alex for trolleycount
         *              {
         *                  addToSmallestQueue(customerBrowsing.remove(currentCustomer),true)//join queue with lest items //ask kieran for itemCount
         *              }
         *              else
         *              {
         *                  addToSmallestQueue(customerBrowsing.remove(currentCustomer),false)//join queue with lest items exclude express
         *              }
         *          }
         *          else
         *          {
         *              currentCustomer.setShoppingTime(currentCustomer.getShoppingTime()-1);
         *              add items
         *          }
         *      }
         *      for each checkout
         *          if has items
         *              scan items
         *              random (barcode related) delays
         *          else
         *              make receipt
         *              save stats
         *              the customer leaves (delete from checkout)
         *              add customer from queue
         *      if checkout length > average length
         *          open new checkout
         *      draw all graphics
         *      sleep till next tick
         *  report statistics
         */
        Thread.currentThread().sleep(500); //method to pause processing.
    }

    public void addToSmallestQueue(Customer myCustomer, boolean express)
    {
        checkout minCheckout = new checkout();
        int min = Integer.MAX_INTEGER;
        if (express)
        {
            for (checkout currentCheckout:checkoutList)
            {
                if (currentCheckout.itemCount > min)
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
        minCheckout.add(myCustomer);

        for(checkout currentCheckout : checkoutList){
            if(currentCheckout.getItemCount() > min){
                if (express || !currentCheckout.express() ){// Will only consider if we're allowing express checkouts if the current checkout is normal
                    // assigment etc.
                }
            }
        }
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

        /*PSEUDOCODE
         *  Random the chance a person will appear depending on the time of day (somehow)
         *  if someone appears
         *      random which kind of person they are
         *      get general attributes of that person
         *      random the in-store time (will decide how many items they get)
         *      create the person
         */
        Customer myCustomer = new Customer();
        customerBrowsing.add(myCustomer); 
    }

}
