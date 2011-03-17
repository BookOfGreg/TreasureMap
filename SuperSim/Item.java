
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
    private final int ID;
    /**
     * Constructor for objects of class Item
     */
    public Item(String itemName, double itemPrice, int itemId)
    {
        NAME = itemName;
        PRICE = itemPrice;
        ID = itemId;
    }

    /**
     * @return The name of the item.
     */
    public String getName() 
    {
        return NAME;
    }

    /**
     * @return The price of the item.
     */
    public double getPrice()
    {
        return PRICE;
    }

    /**
     * @return The Id of the item.
     */
    public int getId()
    {
        return ID;    
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
