import java.util.ArrayList;
import java.util.Random;
import java.awt.*;
/**
 * Write a description of class Checkout here.
 * 
 * @author AngryPirates 
 * @version 21-03-11
 */
public class Checkout
{
    private ArrayList<Customer> queue;
    private ArrayList<String> itemReceipt;
    private boolean express = false;
    private boolean closing = false;
    private Customer currentCustomer;
    private final int ITEM_SCAN_SPEED;
    private int scanInterval;
    private int customerCounter;
    private int totalQueue;
    private Point coordinates;
    private Random rand = new Random();
    /**
     * Constructor for objects of class Checkout
     * @param isExpress Toggles if checkout to be created is express or not.
     */
    public Checkout(boolean isExpress)
    {
        express = isExpress;
        itemReceipt = new ArrayList<String>();
        queue = new ArrayList<Customer>();
        ITEM_SCAN_SPEED = 3;
        scanInterval = 3;
        customerCounter = 0;
    }

    /**
     * Runs once per tick, controls one customer at the till and all customers in the queue.
     */
    public int run()
    {
        int timeTracking = 0;
        if(queueHasCustomer())
        {
            for(Customer cust : queue)
            {
                cust.incWait();
            }
        }
        if (hasCustomer())
        {   
            if (hasItems())
            {
                if (scanInterval >= ITEM_SCAN_SPEED)
                {
                    scanItems();
                    scanInterval = 0;
                }
                else
                {
                    scanInterval -= randomDelay();
                    scanInterval++;
                }
            }
            else 
            {
                itemReceipt.add(Integer.toString(currentCustomer.getID()));
                timeTracking += (currentCustomer.getTimeInStore() + currentCustomer.getTimeInQueue());
                currentCustomer = null;
            }
        }
        else if (queueHasCustomer())
        {
            currentCustomer = queue.remove(0);
            customerCounter++;
        }        
        return timeTracking;
    }

    /**
     * Sums up all the time customers spend in the queue.
     */
    public void calcAverageQueue(Customer cust)
    {
        totalQueue += cust.getTimeInQueue();
    }

    /**
     * Calculates the average time a customer spends in the queue.
     * @return Returns the average length as double.
     */
    public double getAverageQueue()
    {
        double average = totalQueue/customerCounter;
        return average;
    }

    private void makeReceipt()
    {
        //what does this entail?
    }

    /**
     * Returns true if the queue is not empty.
     * @return True if queue has at lest 1 customer.
     */
    private boolean queueHasCustomer()
    {
        if (queue.size() > 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * Returns true if there is a customer at the till.
     * @return True if the till has a customer. 
     */
    private boolean hasCustomer()
    {
        if (!(currentCustomer == null))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * Returns the queue size
     * @return returns queue size as an integer.
     */
    public int getQueueLength()
    {
        return queue.size();
    }

    /**
     * Checks if the customer at the till has any more items in the trolley.
     * @return True if the customer has items in the trolley.
     */
    private boolean hasItems()
    {
        if (currentCustomer.getTrolley().size() > 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    private int randomDelay()
    {
        if (rand.nextInt(50) > 45) {
            return rand.nextInt(10);
        }
        return 0;
    }

    /**
     * Scan items takes an item out of the trolley of the customer and adds the item name to the reciept list.
     */
    private void scanItems()
    {
        Item thisItem = currentCustomer.removeTrolleyItem();
        itemReceipt.add(thisItem.toString()); //Seems to throw a NPE occassionally, no defined cause //Fixed ~Alex
        if (itemReceipt.size() > 10000){
            FileHandler.batchAdd(itemReceipt);
            itemReceipt.clear();
        }
    }

    /**
     * Method to get the number of Items in all the trolleys in the queue.
     * @return Number of items in the queue as an int.
     */
    public int itemCount()
    {
        int items=0;
        for (Customer c:queue)
        {
            items += c.getTrolleyCount();
        }
        return items;
    }

    /**
     * Returns if the checkout is an express checkout.
     */
    public boolean isExpress()
    {
        return express;
    }

    /**
     * Method to add a customer to the queue of the checkout.
     */
    public void add(Customer newCustomer)
    {
        queue.add(newCustomer);
    }
    
    public void setClosing() {
        closing = true;
    }
    
    public boolean getClosing() {
        return closing;
    }
}
