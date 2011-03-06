
/**
 * Write a description of class Controller here.
 * 
 * @author AngryPirates Cabin Boy Greg 
 * @version 0.1
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
    }

    public void main(String [ ] args)throws InterruptedException
    {
        //Initialization methods
        int[] myArray = new int[2];
        myArray = menuSystem(); //0 = ticks, 1 = sleepTime
        int ticks = myArray[0];
        int sleepTime = myArray[1];

        //mainMethods
        for (int currentTick = 1; currentTick <= ticks; currentTick++)
        {
            if ((currentTick % 3600) == 0)
            {
                myStore.calcCurrentProbability(currentTick / 3600);
            }
            myStore.Run();
            if (!(sleepTime == 0))
            {
                drawGraphics();
                Thread.currentThread().sleep(sleepTime);
            }
        }
        reportStatistics(myStore.getCustomerCounter(), myStore.getAverageStore(), myStore.getAverageQueue()); 
    }

    public void drawGraphics()
    {
        //
    }

    public void reportStatistics(int customerCounter, double averageStore, double averageQueue)
    {
        //
    }

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
        int[] myArray = new int[2];
        myArray[0] = ticks;
        myArray[1] = sleepTime;
        return myArray;
    }
}

