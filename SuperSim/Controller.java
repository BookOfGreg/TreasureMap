import java.text.DecimalFormat;
import java.awt.Graphics2D;

/**
 * Write a description of class Controller here.
 * 
 * @author AngryPirates Cabin Boy Greg 
 * @version 2011,03,10
 */
public class Controller
{
    // instance variables - replace the example below with your own
    private int startTime; //starting hour
    private int runTime; //total running time
    private int currentTick; //current running time
    private Store myStore;

    private DecimalFormat statOutput = new DecimalFormat("#,##0");
    private DecimalFormat currencyOutput = new DecimalFormat("#,##0.00");
    private DecimalFormat avgOutput = new DecimalFormat("#,##0.0000");
    /**
     * Constructor for objects of class Controller
     */
    public Controller() 
    {
        myStore = new Store();
        try {
            runController();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void runController() throws InterruptedException
    {
        //Initialization methods
        int[] myArray = new int[3];
        myArray = menuSystem(); //0 = ticks, 1 = sleepTime
        int totalTicks = myArray[0];
        int sleepTime = myArray[1];
        int startHour = myArray[2];

        //Calculate tick related things
        int startingTick = startHour*3600;
        int ticks = startingTick + totalTicks;

        //mainMethods
        myStore.calcCurrentProbability(currentTick / 3600);
        for (int currentTick = startingTick; currentTick <= ticks; currentTick++)
        {
            if ((currentTick % 3600) == 0)
            {
                //System.out.println("CalculatingProbability");
                myStore.calcCurrentProbability((currentTick / 3600)%24);
            }
            myStore.Run((currentTick % 3600));
            myStore.updateCumulativeAverage();
            myStore.Run((currentTick / 3600)%24);
            if (!(sleepTime == 0))
            {
                drawGraphics();
                Thread.currentThread().sleep(sleepTime);
            }
        }
        reportStatistics(totalTicks, myStore.getCustomerCounter(), myStore.getAverageInStore(totalTicks), myStore.getAverageQueue(totalTicks), myStore.getShopProfit()); 
    }

    /**
     * Main method. Runs initialization menu and controlls the ticks through the program.
     */
    public static void main(String [ ] args)
    {
        new Controller();
    }

    public void drawGraphics()
    {
        //Graphics g = myJPanel
    }

    public void reportStatistics(int totalTicks, int customerCounter, double averageInStore, double averageQueue, double shopProfit)
    {
        System.out.println("");
        System.out.println("########################## Statistics: ##########################");
        System.out.println(statOutput.format((totalTicks / 3600)) + " Hours total running time");
        System.out.println(statOutput.format(customerCounter)+ " Customers in the Store");
        System.out.println(avgOutput.format(averageInStore) + " Average Customers per Hour");
        System.out.println(avgOutput.format(averageQueue) + " Average Customers in a Queue");
        System.out.println("�" + currencyOutput.format(shopProfit) + " Total Profit");
        System.out.println("�" + currencyOutput.format((shopProfit/customerCounter)) + " Profit per Customer");
        System.out.println("#################################################################");
        System.out.println("");
    }

    /**
     * Menu system uses User Dialog to gather data on Stats Only mode, Speed multiplier for the program and virtual hours it wants to be run.
     */
    public int[] menuSystem()
    {
        UserDialog myUD = new UserDialog();
        int sleepTime = -1;
        do{
            if(myUD.getBoolean("Do you want to run in Stats-Only mode?")){
                sleepTime = 0;
            }
            else{
                int speed;
                do{
                    speed = myUD.getInt("What speed multiplier do you want to run at? For real time put 1, maximum is 1000x real time");
                }while (speed < 1);
                if (speed > 1000){
                    myUD.showMessage("Speed selected was " + speed + ", number defaulting to 1000");
                    speed = 1000;
                }
                float f = (1/speed)*1000;
                sleepTime = (int)(f + 0.5f);
            }
        }while (sleepTime == -1);
        int hours;
        do{
            hours = myUD.getInt("How many hours do you want to run the program? Maximum is 4 weeks (672 hours)");
        }while (hours < 1);
        if (hours > 672){
            myUD.showMessage("Hours selected was " + hours + ", number defaulting to 672");
            hours = 672;
        }
        int ticks = (hours)*3600; //number of seconds in hour.
        int startHour;
        do{
            startHour = myUD.getInt("Which hour of the day do you want to start the program in? (24h clock)"); //arbitrary // Needs parsing for minutes and if we put in 00:00
        }while (startHour < 0 || startHour > 24);
        int[] myArray = new int[3];
        myArray[0] = ticks;
        myArray[1] = sleepTime;
        myArray[2] = startHour;
        return myArray;
    }
}

