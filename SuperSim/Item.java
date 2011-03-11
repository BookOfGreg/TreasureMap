
/**
 * Write a description of class Item here.
 * 
 * @author AngryPirates
 * @version 0.1
 */
public class Item
{
    // instance variables - replace the example below with your own
    private final String NAME;
    private final double PRICE; 

    /**
     * Constructor for objects of class Item
     */
    public Item(String itemName, double itemPrice)
    {
        // initialise instance variables
        NAME = itemName;
        PRICE = itemPrice;
    }
    
    public String getName()
    {
        return NAME;
    }
    
    public double getPrice()
    {
        return PRICE;
    }
    
    public String toString() {
        return NAME + " - " + PRICE; 
    }
    
}
