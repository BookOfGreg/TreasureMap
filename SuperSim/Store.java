import java.util.ArrayList;
/**
 * Write a description of class Store here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Store
{
    // instance variables - replace the example below with your own
    private int startTime;
    private int runTime;
    private ArrayList<Checkout> checkoutList;
    private ArrayList<Customer> customerBrowsing;
    private ArrayList<Item> itemList;
    
    private int checkoutLimit;
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
    public static void main(String [ ] args)throws InterruptedException
    {
        menuSystem();
        ItemHandler.createItems();
        System.out.println("One");
        //Pause for 4 seconds
        Thread.sleep(1000); //One second
        //Print a message
        System.out.println("Pause for 1 second");
    }
    public static void menuSystem()
    {
        //
    }
    
}
