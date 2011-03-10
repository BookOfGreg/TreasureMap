
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
        int ticks = myArray[0];
        int sleepTime = myArray[1];
        int startHour = myArray[2];
        
        //Calculate tick related things
        int startingTick = startHour*3600;
        ticks += startingTick;


        //mainMethods
        myStore.calcCurrentProbability(currentTick / 3600);
        for (int currentTick = startingTick; currentTick <= ticks; currentTick++)
        {
            if ((currentTick % 3600) == 0)
            {
                System.out.println("CalculatingProbability");
                myStore.calcCurrentProbability((currentTick / 3600)%24);
            }
            myStore.Run((currentTick / 3600)%24);
            if (!(sleepTime == 0))
            {
                drawGraphics();
                Thread.currentThread().sleep(sleepTime);
            }
        }
        reportStatistics(myStore.getCustomerCounter(), myStore.getAverageInStore(), myStore.getAverageQueue()); 
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
        //
    }

    public void reportStatistics(int customerCounter, double averageInStore, double averageQueue)
    {
        System.out.println(customerCounter+ "customer count" + averageInStore + "average store" + averageQueue + "average queue");
    }

    /**
     * Menu system uses User Dialog to gather data on Stats Only mode, Speed multiplier for the program and virtual hours it wants to be run.
     */
    public int[] menuSystem()
    {
        UserDialog myUD = new UserDialog();
        int sleepTime = -1;
        if(myUD.getBoolean("Do you want to run in Stats-Only mode?"))
        {
            sleepTime = 0;
        }
        else
        {
            int speed = myUD.getInt("What speed multiplier do you want to run at? For real time put 1, maximum is 1000x real time");
            float f = (1/speed)*1000;
            sleepTime = (int)(f + 0.5f);
        }
        int hours = myUD.getInt("How many hours do you want to run the program?");
        int ticks = (hours)*3600; //number of seconds in hour.
        int startHour = myUD.getInt("Which hour of the day do you want to start the program in? (24h clock)"); //arbitrary // Needs parsing for minutes and if we put in 00:00
        int[] myArray = new int[3];
        myArray[0] = ticks;
        myArray[1] = sleepTime;
        myArray[2] = startHour;
        return myArray;
    }
}

