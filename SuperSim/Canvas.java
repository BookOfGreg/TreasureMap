import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
/**
 * Write a description of class Canvas here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Canvas extends JPanel 
{
    private final int WIDTH = 640;
    private final int HEIGHT = 480;
    private JPanel p;
    private JFrame f;
    private Image canvasImage;
    private Graphics g;
    private final int CUSTOMER_DIAMETER = 16;
    public final int CHECKOUT_WIDTH = 20;
    public final int CHECKOUT_LENGTH = 160;
    /**
     * Constructor for objects of class Canvas
     */
    public Canvas()
    {
        f = new JFrame(); //creates a frame
        p = new JPanel()
        {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(canvasImage, 0, 0, null);//g.drawLine(0,0,100,100);
            } //overrides paintComponent to draw automatically
        }; //creates a panel
        p.setPreferredSize(new Dimension(WIDTH,HEIGHT));
        f.setContentPane(p); //adds panel to frame
        f.pack(); //resizes frame
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Sets close behaviour
        f.setVisible(true); //paint
        //
        canvasImage = p.createImage(WIDTH,HEIGHT); //makes off screen drawing place
        g = (Graphics)canvasImage.getGraphics(); //
        //g.setColor(new Color(255,0,0)); //sets next color
        //g.fillRect(0, 0, WIDTH/2,HEIGHT/2); //makes a full screen rectangle with color
    }

    private void addCheckout(int index, int Y)
    {
        g.setColor(new Color(0,0,0));
        g.fillRect(index*CHECKOUT_WIDTH,Y,CHECKOUT_WIDTH-1,CHECKOUT_LENGTH);
        g.setColor(new Color(255,0,0));
        int[] x = {(index*CHECKOUT_WIDTH),(index*CHECKOUT_WIDTH)+(CHECKOUT_WIDTH/2),(index*CHECKOUT_WIDTH)+(CHECKOUT_WIDTH)};
        int[] y = {Y+25,Y,Y+25};
        g.fillPolygon(new Polygon(x,y,3));
        p.repaint();
    }

    private void addCustomer(Point coords)
    {
        g.setColor(new Color(0,0,255));
        g.fillOval((int)coords.getX(),(int)coords.getY(),CUSTOMER_DIAMETER,CUSTOMER_DIAMETER);
    }

    private void addCustomer(int column, int row)
    {
        g.setColor(new Color(0,0,255));
        g.fillOval(row*CHECKOUT_WIDTH,column*CHECKOUT_WIDTH, CUSTOMER_DIAMETER,CUSTOMER_DIAMETER);//arbitrary This WILL go wrong, incorrect calculateion on column
    }

    /**
     * adds a shop floor area, calls methods to add all other elements.
     * @param size the size of the shop floor
     * @param aisles the location of impassable aisles on the shop floor
     * @param checkoutArea the size of the checkout area
     * @param checkouts contains the customers in the queues
     */
    public void addShopFloor(Dimension size, ArrayList<Point> aisles, Dimension checkoutArea, ArrayList<Integer> checkouts, ArrayList<Point> customers)
    {
        g.setColor(new Color(255,255,255));
        g.fillRect(0,0,(int)size.getHeight(),(int)size.getWidth());
        g.setColor(new Color(100,100,100));
        for (Point aisle:aisles){
            g.fillRect((int)aisle.getX(),(int)aisle.getY(),20,320);
        }
        addCheckoutArea(checkoutArea, (int)size.getHeight());
        for (int i = 0; i<checkouts.size();i++){
            addCheckout(i,(int)size.getHeight());
            for (int j = 0; j<checkouts.get(i);j++){
                addCustomer(i,j);
            }
        }
        for (Point customer:customers){
            addCustomer(customer);
        }
        p.repaint();
    }

    /**
     * Draws the checkout area at the bottom of the shop area.
     * @param X The position of the top of the checkout area.
     */
    private void addCheckoutArea(Dimension checkoutArea, int Y)
    {
        g.setColor(new Color(50,50,0));
        g.fillRect(0,Y,(int)checkoutArea.getWidth(),(int)checkoutArea.getHeight()+Y);
    }

    public void erase()
    {
        g.setColor(new Color(0,0,0));
        g.fillRect(0,0,WIDTH,HEIGHT);
        p.repaint();
    }
}
