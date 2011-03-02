import java.util.ArrayList;
import java.util.Random;
/**
 * Write a description of class Customer here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Customer
{
    private ArrayList<Item> trolley;
    private Random rand;
    private final int ITEM_COLLECTION_RATE = 1;
    private final String  LOYALTY_CARD_NUMBER = "Parrot";
    private final int  MIN_ITEM = 1;
    private final int  RANGE = 1;
    private final int  ARRIVAL_TIME = 1; //the tick the customer is initialized on
    private int ITEM_LIMIT = 0;
    private String CUSTOMER_TYPE;

    /**
     * Constructor for objects of class Customer
     */
    public Customer()
    {
        // initialise instance variables
        trolley = new ArrayList<Item>();
        rand = new Random();
        int temp = 0;
        //Code to bias random based on time
        switch(rand.nextInt(5)){
            case 0: ITEM_LIMIT = 5; CUSTOMER_TYPE = "Business"; break;//Business
            case 1: ITEM_LIMIT = 15; CUSTOMER_TYPE = "Old"; break;//Old
            case 2: ITEM_LIMIT = 90; CUSTOMER_TYPE = "Family"; break;//Family
            case 3: ITEM_LIMIT = 3; CUSTOMER_TYPE = "Children"; break;//Children
            case 4: ITEM_LIMIT = 25; CUSTOMER_TYPE = "Generic"; break;//generic
        }
        //System.out.println(temp);
    }
    //addItem
    
    
    public void addItem()
    {
        for(int i = 0; i <= rand.nextInt((ITEM_LIMIT+1)); i++){
            trolley.add(new Item(rand.nextInt(51)));
        }
        
    }
    
    
    
}
