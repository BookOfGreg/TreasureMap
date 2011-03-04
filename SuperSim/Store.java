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
    public void main(String [ ] args)throws InterruptedException
    {
        //Initialization methods
        int[] myArray = new int[2];
        myArray = menuSystem(); //0 = ticks, 1 = sleepTime
        int ticks = myArray[0];
        int sleepTime = myArray[1];
        ItemHandler myItemHandler = new ItemHandler(); //myItemHandler.createItems is an instance method. When called from static context it needs to be an instance as a class is too vague.
        itemList = myItemHandler.getItemList();

        //Main methods
        for (int currentTick = 1; currentTick <= ticks; currentTick++)
        {
            /* Not sure if this is necessary with the current definiton of rebalanceCustomers(), correct me if I'm wrong - Sam 
            * if ((ticks % 3600) == 0) //if tick is in new hour
            * {
            *     rebalanceCustomers(); //Each hour set the customer arrival rates
            * }
            */
           
            createCustomer(); //(calculate likelyhood of each customer type)
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
                    addToSmallestQueue(customerBrowsing.remove(currentCustomerIndex), (currentCustomer.getTrolleyCount() <= 10));
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
               // currentCheckout.runCheckout();
            }
            //if (checkoutLength > desiredAverageLength)
            {
               // openNewCheckout();
            }
            if (!(sleepTime == 0))
            {
               // drawGraphics();
                Thread.currentThread().sleep(sleepTime); //method to pause processing.
            }
        }
        //reportStatistics(); 
    }

    public void addToSmallestQueue(Customer myCustomer, boolean express)
    {
        Checkout minCheckout = new Checkout();
        int min = Integer.MAX_VALUE;
        if (express)
        {
            for (Checkout currentCheckout:checkoutList)
            {
                //if (currentCheckout.itemCount() > min)
                {
                    minCheckout = currentCheckout;
                  //  min = currentCheckout.itemCount;
                }
            }
        }
        else
        {
           // for (checkout currentCheckout:checkoutList)
            {
               // if (!currentCheckout.express())
                  //  if (currentCheckout.itemCount > min)
                    {
                      //  minCheckout = currentCheckout;
                     //   min = currentCheckout.itemCount;
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

       // minCheckout.add(myCustomer);
    }

    public int[] menuSystem()
    {
        //How fast you wish to run
        //Time period per tick  ticks per second
        //how long do you want ro run it for
        //option to watch simulation or go straight to stats
        UserDialog myUD = new UserDialog();
        int sleepTime = -1;
        if(myUD.getBoolean("Do you want to run in Stats-Only mode?"))
        {
            sleepTime = 0;
        }
        else
        {
            int speed = myUD.getInt("What speed multiplier do you want to run at? For real time put 1, maximum is 1000x real time");
            //max speed = 1000x real life, 1 second = 1 millisecond.
            float f = (1/speed)*1000;
            sleepTime = (int)(f + 0.5f);
        }
        int hours = myUD.getInt("How many hours do you want to run the program?");
        int ticks = (hours)*3600; //number of seconds in hour.
        int[] myArray = new int[2];
        myArray[0] = ticks;
        myArray[1] = sleepTime;
        return myArray;
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
        if (rand.nextFloat() <= rebalanceCustomers()) {
            customerBrowsing.add(new Customer());
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

    public int rebalanceCustomers() {
        //Assuming the the chances are going to vary to the nearest hour
        double[] timeProbabilities =  {0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5};
        int currentHour = currentTick / 3600;
        int currentProbability = (int)timeProbabilities[currentHour];
        
        return currentProbability;
    }
}
