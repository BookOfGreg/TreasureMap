
/**
 *Creates an Item from Items.txt and assigns a name and price.
 * 
 * @author AngryPirates
 * @version 0.1
 */
public class Item
{
    private final String NAME;
    private final String PRICE; 
    private String ID;
    /**
     * Constructor for objects of class Item
     */
    public Item(String itemName, String itemPrice, String itemId)
    {
        NAME = itemName;
        PRICE = itemPrice;
        ID = itemId;
    }
    
    public String getID()
    {
        return ID;
    }

    /**
     *  Generic method to display name and price of an item.
     * @return String value of name and price.
     */
    public String toString() 
    {
        return NAME + " " + PRICE; 
    }

}
