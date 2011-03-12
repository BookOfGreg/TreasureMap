
/**
 *Creates an Item from Items.txt and assigns a name and price.
 * 
 * @author AngryPirates
 * @version 0.1
 */
public class Item
{
    private final String NAME;
    private final double PRICE; 

    /**
     * Constructor for objects of class Item
     */
    public Item(String itemName, double itemPrice)
    {
        NAME = itemName;
        PRICE = itemPrice;
    }
    
    /**
     * @return The name of the item.
     */
    public String getName() 
    {
        return NAME;
    }
    
    
    /**
     * @return The price if the item.
     */
    public double getPrice()
    {
        return PRICE;
    }
    
    /**
     *  Generic method to display name and price of an item.
     * @return String value of name and price.
     */
    public String toString() 
    {
        return NAME + " - " + PRICE; 
    }
    
}
