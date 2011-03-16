import java.text.DecimalFormat;
import java.awt.Graphics2D;

/**
 * Write a description of class Controller here.
 * 
 * @author AngryPirates Cabin Boy Greg & Seaman Sam
 * @version 2011,03,14
 */
public class Controller
{
    /* Average Execution Times (Sam):
     * 21504 Hours = 48 Minutes 17 Seconds (18.1 Gb - Loyalty File) - Simulation of about 2.5 Years
     * 10752 Hours = 19 Minutes 45 Seconds
     * 5376 Hours = 11 Minutes 51 Seconds
     * 2688 Hours = 5 Minutes 44 Seconds
     * 1344 Hours = 2 Minutes 40 Seconds
     * 672 Hours = 1 Minute 18 Seconds
     * 336 Hours = 38 Seconds
     * 168 Hours = 16 Seconds
     * 24 Hours = 2 Seconds
     */
    private final int MAX_HOURS = 86016;
    
    private int startTime; //starting hour
    private int runTime; //total running time
    private int currentTick; //current running time
    private Store myStore;

    //Number formating for statistics output
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

    /**
     * Performs the main operations of the simulation, controlling timings and everything else
     */
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
        
        //Gets the time from the start of the simulation as a number of seconds

        long startTime = System.currentTimeMillis();

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
        
        //Gets the time from the end of the simulation as a number of seconds

        long endTime = System.currentTimeMillis();
        
        reportStatistics(totalTicks, myStore.getCustomerCounter(), myStore.getAverageInStore(totalTicks), myStore.getAverageQueue(totalTicks), myStore.getShopProfit(), (endTime - startTime)/1000); 
    }

    /**
     * Main method. Runs initialization menu and controlls the ticks through the program.
     */
    public static void main(String [ ] args)
    {
        new Controller();
    }

    /**
     * Will at some point handle the visual aspect of the simulation
     */
    public void drawGraphics()
    {
        //Graphics g = myJPanel
    }

    /**
     * Handles the output of statistics at the end of the simulation. Outputs to terminal
     * @param totalTicks The total number of ticks the simulation was run for
     * @param customerCounter The total number of customers that came through the store
     * @param averageInStore The average number of customers in the store per hour
     * @param averageQueue The average queue length in the store
     * @param shopProfit The total cash earned from selling items
     * @param executionTime The time the simulation took to run
     */
    public void reportStatistics(int totalTicks, int customerCounter, double averageInStore, double averageQueue, double shopProfit, long executionTime)
    {
        System.out.println("");
        System.out.println("########################## Statistics: ##########################");
        System.out.println(statOutput.format((totalTicks / 3600)) + " Hours total running time");
        System.out.print("Execution Time: ");
        if (executionTime < 60) {
            System.out.println(executionTime + " Seconds");
        } else {
            System.out.print((executionTime/60) + " Minutes ");
            System.out.println((executionTime - ((executionTime/60)*60)) + " Seconds");
        }
        System.out.println("");
        
        System.out.println(statOutput.format(customerCounter)+ " Customers in the Store");
        System.out.println(avgOutput.format(averageInStore) + " Average Customers per Hour");
        System.out.println(avgOutput.format(averageQueue) + " Average Customers in a Queue");
        System.out.println("");
        
        System.out.println("£" + currencyOutput.format(shopProfit) + " Total Profit"); //Need to let profigt be tracked as BigDecimal, value is being capped (I think) - Sam
        System.out.println("£" + currencyOutput.format((shopProfit/customerCounter)) + " Profit per Customer");
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
            hours = myUD.getInt("How many hours do you want to run the program? Maximum is " + ((MAX_HOURS/24)/7) + " weeks (" + MAX_HOURS + " hours)");
        }while (hours < 1);
        if (hours > MAX_HOURS){
            myUD.showMessage("Hours selected was " + hours + ", number defaulting to " + MAX_HOURS);
            hours = MAX_HOURS;
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

