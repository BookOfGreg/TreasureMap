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
    private ArrayList<String> itemReceipt;
    //private ArrayList<String> valueReceipt; //can fetch from itemlist.
    private boolean express = false;
    private Customer currentCustomer;
    private final int ITEM_SCAN_SPEED;
    private int scanInterval;
    /**
     * Constructor for objects of class Checkout
     */
    public Checkout(boolean isExpress)
    {
        express = isExpress;
        ArrayList itemReceipt = new ArrayList<String>();
        //ArrayList valueReceipt = new ArrayList<String>();
        queue = new ArrayList<Customer>();
        ITEM_SCAN_SPEED = 3;
        scanInterval = 3;
    }
    
    public void run()
    {
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
                makeReceipt();
                saveStats();
                currentCustomer = null;
            }
        }
        else if (queueHasCustomer())
        {
            currentCustomer = queue.remove(0);
        }
        else
        {
            //do nothing
        }
    }
    
    private void makeReceipt()
    {
        //what does this entail?
    }
    
    private void saveStats()
    {
        //stub
    }
    
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
    
    public int getQueueLength()
    {
        return queue.size();
    }
    
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
        return 0; //arbitrary//do something with a really small chance of delay, and the delay can be between a small range.
    }
    
    private void scanItems()
    {
        Item thisItem = currentCustomer.removeTrolleyItem();
        itemReceipt.add(thisItem.getName());
        //add price?
    }
    
    public int itemCount()
    {
        int items=0;
        for (Customer c:queue)
        {
            items += c.getTrolleyCount();
        }
        return items;
    }
    
    public boolean isExpress()
    {
        return express;
    }
    
    public void add(Customer newCustomer)
    {
        queue.add(newCustomer);
    }
    //SOME SUGGESTED METHODS.
    //scan item
    //write to loyalty log
    //pushCustomer
    //popCustomer
    //getQueueLength||getItemsInQueue
}
