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
    private int checkoutLimit;
    /**
     * Constructor for objects of class Store
     */
    public Store()
    {
        checkoutList = new ArrayList<Checkout>();
        customerBrowsing = new ArrayList<Customer>();
    }

    /** 
     * Set the timing for the program
     */
    public  void main(String [ ] args)throws InterruptedException
    {
        menuSystem();
        System.out.println("One");
        //Pause for 4 seconds
        Thread.sleep(1000); //One second
        //Print a message
        System.out.println("Pause for 1 second");
    }
    public  void menuSystem()
    {
        //
    }
    
}
