import javax.swing.*;
import java.awt.*;
/**
 * Write a description of class Canvas here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Canvas extends JPanel 
{
    private final int WIDTH = 1000;
    private final int HEIGHT = 1000;
    private JPanel p;
    private JFrame f;
    private Image canvasImage;
    private Graphics g;
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

    public void addCheckout()
    {
        g.setColor(new Color(255,255,0));
        g.drawLine(0,0,150,150);
        p.repaint();
    }
    
    public void addCustomer()
    {
        //
    }
    
    public void addShopFloor()
    {
        //
    }
}
