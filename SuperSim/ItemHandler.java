import java.util.ArrayList;
import java.io.*;
import javax.swing.*;
import java.util.Scanner;
/**
 * Write a description of class ItemHandler here.
 * 
 * @author AngryPirates, based on TestReader by Steve Townsend and Jim Hunter, and TextFileHandler by Jim Hunter.
 * @version 0.1
 */
public class ItemHandler
{
    private ArrayList<Item> itemList;
    private BufferedReader reader;
    protected boolean open = false;
    protected static File currentDirectory = null;

    /**
     * Constructor for objects of class ItemHandler
     */
    public ItemHandler() 
    {
        itemList = new ArrayList<Item>();
       
        File file = getFile();
        if (file != null)
            openReader(file);

        String currentLine = readLine();
        while (currentLine != null)
        {
            Scanner s = new Scanner(currentLine).useDelimiter("\\s*,\\s*");
            String name = s.next();
            double price = Double.parseDouble(s.next());
            Item myItem = new Item(name,price);
            itemList.add(myItem);
            currentLine = readLine();
        }
    }

    /**
     * Try to open the given file and print an error message if it can't be done
     * @param file   the file
     */
    private void openReader(File file){
        try {
            // open new buffered reader for the specified file
            reader = new BufferedReader(new FileReader(file));
            open = true;
        }
        catch (FileNotFoundException e) { 
            System.err.println("TextReader: Problem opening file for reading: "+ file.getAbsolutePath());
        }
    }

    /**
     * This was taken from SimpleIO
     * 
     * Use a file choser to allow the user to select a file
     * @return file   the selected file or null if user cancels out
     */
    protected File getFile() {
        //fix to make sure the file chooser is on top of the BlueJ window
        JFrame jf = new JFrame("TextFileHandler"); 
        jf.setVisible(true);
        jf.setAlwaysOnTop(true);
        jf.setAlwaysOnTop(false);
        JFileChooser fc = new JFileChooser();
        // use the current directory if there is one
        if (currentDirectory != null)
            fc.setCurrentDirectory(currentDirectory);
        int response = fc.showOpenDialog(null); // prompt user for filename
        jf.dispose();
        if (response == JFileChooser.APPROVE_OPTION){  // response ok
            currentDirectory = fc.getCurrentDirectory();
            return fc.getSelectedFile();
        }
        else
            return null;
    }

    /**
     * This was taken from SimpleIO.
     * 
     * Reads a complete line
     * @return  the remainder of the line as a string,
     *          null if the end-of-file is reached
     *          null if an I/O exception is raised
     */
    public String readLine()
    {
        if (open){
            try{
                return reader.readLine();
            } 
            catch (IOException e) {
                System.err.println("TextReader: IOException raised in readLine(): " +e);
                return null;
            }
        }
        else {
            System.err.println("TextReader: File is not open in readLine()");
            return null;
        }       
    }
    
    public ArrayList<Item> getItemList()
    {
        return itemList;
    }
}